package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
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

	public void supplementFromCallInfo(CallInfo callInfo) {
		this.ctiUuid = callInfo.getCtiUuid();
		this.dialTime = callInfo.getDialTime();
		this.ringTime = callInfo.getRingTime();
		this.callStartTime = callInfo.getCallStartTime();
		this.callEndTime = callInfo.getCallEndTime();
		this.finishTime = callInfo.getFinishTime();
		
		int durationTime = this.callStartTime == null || this.callEndTime == null ? 0
				: (int) (this.callEndTime.getTime() - this.callStartTime.getTime()) / 1000;
		this.durationTime = durationTime;
		
		if (this.callType == CallType.IN) {
			if (this.callEndTime != null) {
				this.callResult = CallResult.COLLECTOR_ANSWER.getValue();
			} else if (this.ringTime != null) {
				this.callResult = CallResult.COLLECTOR_UNANSWER.getValue();
			} else {
				this.callResult = CallResult.OUT_OF_QUEUE.getValue();
			}
		} else {
			if (this.callEndTime != null) {
				this.callResult = CallResult.CUSTOMER_ANSWER.getValue();
			} else {
				this.callResult = CallResult.CUSTOMER_UNANSWER.getValue();
			}
		}
	}

	public static CollectorCallLog buildFromCallInfo(CallInfo callInfo) {
		CollectorCallLog callLog = new CollectorCallLog();
		callLog.agent = callInfo.getAgent();
		callLog.callType = callInfo instanceof CallinInfo ? CallType.IN : CallType.OUT;
		callLog.extension = callInfo.getExtension();
		callLog.customerNo = callInfo.getCustomNo();
		callLog.targetTel = callInfo.getTarget();
		
		callLog.supplementFromCallInfo(callInfo);
		return callLog;
	}
}
