package com.cat.module.vo;

public class CallLogVo {

	private String name;

	private String tel;
	
	private Long callDuration;//'通话总时长'
	
	private String callNum;//'通话总次数'	
	
	private String  actionRecordNum;//'催记次数'	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCallDuration() {
		if(this.callDuration == null || this.callDuration == 0){
			return "0时0分0秒";
		}
		Long hour = callDuration /3600; 
		Long minute = callDuration % 3600 / 60;
		Long second = callDuration % 3600 / 60 %60 ;
		return hour+"时"+minute+"分"+second+"秒";
	}

	public void setCallDuration(Long callDuration) {
		this.callDuration = callDuration;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getActionRecordNum() {
		return actionRecordNum;
	}

	public void setActionRecordNum(String actionRecordNum) {
		this.actionRecordNum = actionRecordNum;
	}
	
  public static void main(String[] args) {
	System.out.println(10675%3600);
	System.out.println(10675/3600);
}
}
