package com.cat.module.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AgentStatus {

	AVAILABLE("Available"),
	ON_BREAK("On Break"),
	LOGGED_OUT("Logged Out");

	private String value;

	private AgentStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@JsonCreator
	public static AgentStatus getAgentStatus(String name) {
		AgentStatus agentStatus = null;
		try {
			agentStatus = AgentStatus.valueOf(name);
		} catch (Exception e) {
			// invalid enum name
		}
		return agentStatus;
	}
}
