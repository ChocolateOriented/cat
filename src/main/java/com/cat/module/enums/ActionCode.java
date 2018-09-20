package com.cat.module.enums;

public enum ActionCode {

	ALPA("声称已还"),
	BUSY("电话占线"),
	CUT("一接就挂"),
	FEE("费用减免"),
	INSY("无还款诚意"),
	KNOW("愿意还款"),
	LOOO("空号"),
	MESF("传真"),
	MESS("转告"),
	NOAS("无人接听"),
	NOSE("查无此人"),
	NOTK("无法转告"),
	OFF("关机"),
	PTP("承诺还款(本人)"),
	PTPX("承诺还款(第三方)"),
	STOP("停机");

	private String desc;

	private ActionCode(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}