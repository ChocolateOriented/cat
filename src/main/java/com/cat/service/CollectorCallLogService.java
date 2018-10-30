package com.cat.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.annotation.ClustersSchedule;
import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.CtiManager;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.dto.cti.CallInfo;
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
	private MobileAddressService mobileAddressService;

	@Autowired
	private CollectorCallLogRepository collectorCallLogRepository;

	@Autowired
	private CtiManager ctiManager;

	@Value("${cti.dialRule.agentPattern}")
	private String[] agentPattern;

	@Value("${cti.dialRule.preDial}")
	private String[] preDial;

	@Value("${cti.dialRule.nonlocalPre}")
	private String[] nonlocalPre;

	private List<DialRule> dialRules  = new ArrayList<>();

	/**
	 * 初始化cti拨号加拨规则
	 */
	@PostConstruct
	private void init() {
		int length = agentPattern.length;
		
		if (length != preDial.length || length != nonlocalPre.length) {
			throw new IllegalStateException("CTI拨号规则配置异常");
		}
		
		for (int i = 0; i < length; i++) {
			DialRule rule = new DialRule();
			rule.agentPattern = agentPattern[i];
			rule.preDial = preDial[i];
			rule.nonlocalPre = nonlocalPre[i];
			dialRules.add(rule);
		}
	}

	/**
	 * 加拨规则实体
	 */
	private class DialRule {
		
		private String agentPattern;
		
		private String preDial;
		
		private String nonlocalPre;
		
	}

	/**
	 * 发起呼叫
	 * @param callLog
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void orignateCall(CollectorCallLog callLog) throws ServiceException {
		String targetTel = trimInvalidTelNumber(callLog.getTargetTel());
		
		callLog.setId(generateId());
		callLog.setCallType(CallType.OUT);
		
		String location = mobileAddressService.getFullAddressByMobile(targetTel);
		callLog.setLocation(location);
		collectorCallLogRepository.save(callLog);
		
		String dialTarget = prependDialTel(callLog.getAgent(), targetTel);
		
		try {
			ctiManager.originate(callLog.getAgent(), dialTarget, String.valueOf(callLog.getId()));
		} catch (ApiException e) {
			logger.info("发起呼叫失败：", e);
			throw new ServiceException("操作失败");
		}
	}

	/**
	 * 去除号码中的非法字符
	 * @param tel
	 * @return
	 */
	private String trimInvalidTelNumber(String tel) {
		String newTel = tel.replaceAll("\\D", "");
		if (newTel.startsWith("86")) {
			newTel = newTel.substring(2);
		}
		return newTel;
	}

	/**
	 * 根据坐席对呼出号码进行加拨
	 * @param agent
	 * @param tel
	 * @return
	 */
	private String prependDialTel(String agent, String tel) {
		for (DialRule rule : dialRules) {
			if (!Pattern.matches(rule.agentPattern, agent)) {
				continue;
			}
			
			if (mobileAddressService.isMobile(tel) && !mobileAddressService.isLocalMobile(tel)) {
				tel = rule.nonlocalPre + tel;
			}
			
			tel = rule.preDial + tel;
			return tel;
		}
		
		return tel;
	}

	/**
	 * 每5分钟同步CTI通话信息
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	@ClustersSchedule
	public void syncCallInfo5Minutely() {
		logger.info("5分钟定时同步电话通话信记录开始");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.MINUTE, -1);
		Date end = calendar.getTime();
		calendar.add(Calendar.MINUTE, -5);
		calendar.add(Calendar.SECOND, -1);
		Date start = calendar.getTime();
		
		syncCallInfoByTime(start, end);
		logger.info("5分钟定时同步电话通话信记录结束");
	}

	/**
	 * 没1小时同步CTI通话信息
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	@ClustersSchedule
	public void syncCallInfoHourly() {
		logger.info("每小时同步电话通话信记录开始");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.add(Calendar.MINUTE, -30);
		Date end = c.getTime();
		c.add(Calendar.HOUR_OF_DAY, -1);
		c.add(Calendar.SECOND, -1);
		Date start = c.getTime();
		
		syncCallInfoByTime(start, end);
		logger.info("每小时同步电话通话信记录结束");
	}

	/**
	 * 每日同步CIT通话信息
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	@ClustersSchedule
	public void syncCallInfoDaily() {
		logger.info("每日同步电话通话信记录开始");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date end = c.getTime();
		c.add(Calendar.DATE, -1);
		c.add(Calendar.SECOND, -1);
		Date start = c.getTime();
		
		syncCallInfoByTime(start, end);
		logger.info("每日时同步电话通话信记录结束");
	}

	/**
	 * 根据时间段同步CTI通话信息
	 * @param start
	 * @param end
	 */
	public void syncCallInfoByTime(Date start, Date end) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String starttime = dateFormat.format(start);
		String endtime = dateFormat.format(end);
		
		CallInfoQueryCommand command = new CallInfoQueryCommand();
		command.setStarttime(starttime);
		command.setEndtime(endtime);
		
		command.setCmd(RequestCommand.CALLOUT_INFO);
		
		List<Agent> agents = agentRepository.findAll();
		if (agents == null || agents.isEmpty()) {
			logger.info("没有需要同步通话记录的坐席");
			return;
		}
		
		for (Agent agent : agents) {
			if (StringUtils.isEmpty(agent.getCollectorId())) {
				continue;
			}
			
			logger.info("同步坐席{}", agent.getAgent());
			command.setAgent(agent.getAgent());
			command.setQueue(null);
			List<CallInfo> queryCallinInfo = queryCallInfo(command, CallType.IN);
			saveAgentCallInfo(queryCallinInfo, agent, CallType.IN);

			List<CallInfo> queryCalloutInfo = queryCallInfo(command, CallType.OUT);
			saveAgentCallInfo(queryCalloutInfo, agent, CallType.OUT);
		}
	}

	/**
	 * 查询CTI通话信息
	 * @param command
	 * @param callType
	 * @return
	 */
	private List<CallInfo> queryCallInfo(CallInfoQueryCommand command, CallType callType) {
		command.setPage(null);
		List<CallInfo> callInfos = new ArrayList<CallInfo>();
		try {
			PageResponse<CallInfo> first = ctiManager.queryCallInfo(command, callType);
			if (first == null) {
				logger.info("获取{}通话信息失败", callType.getDesc());
				return callInfos;
			}
			
			Page<CallInfo> page = first.getData();
			if (page == null || page.isEmpty()) {
				return callInfos;
			}
			
			callInfos.addAll(page.getEntities());
			
			long total = page.getTotal();
			long totalPage = total / 10;
			if (total % 10 > 0) {
				totalPage++;
			}
			logger.info("获取{}通话信息页数{}",callType.getDesc(), totalPage);
			
			for (int i = 2; i <= totalPage; i++) {
				command.setPage(String.valueOf(i));
				PageResponse<CallInfo> next = ctiManager.queryCallInfo(command, callType);
				if (next == null) {
					continue;
				}
				
				List<CallInfo> nextEntities = next.getEntities();
				if (nextEntities == null) {
					return callInfos;
				}
				
				callInfos.addAll(nextEntities);
			}
		} catch (Exception e) {
			logger.info("获取"+ callType.getDesc() + "通话信息失败,失败信息:", e);
		}
		return callInfos;
	}

	/**
	 * 保存坐席通话信息结果
	 * @param callInfos
	 * @param agent
	 * @param callType
	 */
	private void saveAgentCallInfo(List<CallInfo> callInfos, Agent agent, CallType callType) {
		try {
			for (CallInfo callInfo : callInfos) {
				CollectorCallLog current;
				
				switch (callType) {
					case OUT:
						String customNo = callInfo.getCustomNo();
						if (!StringUtils.isEmpty(customNo)) {
							updateCallOutInfo(customNo, callInfo);
							continue;
						}
						// no break to fall through
					case IN:
						current = collectorCallLogRepository.findTopByCtiUuidAndCallType(callInfo.getCtiUuid(), callType);
						if (current != null) {
							continue;
						}
						break;
					default:
						break;
				}
				
				CollectorCallLog callLog = CollectorCallLog.buildFrom(callInfo);
				
				String targetTel = callLog.getTargetTel();
				targetTel = CtiManager.trimCtiCallInfoTel(targetTel);
				callLog.setTargetTel(targetTel);
				
				String location = mobileAddressService.getFullAddressByMobile(targetTel);
				callLog.setLocation(location);
				
				callLog.setCollectorId(agent.getCollectorId());
				callLog.setExtension(agent.getExtension());
				
				callLog.setId(generateId());
				collectorCallLogRepository.save(callLog);
			}
		} catch (Exception e) {
			logger.info("保存" + callType.getDesc() + "同步通话信息失败,失败信息:", e);
		}
	}

	/**
	 * 更新页面直接呼出请求的CTI通话信息
	 * @param customNo
	 * @param callInfo
	 */
	private void updateCallOutInfo(String customNo, CallInfo callInfo) {
		//排除旧mis系统的通话信息
		if (!StringUtils.isNumeric(customNo)) {
			return;
		}
		
		CollectorCallLog current = collectorCallLogRepository.findOne(Long.parseLong(customNo));
		if (current == null) {
			return;
		}
		
		if (current.getCtiUuid() != null) {
			return;
		}
		
		current.setCtiUuid(callInfo.getCtiUuid());
		current.setDialTime(callInfo.getDialTime());
		current.setRingTime(callInfo.getRingTime());
		current.setCallStartTime(callInfo.getCallStartTime());
		current.setCallEndTime(callInfo.getCallEndTime());
		current.setFinishTime(callInfo.getFinishTime());
		
		int durationTime = current.getCallStartTime() == null || current.getCallEndTime() == null ? 0
				: (int) (current.getCallEndTime().getTime() - current.getCallStartTime().getTime()) / 1000;
		current.setDurationTime(durationTime);
		
		collectorCallLogRepository.save(current);
		
	}
}
