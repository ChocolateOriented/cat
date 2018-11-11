package com.cat.module.vo;

import java.util.Date;

import com.cat.module.enums.CallResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CollectorCallLogVo {

	private Long id;

	private String agent;

	private String extension;

	private String collectorId;

	private String callType;
	
	private String targetTel;

	private String targetName;

	private Date dialTime;

	private Integer callResult;

	private String orderId;
	
	private  String location;
	
	private Long dialTimeStart;
	
	private Long dialTimeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getTargetTel() {
		return targetTel;
	}

	public void setTargetTel(String targetTel) {
		this.targetTel = targetTel;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Date getDialTime() {
		return dialTime;
	}

	public void setDialTime(Date dialTime) {
		this.dialTime = dialTime;
	}

	public Integer getCallResult() {
		return callResult;
	}

	public void setCallResult(Integer callResult) {
		this.callResult = callResult;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDialTimeStart() {
		return this.dialTimeStart == null ? null : new Date(this.dialTimeStart);
	}

	public void setDialTimeStart(Long dialTimeStart) {
		this.dialTimeStart = dialTimeStart;
	}

	public Date getDialTimeEnd() {
		return this.dialTimeEnd == null ? null : new Date(this.dialTimeEnd);
	}

	public void setDialTimeEnd(Long dialTimeEnd) {
		this.dialTimeEnd = dialTimeEnd;
	}

	public String getCallResultDesc() {
		return callResult == null ? null : CallResult.valueOf(callResult).getDesc();
	}
	
}
