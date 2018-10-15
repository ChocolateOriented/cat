package com.cat.module.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ReliefReason {

	COMPENSATION("代偿"),
	CASH_PROBLEM("客户资金困难"),
	MULTI_DEBT("共债"),
	COMPLAINT("投诉"),
	SYSTEM_PROBLEM("系统问题"),
	OTHER("其他");

	private String desc;

	private ReliefReason(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	@JsonCreator
	public static ReliefReason getReliefReason(String name) {
		ReliefReason reliefReason = null;
		try {
			reliefReason = ReliefReason.valueOf(name);
		} catch (Exception e) {
			// invalid enum name
		}
		return reliefReason;
	}
}
