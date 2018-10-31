package com.cat.module.dto.cti;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CTI呼出信息
 */
public class CalloutInfo extends CallInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 目标号码
	 */
	private String target;

	/**
	 * 创建时间  单位s
	 */
	private Long channelCreateTime;

	/**
	 * 应答时间  单位s
	 */
	private Long channelAnswerTime;

	/**
	 * 关断时间  单位s
	 */
	private Long channelHangupTime;

	/**
	 * 分机uuid
	 */
	private String euuid;

	/**
	 * 目标uuid
	 */
	private String tuuid;

	/*
	 * 自定义编号
	 */
	private String customerno;

	private String agentState;

	public String getAgentState() {
		return agentState;
	}

	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}

	@Override
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Long getChannelCreateTime() {
		return channelCreateTime;
	}

	@JsonProperty("channelcreatetime")
	public void setChannelCreateTime(Long channelCreateTime) {
		this.channelCreateTime = channelCreateTime;
	}

	public Long getChannelAnswerTime() {
		return channelAnswerTime;
	}

	@JsonProperty("channelanswertime")
	public void setChannelAnswerTime(Long channelAnswerTime) {
		this.channelAnswerTime = channelAnswerTime;
	}

	public Long getChannelHangupTime() {
		return channelHangupTime;
	}

	@JsonProperty("channelhanguptime")
	public void setChannelHangupTime(Long channelHangupTime) {
		this.channelHangupTime = channelHangupTime;
	}

	public String getEuuid() {
		return euuid;
	}

	public void setEuuid(String euuid) {
		this.euuid = euuid;
	}

	public String getTuuid() {
		return tuuid;
	}

	public void setTuuid(String tuuid) {
		this.tuuid = tuuid;
	}

	public String getCustomerno() {
		return customerno;
	}

	public void setCustomerno(String customerno) {
		this.customerno = customerno;
	}

	@Override
	public Date getDialTime() {
		return channelCreateTime == null || channelCreateTime == 0 ? null : new Date(channelCreateTime * 1000);
	}

	@Override
	public Date getRingTime() {
		return channelCreateTime == null || channelCreateTime == 0 ? null : new Date(channelCreateTime * 1000);
	}

	@Override
	public Date getCallStartTime() {
		return channelAnswerTime == null || channelAnswerTime == 0 ? null : new Date(channelAnswerTime * 1000);
	}

	@Override
	public Date getCallEndTime() {
		return channelHangupTime == null || channelHangupTime == 0 || channelAnswerTime == null || channelAnswerTime == 0
				? null : new Date(channelHangupTime * 1000);
	}

	@Override
	public Date getFinishTime() {
		return channelHangupTime == null || channelHangupTime == 0 ? null : new Date(channelHangupTime * 1000);
	}

	@Override
	public String getCtiUuid() {
		return euuid;
	}

	@Override
	public String getCustomNo() {
		return customerno;
	}

}
