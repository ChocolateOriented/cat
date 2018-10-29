package com.cat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.CtiManager;
import com.cat.module.entity.Agent;
import com.cat.module.enums.AgentStatus;
import com.cat.repository.AgentRepository;

@Service
public class AgentService extends BaseService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CtiManager ctiManager;

	public Agent findTopByCollectorId(String collectorId) {
		return agentRepository.findTopByCollectorId(collectorId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void changAgentStatus(Agent currentAgent, AgentStatus newStatus) throws ServiceException {
		// TODO update datebase
		try {
			ctiManager.changeAgentStatus(currentAgent.getAgent(), newStatus);
		} catch (ApiException e) {
			logger.info("变更坐席状态失败：", e);
			throw new ServiceException("变更坐席状态失败");
		}
	}
}
