package com.cat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cat.module.entity.Agent;
import com.cat.module.entity.AgentLoginLog;
import com.cat.module.entity.AgentStatistic;
import com.cat.module.enums.AgentStatus;
import com.cat.module.vo.AgentStatisticVo;
import com.cat.module.vo.CollectorCallLogVo;

public interface AgentMapper {

	void updateAgentById(Agent currentAgent);

	void insertAgentLog(AgentLoginLog agentLoginLog);

	AgentStatistic findByCollectorIdAndDate(String collectorId);

	void insertAgentStatistic(AgentStatistic agentStatistic);

	void updateAgentStatisticById(AgentStatistic agentStatistic);

	List<CollectorCallLogVo> findCollectorCallLogList(CollectorCallLogVo collectorCallLogVo);

	AgentStatisticVo findCountCallLog(String userId);

	List<Agent> findOnlineAgent();

	void updateAgentStatusById(@Param("status")AgentStatus status,@Param("id") Long id);
	
}
