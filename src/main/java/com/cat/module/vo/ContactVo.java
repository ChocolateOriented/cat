package com.cat.module.vo;

public class ContactVo {

	private String name;

	private String tel;
	
	private Integer  actionRecordNum;//'催记次数'	

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

	public Integer getActionRecordNum() {
		return actionRecordNum;
	}

	public void setActionRecordNum(Integer actionRecordNum) {
		this.actionRecordNum = actionRecordNum;
	}

}
