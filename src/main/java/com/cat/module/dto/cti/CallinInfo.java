package com.cat.module.dto.cti;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CTI呼入信息
 */
public class CallinInfo extends CallInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会话id
	 */
	private String sessionid;

	/**
	 * 来电号码
	 */
	private String caller;

	/**
	 * 号码来源
	 */
	private String callerRdnis;

	/**
	 * 坐席振铃时间  单位s
	 */
	private Long agentOfferTime;

	/**
	 * 坐席开始接听时间  单位s
	 */
	private Long agentStartTime;

	/**
	 * 坐席接听结束时间  单位s
	 */
	private Long agentEndTime;

	/**
	 * 坐席接听失败时间  单位s
	 */
	private Long agentFailTime;

	/**
	 * 队列名称
	 */
	private String queue;

	/**
	 * 入队列时间  单位s
	 */
	private Long inQueueTime;

	/**
	 * 出队列时间  单位s
	 */
	private Long outQueueTime;

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallerRdnis() {
		return callerRdnis;
	}

	@JsonProperty("caller_rdnis")
	public void setCallerRdnis(String callerRdnis) {
		this.callerRdnis = callerRdnis;
	}

	public Long getAgentOfferTime() {
		return agentOfferTime;
	}

	@JsonProperty("agent_offer_time")
	public void setAgentOfferTime(Long agentOfferTime) {
		this.agentOfferTime = agentOfferTime;
	}

	public Long getAgentStartTime() {
		return agentStartTime;
	}

	@JsonProperty("agent_start_time")
	public void setAgentStartTime(Long agentStartTime) {
		this.agentStartTime = agentStartTime;
	}

	public Long getAgentEndTime() {
		return agentEndTime;
	}

	@JsonProperty("agent_end_time")
	public void setAgentEndTime(Long agentEndTime) {
		this.agentEndTime = agentEndTime;
	}

	public Long getAgentFailTime() {
		return agentFailTime;
	}

	@JsonProperty("agent_fail_time")
	public void setAgentFailTime(Long agentFailTime) {
		this.agentFailTime = agentFailTime;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public Long getInQueueTime() {
		return inQueueTime;
	}

	@JsonProperty("in_queue_time")
	public void setInQueueTime(Long inQueueTime) {
		this.inQueueTime = inQueueTime;
	}

	public Long getOutQueueTime() {
		return outQueueTime;
	}

	@JsonProperty("out_queue_time")
	public void setOutQueueTime(Long outQueueTime) {
		this.outQueueTime = outQueueTime;
	}

	@Override
	public String getTarget() {
		return caller;
	}

	@Override
	public Date getDialTime() {
		return inQueueTime == null ? null : new Date(inQueueTime * 1000);
	}

	@Override
	public Date getRingTime() {
		return agentOfferTime == null ? null : new Date(agentOfferTime * 1000);
	}

	@Override
	public Date getCallStartTime() {
		return agentStartTime == null ? null : new Date(agentStartTime * 1000);
	}

	@Override
	public Date getCallEndTime() {
		return agentEndTime == null ? null : new Date(agentEndTime * 1000);
	}

	@Override
	public Date getFinishTime() {
		return outQueueTime == null ? null : new Date(outQueueTime * 1000);
	}

	@Override
	public String getCtiUuid() {
		return sessionid;
	}

	@Override
	public String getCustomNo() {
		return null;
	}

}
