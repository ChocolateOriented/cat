package com.cat.mapper;

import java.util.List;

import com.cat.module.entity.Agent;
import com.cat.module.entity.AgentStatistic;
import com.cat.module.vo.CollectorCallLogVo;

public interface AgentMapper {

	void updateAgentById(Agent currentAgent);

	void insertAgentLog(Agent currentAgent);

	AgentStatistic findByCollectorIdAndDate(String collectorId);

	void insertAgentStatistic(AgentStatistic agentStatistic);

	void updateAgentStatisticById(AgentStatistic agentStatistic);

	List<CollectorCallLogVo> findCollectorCallLogList(CollectorCallLogVo collectorCallLogVo);
	
}
