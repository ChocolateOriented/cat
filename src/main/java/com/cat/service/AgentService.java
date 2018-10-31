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

	/**
	 * 根据催收员Id查询坐席
	 * @param collectorId
	 * @return
	 */
	public Agent findTopByCollectorId(String collectorId) {
		return agentRepository.findTopByCollectorId(collectorId);
	}

	/**
	 * 查询所有在线坐席
	 * @return
	 */
	public List<Agent> findOnlineAgent() {
		return agentMapper.findOnlineAgent();
	}

	/**
	 * 变更坐席状态
	 * @param currentAgent
	 * @param newStatus
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void changAgentStatus(Agent currentAgent, AgentStatus newStatus) throws ServiceException {
		AgentStatistic agentStatistic = agentMapper.findByCollectorIdAndDate(currentAgent.getCollectorId());
		Date date = new Date();
		if (agentStatistic == null && newStatus != AgentStatus.LOGGED_OUT) {
			AgentStatistic	statistic = new AgentStatistic();
			statistic.setId(this.generateId());
			statistic.setAgent(currentAgent.getAgent());
			statistic.setCollectorId(currentAgent.getCollectorId());
			statistic.setFirstLoginTime(date);
			statistic.setLastLoginTime(date);
			statistic.setAccumulativeTime(0);
			agentMapper.insertAgentStatistic(statistic);
		}
		
		AgentStatus currentStatus = currentAgent.getStatus();
		if (agentStatistic != null ) {
			//在线变离线
			if ((currentStatus == AgentStatus.AVAILABLE || currentStatus == AgentStatus.ON_BREAK) 
					&& newStatus == AgentStatus.LOGGED_OUT ) {
				agentStatistic.setLastLogoutTime(date);
				int lineTime = (int) ((date.getTime() - agentStatistic.getLastLoginTime().getTime()) / 1000
						+ agentStatistic.getAccumulativeTime());
				agentStatistic.setAccumulativeTime(lineTime);
				agentStatistic.setLastLoginTime(null);
				agentMapper.updateAgentStatisticById(agentStatistic);
			}
			//离线变在线
			if ((newStatus == AgentStatus.AVAILABLE || newStatus == AgentStatus.ON_BREAK)
					&& currentStatus == AgentStatus.LOGGED_OUT) {
				agentStatistic.setLastLoginTime(date);
				agentMapper.updateAgentStatisticById(agentStatistic);
			}
		}
		
		agentMapper.updateAgentStatusById(newStatus,currentAgent.getId());
		
		AgentLoginLog agentLoginLog = new AgentLoginLog();
		agentLoginLog.setId(this.generateId());
		agentLoginLog.setAgent(currentAgent.getAgent());
		agentLoginLog.setCollectorId(currentAgent.getCollectorId());
		agentLoginLog.setStatus(newStatus);
		agentMapper.insertAgentLog(agentLoginLog);
		
		try {
			ctiManager.changeAgentStatus(currentAgent.getAgent(), newStatus);
		} catch (ApiException e) {
			logger.info("变更坐席状态失败：", e);
			throw new ServiceException("变更坐席状态失败");
		}
	}

	/**
	 * 查询催收员通话记录
	 * @param collectorCallLogVo
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public PageResponse<CollectorCallLogVo> listCollectorCallLog(CollectorCallLogVo collectorCallLogVo, Integer pageNum,
			Integer pageSize, String userId) {
		//进行查询
		PageHelper.startPage(pageNum, pageSize);
		collectorCallLogVo.setCollectorId(userId);
		List<CollectorCallLogVo> list = agentMapper.findCollectorCallLogList(collectorCallLogVo);
		PageInfo<CollectorCallLogVo> pageInfo = new PageInfo<>(list);
		return new PageResponse<CollectorCallLogVo>(list, pageNum, pageSize, pageInfo.getTotal());
	}

	/**
	 * 获取催收员坐席统计信息
	 * @param userId
	 * @return
	 */
	public AgentStatisticVo getAgentStatistic(String userId) {
		AgentStatisticVo agentStatisticVo = agentMapper.findCountCallLog(userId);
		agentStatisticVo = agentStatisticVo == null ? new AgentStatisticVo() : agentStatisticVo;
		
		if (agentStatisticVo.getCallOutNum() == null) {
			agentStatisticVo.setCallOutConnectRate(0);
		} else {
			agentStatisticVo.setCallOutConnectRate(agentStatisticVo.getCallOutConnectNum() * 100 / agentStatisticVo.getCallOutNum());
		}
		
		AgentStatistic agentStatistic = agentMapper.findByCollectorIdAndDate(userId);
		if (agentStatistic != null) {
			agentStatisticVo.setLoginTime(agentStatistic.getFirstLoginTime());
			Date lastLoginTime = agentStatistic.getLastLoginTime();
			
			if (lastLoginTime == null) {
				agentStatisticVo.setOnlineTime(agentStatistic.getAccumulativeTime());
			} else {
				Integer time = (int) ((System.currentTimeMillis() - lastLoginTime.getTime()) / 1000);
				agentStatisticVo.setOnlineTime(agentStatistic.getAccumulativeTime() + time);
			}
		}
		return agentStatisticVo;
	}

}
