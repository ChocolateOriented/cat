package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cat.module.enums.AgentStatus;

@Entity
@Table(name = "t_agent")
public class Agent extends AuditingEntity {

	@Id
	private Long id;

	private String agent;

	private String extension;

	private String queue;

	private String direct;

	private String collectorId;

	@Enumerated(EnumType.STRING)
	private AgentStatus status;

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

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public AgentStatus getStatus() {
		return status;
	}

	public void setStatus(AgentStatus status) {
		this.status = status;
	}

}
