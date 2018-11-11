package com.cat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cat.annotation.ClustersSchedule;
import com.cat.exception.ServiceException;
import com.cat.module.entity.Agent;
import com.cat.module.enums.AgentStatus;

@Service
public class AgentScheduleService extends BaseService {

	@Autowired
	private AgentService agentService;

	/**
	 * 每日定时将未手动离线的坐席变更为离线
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	@ClustersSchedule
	public void changeAgentToLoggedOut() {
		logger.info("开始定时同步坐席状态变为离线");
		List<Agent> agentList = agentService.findOnlineAgent();
		if (agentList.isEmpty()) {
			logger.info("今日无定时同步坐席状态变为离线");
			return;
		}
		for (Agent agent : agentList) {
			try {
				agentService.changAgentStatus(agent, AgentStatus.LOGGED_OUT);
			} catch (ServiceException e) {
				logger.error("定时同步坐席状态变为离线失败", e);
			}
			
		}
		logger.info("定时同步坐席状态变为离线,今日完成");
	}
}
