package com.cat.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.CtiManager;
import com.cat.module.dto.cti.cmd.CallInfoQueryCommand;
import com.cat.module.dto.cti.cmd.RequestCommand;
import com.cat.module.entity.Agent;
import com.cat.module.entity.CollectorCallLog;
import com.cat.module.enums.CallType;
import com.cat.repository.AgentRepository;
import com.cat.repository.CollectorCallLogRepository;
import com.cat.util.StringUtils;

@Service
public class CollectorCallLogService extends BaseService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CollectorCallLogRepository collectorCallLogRepository;

	@Autowired
	private CtiManager ctiManager;

	@Transactional(rollbackFor = Exception.class)
	public void orignateCall(CollectorCallLog callLog) throws ServiceException {
		callLog.setId(generateId());
		callLog.setCallType(CallType.IN);
		collectorCallLogRepository.save(callLog);
		try {
			ctiManager.originate(callLog.getAgent(), callLog.getTargetTel(), String.valueOf(callLog.getId()));
		} catch (ApiException e) {
			logger.info("发起呼叫失败：", e);
			throw new ServiceException("操作失败");
		}
	}

	private void syncAgent(Agent agent) {
		
	}

	public void syncCallInfo() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.MINUTE, -1);
		Date end = calendar.getTime();
		calendar.add(Calendar.MINUTE, -5);
		calendar.add(Calendar.SECOND, -1);
		Date start = calendar.getTime();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String starttime = dateFormat.format(start);
		String endtime = dateFormat.format(end);
		
		CallInfoQueryCommand command = new CallInfoQueryCommand();
		command.setStarttime(starttime);
		command.setEndtime(endtime);
		
		command.setCmd(RequestCommand.CALLOUT_INFO);
		
		List<Agent> agents = agentRepository.findAll();
		if (agents == null) {
			return;
		}
		
		for (Agent agent : agents) {
			if (StringUtils.isEmpty(agent.getCollectorId())) {
				continue;
			}
			
			command.setAgent(agent.getAgent());
			syncAgent(agent);
		}
		
	}

}
