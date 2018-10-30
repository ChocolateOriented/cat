package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cat.module.dto.cti.CallInfo;
import com.cat.module.dto.cti.CallinInfo;
import com.cat.module.enums.CallResult;
import com.cat.module.enums.CallType;

@Entity
@Table(name = "t_collector_call_log")
public class CollectorCallLog extends AuditingEntity {

	@Id
	private Long id;

	private String agent;

	private String extension;

	private String collectorId;

	private CallType callType;

	@NotNull(message = "呼叫号码不能为空")
	private String targetTel;

	private String targetName;

	private String location;

	private String customerNo;

	private Date dialTime;

	private Date ringTime;

	private Date callStartTime;

	private Date callEndTime;

	private Date finishTime;

	private Integer durationTime;

	private Integer callResult;

	private String ctiUuid;

	private String orderId;

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

	public CallType getCallType() {
		return callType;
	}

	public void setCallType(CallType callType) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Date getDialTime() {
		return dialTime;
	}

	public void setDialTime(Date dialTime) {
		this.dialTime = dialTime;
	}

	public Date getRingTime() {
		return ringTime;
	}

	public void setRingTime(Date ringTime) {
		this.ringTime = ringTime;
	}

	public Date getCallStartTime() {
		return callStartTime;
	}

	public void setCallStartTime(Date callStartTime) {
		this.callStartTime = callStartTime;
	}

	public Date getCallEndTime() {
		return callEndTime;
	}

	public void setCallEndTime(Date callEndTime) {
		this.callEndTime = callEndTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}

	public Integer getCallResult() {
		return callResult;
	}

	public void setCallResult(Integer callResult) {
		this.callResult = callResult;
	}

	public String getCallResultDesc() {
		return callResult == null ? null : CallResult.valueOf(callResult).getDesc();
	}

	public String getCtiUuid() {
		return ctiUuid;
	}

	public void setCtiUuid(String ctiUuid) {
		this.ctiUuid = ctiUuid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public static CollectorCallLog buildFrom(CallInfo callInfo) {
		CollectorCallLog callLog = new CollectorCallLog();
		callLog.agent = callInfo.getAgent();
		callLog.callType = callInfo instanceof CallinInfo ? CallType.IN : CallType.OUT;
		callLog.extension = callInfo.getExtension();
		callLog.customerNo = callInfo.getCustomNo();
		callLog.targetTel = callInfo.getTarget();
		callLog.ctiUuid = callInfo.getCtiUuid();
		callLog.dialTime = callInfo.getDialTime();
		callLog.ringTime = callInfo.getRingTime();
		callLog.callStartTime = callInfo.getCallStartTime();
		callLog.callEndTime = callInfo.getCallEndTime();
		callLog.finishTime = callInfo.getFinishTime();
		
		int durationTime = callLog.callStartTime == null || callLog.callEndTime == null ? 0
				: (int) (callLog.callEndTime.getTime() - callLog.callStartTime.getTime()) / 1000;
		callLog.durationTime = durationTime;
		return callLog;
	}
}
