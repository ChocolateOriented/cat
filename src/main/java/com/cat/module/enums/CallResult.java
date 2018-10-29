package com.cat.module.enums;

public enum CallResult {
	CUSTOMER_ANSWER(0, "用户接听"),
	CUSTOMER_UNANSWER(1, "用户未接"),
	COLLECTOR_ANSWER(2, "催收接听"),
	COLLECTOR_UNANSWER(3, "催收未接"),
	OUT_OF_QUEUE(4, "队列中放弃");

	private int value;

	private String desc;

	private CallResult(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static CallResult valueOf(int value) {
		for (CallResult contactType : CallResult.values()) {
			if (value == contactType.getValue()) {
				return contactType;
			}
		}
		
		return null;
	}
}
