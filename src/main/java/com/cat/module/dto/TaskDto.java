package com.cat.module.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

import com.cat.module.entity.BaseEntity;
import com.cat.module.enums.ActionCode;
import com.cat.util.NumberUtil;
import com.cat.util.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@Entity
public class TaskDto extends BaseEntity{
  
	private String  orderId;//订单ID - 业务流水号
	private String  name;//客户姓名
	private String  mobile;//用户手机号
	private String  orderStatus;//订单状态
	private BigDecimal  loanAmount;//欠款金额
	private Integer  repayAmount;//应催金额
	private Date repaymentTime; //到期还款日
	private Integer overdueDays;//逾期天数
	private String remark;//催收备注
	private String collectorName;//催收人
	private Date payoffTime;//还清日期
	private Long organizationId;//机构id
	private Long collectorId;//催收员id
	private ActionCode actionFeedbackType;//行动码
	
	//------------请求参数----------
	private Integer overdueDaysStart;//逾期天数第1个值
	private Integer overdueDaysEnd;//逾期天数第2个值
	private Date payoffTimeStart;//还清日期第一个值
	private Date payoffTimeEnd;//还清日期第二个值
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(Integer repayAmount) {
		this.repayAmount = repayAmount;
	}
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	public Integer getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCollectorName() {
		return collectorName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public Date getPayoffTime() {
		return payoffTime;
	}
	public void setPayoffTime(Date payoffTime) {
		this.payoffTime = payoffTime;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}
	public ActionCode getActionFeedbackType() {
		return actionFeedbackType;
	}
	public void setActionFeedbackType(ActionCode actionFeedbackType) {
		this.actionFeedbackType = actionFeedbackType;
	}
	public Integer getOverdueDaysStart() {
		return overdueDaysStart;
	}
	public void setOverdueDaysStart(Integer overdueDaysStart) {
		this.overdueDaysStart = overdueDaysStart;
	}
	public Integer getOverdueDaysEnd() {
		return overdueDaysEnd;
	}
	public void setOverdueDaysEnd(Integer overdueDaysEnd) {
		this.overdueDaysEnd = overdueDaysEnd;
	}
	public Date getPayoffTimeStart() {
		return payoffTimeStart;
	}
	public void setPayoffTimeStart(Date payoffTimeStart) {
		this.payoffTimeStart = payoffTimeStart;
	}
	public Date getPayoffTimeEnd() {
		return payoffTimeEnd;
	}
	public void setPayoffTimeEnd(Date payoffTimeEnd) {
		this.payoffTimeEnd = payoffTimeEnd;
	}
	
}
