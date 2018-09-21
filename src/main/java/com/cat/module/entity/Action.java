package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cat.module.enums.ActionCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_action")
@JsonIgnoreProperties({"createBy", "createTime", "updateBy", "updateTime"})
public class Action extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String orderId;

	private String orderStatus;

	private Date repaymentTime;

    private String customerId;

    private String collectorId;

    private String collectorName;

    private String contactTel;

    private String contactName;

    private Integer contactType;

    @Enumerated(EnumType.STRING)
    private ActionCode actionFeedback;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getRepaymentTime() {
		return repaymentTime == null ? null : repaymentTime.getTime();
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public ActionCode getActionFeedback() {
		return actionFeedback;
	}

	public void setActionFeedback(ActionCode actionFeedback) {
		this.actionFeedback = actionFeedback;
	}

	public String getOperatorName() {
		return super.getUpdateBy();
	}

	public Long getOperatorTime() {
		return super.getUpdateTime() == null ? null : super.getUpdateTime().getTime();
	}
}