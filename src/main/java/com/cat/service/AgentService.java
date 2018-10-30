package com.cat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.CtiManager;
import com.cat.mapper.AgentMapper;
import com.cat.module.dto.PageResponse;
import com.cat.module.entity.Agent;
import com.cat.module.entity.AgentStatistic;
import com.cat.module.enums.AgentStatus;
import com.cat.module.vo.AgentStatisticVo;
import com.cat.module.vo.CollectorCallLogVo;
import com.cat.repository.AgentRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AgentService extends BaseService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CtiManager ctiManager;
	@Autowired
	private AgentMapper agentMapper;

	public Agent findTopByCollectorId(String collectorId) {
		return agentRepository.findTopByCollectorId(collectorId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void changAgentStatus(Agent currentAgent, AgentStatus newStatus) throws ServiceException {
		try {
			ctiManager.changeAgentStatus(currentAgent.getAgent(), newStatus);
		} catch (ApiException e) {
			logger.info("变更坐席状态失败：", e);
			throw new ServiceException("变更坐席状态失败");
		}
		AgentStatistic agentStatistic = agentMapper.findByCollectorIdAndDate(currentAgent.getCollectorId());
		Date date = new Date();
		if(agentStatistic == null && newStatus != AgentStatus.LOGGED_OUT){
			agentStatistic = new AgentStatistic();
			agentStatistic.setId(this.generateId());
			agentStatistic.setAgent(currentAgent.getAgent());
			agentStatistic.setFirstLoginTime(date);
			agentStatistic.setLastLoginTime(date);
			agentStatistic.setAccumulativeTime(0);
			agentMapper.insertAgentStatistic(agentStatistic);
		}
		AgentStatus currentStatus = currentAgent.getStatus();
		if(agentStatistic != null ){
			//在线变离线
			if((currentStatus == AgentStatus.AVAILABLE || currentStatus == AgentStatus.ON_BREAK) 
					&& newStatus == AgentStatus.LOGGED_OUT ){
				agentStatistic.setLastLogoutTime(date);
				int lineTime = (int) ((date.getTime()-agentStatistic.getLastLoginTime().getTime())/1000 + agentStatistic.getAccumulativeTime());
				agentStatistic.setAccumulativeTime(lineTime);
				agentStatistic.setLastLoginTime(null);
				
			}
			//离线变在线
			if((newStatus == AgentStatus.AVAILABLE || newStatus == AgentStatus.ON_BREAK) 
					&& currentStatus == AgentStatus.LOGGED_OUT ){
				agentStatistic.setLastLoginTime(date);
			}
			agentMapper.updateAgentStatisticById(agentStatistic);
		}
		currentAgent.setStatus(newStatus);
		agentMapper.updateAgentById(currentAgent);
		currentAgent.setId(this.generateId());
		agentMapper.insertAgentLog(currentAgent);
	}

	public PageResponse<CollectorCallLogVo> list(CollectorCallLogVo collectorCallLogVo, Integer pageNum,
			Integer pageSize, String userId) {
		//进行查询
		PageHelper.startPage(pageNum, pageSize);
		collectorCallLogVo.setCollectorId(userId);
		List<CollectorCallLogVo> list = agentMapper.findCollectorCallLogList(collectorCallLogVo);
		PageInfo<CollectorCallLogVo> pageInfo = new PageInfo<>(list);
		return new PageResponse<CollectorCallLogVo>(list, pageNum, pageSize, pageInfo.getTotal());
	}

	public AgentStatisticVo getAgentStatistic(String userId) {
		AgentStatisticVo agentStatisticVo = new AgentStatisticVo();
		AgentStatistic agentStatistic = agentMapper.findByCollectorIdAndDate(userId);
		if(agentStatistic == null){
			return agentStatisticVo;
		}
		agentStatisticVo.setLoginTime(agentStatistic.getFirstLoginTime());
		Date lastLoginTime = agentStatistic.getLastLoginTime();
		if(lastLoginTime == null){
			agentStatisticVo.setOnlineTime(agentStatistic.getAccumulativeTime());
		}else{
			Integer time = (int) (new Date().getTime()-lastLoginTime.getTime());
			agentStatisticVo.setOnlineTime(agentStatistic.getAccumulativeTime()+time);
		}
		return agentStatisticVo;
	}

}
