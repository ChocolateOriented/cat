package com.cat.module.enums;

public enum ContactTargetType implements BaseCodeEnum {
	SELF(0, "本人"),
	ADDRESS_LIST(1, "通讯录"),
	CALL_LOG(2, "通话记录");

	private int value;

	private String desc;

	private ContactTargetType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
