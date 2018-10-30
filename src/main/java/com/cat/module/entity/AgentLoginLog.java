package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.cat.module.enums.AgentStatus;

@Entity
@Table(name = "t_agent_login_log")
public class AgentLoginLog {

	@Id
	private Long id;

	private String agent;

	private String collectorId;

	private AgentStatus status;

	@CreatedDate
	private Date time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
