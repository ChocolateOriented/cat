package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.cat.annotation.ContactTypeConstraint;
import com.cat.module.enums.ActionFeedback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_action")
@JsonIgnoreProperties({"createBy", "createTime", "updateBy", "updateTime"})
public class Action extends AuditingEntity {

	@Id
	private Long id;

	@NotBlank(message = "订单号不能为空")
	private String orderId;

    private String customerId;

    private String collectorId;

    private String collectorName;

	@NotNull(message = "联系人电话不能为空")
    private String contactTel;

    private String contactName;

    @ContactTypeConstraint
    private Integer contactType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "无效的行动码")
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

	public ActionFeedback getActionFeedback() {
		return actionFeedback;
	}

	public void setActionFeedback(ActionFeedback actionFeedback) {
		this.actionFeedback = actionFeedback;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperatorName() {
		return getUpdateBy();
	}

	public Long getOperateTime() {
		return getUpdateTime() == null ? null : getUpdateTime().getTime();
	}
	public String getActionFeedbackDesc() {
		return actionFeedback == null ? null : actionFeedback.getDesc();
	}

}