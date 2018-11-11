package com.cat.module.dto.cti.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestCommand {

	/**
	 * 坐席绑定号码
	 */
	public static final String AGENT_BIND_EXTENSION = "agent_bind_extension";

	/**
	 * 坐席取消绑定号码
	 */
	public static final String AGENT_UNBIND_EXTENSION = "agent_unbind_extension";

	/**
	 * 坐席普通状态变更
	 */
	public static final String AGENT_STATUS_CHANGE = "agent_status_change";

	/**
	 * 发起呼叫
	 */
	public static final String ORIGINATE = "originate";

	/**
	 * 呼叫中断
	 */
	public static final String HANGUP = "hangup";

	/**
	 * 呼叫保持
	 */
	public static final String HOLD = "hold";

	/**
	 * 取消保持
	 */
	public static final String HOLD_OFF = "hold_off";

	/**
	 * 应答
	 */
	public static final String ANSWER = "answer";

	/**
	 * 呼叫转移
	 */
	public static final String TRANSFER = "transfer";

	/**
	 * 呼叫监听
	 */
	public static final String LIGNTEAVESDROP = "lignteavesdrop";

	/**
	 * 呼叫强插
	 */
	public static final String EAVESDROP = "eavesdrop";

	/**
	 * 获取坐席
	 */
	public static final String AGENT_INFO = "agent_info";

	/**
	 * 分机信息
	 */
	public static final String EXTENSION_INFO = "extension_info";

	/**
	 * 呼入信息
	 */
	public static final String CALLIN_INFO = "callin_info";

	/**
	 * 呼出信息
	 */
	public static final String CALLOUT_INFO = "callout_info";

	private String cmd;

	private String agent;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Map<String, Object> toParamMap() {
		Map<String, Object> map = new HashMap<>();
		
		Method[] methods = this.getClass().getMethods();
		for (Method m : methods) {
			String name = m.getName();
			if (!name.startsWith("get") || "getClass".equals(name)) {
				continue;
			}
			
			Object value = null;
			String key = null;
			key = name.substring(3, name.length());
			if (name.length() == 0) {
				continue;
			}
			
			try {
				value = m.invoke(this);
			} catch (IllegalAccessException e) {
				continue;
			} catch (InvocationTargetException e) {
				continue;
			}
			
			if (value == null || "".equals(value.toString())) {
				continue;
			}
			map.put(key.substring(0, 1).toLowerCase() + key.substring(1, key.length()), value.toString());
		}
		
		return map;
	}
}
