package com.cat.module.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.cat.module.enums.ReliefReason;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_relief")
@JsonIgnoreProperties({"createBy", "createTime", "updateBy", "updateTime", "collectorId", "collectorName"})
public class Relief extends AuditingEntity {

	@Id
	private Long id;

	@NotBlank(message = "订单号不能为空")
	private String orderId;

	private String collectorId;

	private String collectorName;

	@Min(value = 0, message = "减免金额不能小于0")
	private BigDecimal reliefAmount;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "无效的减免原因")
	private ReliefReason reliefReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public BigDecimal getReliefAmount() {
		return reliefAmount;
	}

	public void setReliefAmount(BigDecimal reliefAmount) {
		this.reliefAmount = reliefAmount;
	}

	public ReliefReason getReliefReason() {
		return reliefReason;
	}

	public void setReliefReason(ReliefReason reliefReason) {
		this.reliefReason = reliefReason;
	}

	public String getReliefReasonDesc() {
		return reliefReason == null ? null : reliefReason.getDesc();
	}

	public String getOperatorName() {
		return getUpdateBy();
	}

	public Long getOperateTime() {
		return getUpdateTime() == null ? null : getUpdateTime().getTime();
	}
}
