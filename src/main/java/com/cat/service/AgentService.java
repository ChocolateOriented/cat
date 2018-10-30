package com.cat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.annotation.ClustersSchedule;
import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.CtiManager;
import com.cat.mapper.AgentMapper;
import com.cat.module.dto.PageResponse;
import com.cat.module.entity.Agent;
import com.cat.module.entity.AgentLoginLog;
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
			AgentStatistic	statistic = new AgentStatistic();
			statistic.setId(this.generateId());
			statistic.setAgent(currentAgent.getAgent());
			statistic.setFirstLoginTime(date);
			statistic.setLastLoginTime(date);
			statistic.setAccumulativeTime(0);
			agentMapper.insertAgentStatistic(statistic);
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
		
		AgentLoginLog agentLoginLog = new AgentLoginLog();
		agentLoginLog.setId(this.generateId());
		agentLoginLog.setAgent(currentAgent.getAgent());
		agentLoginLog.setCollectorId(currentAgent.getCollectorId());
		agentLoginLog.setStatus(newStatus);
		agentMapper.insertAgentLog(agentLoginLog);
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
		AgentStatistic agentStatistic = agentMapper.findByCollectorIdAndDate(userId);
		if(agentStatistic == null){
			return null;
		}
		AgentStatisticVo agentStatisticVo = agentMapper.findCountCallLog(userId);
		agentStatisticVo =	agentStatisticVo == null ? new AgentStatisticVo() :agentStatisticVo;
		if(agentStatisticVo.getCallOutNum() == null){
			agentStatisticVo.setCallOutConnectRate("0%");
		}else{
			agentStatisticVo.setCallOutConnectRate(agentStatisticVo.getCallOutConnectNum() /agentStatisticVo.getCallOutNum()+"%");
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
	@Scheduled(cron = "23 23 23 * * ?")
	@ClustersSchedule
	public void syncCallInfo5Minutely() {
		logger.info("开始定时同步坐席状态变为离线");
		List<Agent> agentList = agentMapper.findOnlineAgent();
		if(agentList.isEmpty()){
			logger.info("今日无定时同步坐席状态变为离线");
			return;
		}
		for (Agent agent : agentList) {
			try {
				this.changAgentStatus(agent,AgentStatus.LOGGED_OUT);
			} catch (ServiceException e) {
				logger.error("定时同步坐席状态变为离线失败",e);
			}
			
		}
		logger.info("定时同步坐席状态变为离线,今日完成");
	}
}
