package com.cat.module.enums;

public enum CallType {

	IN("呼入"),
	OUT("呼出");

	private String desc;

	private CallType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
