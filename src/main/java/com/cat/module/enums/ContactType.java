package com.cat.module.enums;

public enum ContactType {
	SELF("本人"),
	ADDRESS_LIST("通讯录"),
	CALL_LOG("通话记录");


	private String desc;

	private ContactType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
