package com.cat.module.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.cat.annotation.ActionFeedbackConstraint;
import com.cat.annotation.ContactTypeConstraint;
import com.cat.module.enums.ActionFeedback;
import com.cat.module.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_action")
@JsonIgnoreProperties({"createBy", "createTime", "updateBy", "updateTime"})
public class Action extends AuditingEntity {

	@Id
	private Long id;

	@NotBlank(message = "订单号不能为空")
	private String orderId;

	@Transient
	private String orderStatus;

	@Transient
	private Date repaymentTime;

    private String customerId;

    private String collectorId;

    private String collectorName;

	@NotNull(message = "联系人电话不能为空")
    private String contactTel;

    private String contactName;

    @ContactTypeConstraint
    private Integer contactType;

    @Enumerated(EnumType.STRING)
    @ActionFeedbackConstraint
    private ActionFeedback actionFeedback;

    private String remark;

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

	public String getContactTypeDesc() {
		if (contactType == null) {
			return null;
		}
		
		ContactType contactTypeEnum = ContactType.valueOf(contactType);
		return contactTypeEnum == null ? null : contactTypeEnum.getDesc();
	}

	public ActionFeedback getActionFeedback() {
		return actionFeedback;
	}

	public void setActionFeedback(ActionFeedback actionFeedback) {
		this.actionFeedback = actionFeedback;
	}

	public String getActionFeedbackDesc() {
		return actionFeedback == null ? null : actionFeedback.getDesc();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorName() {
		return super.getUpdateBy();
	}

	public Long getOperateTime() {
		return super.getUpdateTime() == null ? null : super.getUpdateTime().getTime();
	}
}