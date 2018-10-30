package com.cat.module.vo;

import java.util.Date;

public class AgentStatisticVo {

	
	private Date loginTime;//登录时间
	
	private Integer onlineTime;//在线时长
	
	private Integer  callOutNum;//呼出总量
	
	private Integer  callOutConnectNum;//接通总量
	
	private String  callOutConnectRate;//接通率	

	public Long getLoginTime() {
		return  loginTime == null ? null : loginTime.getTime();
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getOnlineTime() {
		if(this.onlineTime == null || this.onlineTime == 0){
			return "0:0:0";
		}
		Integer hour = onlineTime /3600; 
		Integer minute = onlineTime % 3600 / 60;
		Integer second = onlineTime % 3600 / 60 %60 ;
		return hour+":"+minute+":"+second;
	}

	public void setOnlineTime(Integer onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getCallOutNum() {
		return callOutNum;
	}

	public void setCallOutNum(Integer callOutNum) {
		this.callOutNum = callOutNum;
	}

	public Integer getCallOutConnectNum() {
		return callOutConnectNum;
	}

	public void setCallOutConnectNum(Integer callOutConnectNum) {
		this.callOutConnectNum = callOutConnectNum;
	}

	public String getCallOutConnectRate() {
		return callOutConnectRate;
	}

	public void setCallOutConnectRate(String callOutConnectRate) {
		this.callOutConnectRate = callOutConnectRate;
	}


}
