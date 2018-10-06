package com.cat.module.bean;

import java.io.Serializable;
import java.util.Date;

public class CallLogBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1740622520325003624L;
	
	private Long id;

	private String mobile;

	private String callTel;
	
	private String callDuration;//'通话总时长'
	
	private String callTime;//'通话起始时间'
	
	private String callnum;//'通话总次数'
	
//	private String full_name;//手机登记姓名'
//	private String address;//'手机登记地址'
//	private String id_card;//'手机登记身份证号'
//	private String call_method;//'呼叫类型'

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getCallnum() {
		return callnum;
	}

	public void setCallnum(String callnum) {
		this.callnum = callnum;
	}
	

	
	
}
