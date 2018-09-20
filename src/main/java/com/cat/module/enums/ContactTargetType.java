package com.cat.module.enums;

public enum ContactTargetType {
	SELF("本人"),
	ADDRESS_LIST("通讯录"),
	CALL_LOG("通话记录");


	private String desc;

	private ContactTargetType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
