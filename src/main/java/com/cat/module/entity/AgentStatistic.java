package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_agent_statistic")
public class AgentStatistic {

	@Id
	private Long id;

	private Date date;

	private String agent;

	private String collectorId;

	private Date firstLoginTime;

	private Date lastLoginTime;

	private Date lastLogoutTime;

	private Integer accumulative_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Date getFirstLoginTime() {
		return firstLoginTime;
	}

	public void setFirstLoginTime(Date firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public Integer getAccumulative_time() {
		return accumulative_time;
	}

	public void setAccumulative_time(Integer accumulative_time) {
		this.accumulative_time = accumulative_time;
	}

}
