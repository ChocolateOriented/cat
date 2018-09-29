package com.cat.module.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cat.module.enums.ActionFeedback;
import com.cat.module.enums.ContactType;

@Entity
@Table(name = "t_action")
public class ActionVo {

	@Id
	private Long id;

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
    private ActionFeedback actionFeedback;

    private String remark;

    private String updateBy;

    private Date updateTime;

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
		return updateBy;
	}

	public Long getOperateTime() {
		return updateTime == null ? null : updateTime.getTime();
	}
}
