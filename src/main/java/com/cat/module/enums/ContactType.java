package com.cat.module.enums;

public enum ContactType {
	SELF(0, "本人"),
	ADDRESS_LIST(1, "通讯录"),
	CALL_LOG(2, "通话记录");

	private int value;

	private String desc;

	private ContactType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static ContactType valueOf(int value) {
		for (ContactType contactType : ContactType.values()) {
			if (value == contactType.getValue()) {
				return contactType;
			}
		}
		
		return null;
	}
}
