package com.cat.module.entity.risk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_risk_call_log")
public class CallLog {

	@Id
	@GeneratedValue
	private Long id;

	private String mobile;

	private String callTel;

	private String callMethod;

	private Integer callDuration;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCallTel() {
		return callTel;
	}

	public void setCallTel(String callTel) {
		this.callTel = callTel;
	}

	public String getCallMethod() {
		return callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}

	public Integer getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(Integer callDuration) {
		this.callDuration = callDuration;
	}
	
}
