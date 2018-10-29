package com.cat.module.dto.cti;

/**
 * CTI呼叫信息抽象类
 */
public abstract class CallInfo {

	/**
	 * 记录id
	 */
	private String id;

	/**
	 * 坐席
	 */
	private String agent;

	/**
	 * 分机号码
	 */
	private String extension;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 获取呼叫发起时间
	 */
	public abstract long getDialTime();

	/**
	 * 获取响铃时间
	 */
	public abstract long getRingTime();

	/**
	 * 获取通话开始时间
	 */
	public abstract long getCallStartTime();

	/**
	 * 获取通话结束时间
	 */
	public abstract long getCallEndTime();

	/**
	 * 获取呼叫结束时间
	 */
	public abstract long getFinishTime();
}
