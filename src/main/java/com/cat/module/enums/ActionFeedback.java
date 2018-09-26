package com.cat.module.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActionFeedback {

	PTP("承诺还款(本人)"),
	PTPX("承诺还款(第三方)"),
	ALPA("声称已还"),
	KNOW("愿意还款"),
	FEE("费用减免"),
	INSY("无还款诚意"),
	BUSY("电话占线"),
	CUT("一接就挂"),
	NOAS("无人接听"),
	OFF("关机"),
	STOP("停机"),
	LOOO("空号"),
	MESF("传真"),
	MESS("转告"),
	NOTK("无法转告"),
	NOSE("查无此人");

	private String desc;

	private ActionFeedback(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	@JsonCreator
	public static ActionFeedback getActionFeedback(String name) {
		ActionFeedback actionFeedback = null;
		try {
			actionFeedback = ActionFeedback.valueOf(name);
		} catch (Exception e) {
			// invalid enum name
		}
		return actionFeedback;
	}
}
