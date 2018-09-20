package com.cat.module.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private Long createBy;
	private Date createTime;
	private Long updateBy;
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "BaseEntity{" +
				"id=" + id +
				", createBy=" + createBy +
				", createTime=" + createTime +
				", updateBy=" + updateBy +
				", updateTime=" + updateTime +
				'}';
	}
}
