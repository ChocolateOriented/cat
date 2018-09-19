package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Task extends BaseEntity{
	
	private String  orderId;//订单ID - 业务流水号
	private String  ownerId;//用户code
	private String  orderType;//订单类型
	private String  productType;//产品类型
	private String  orderStatus;//订单状态
	private BigDecimal  loanNumber;//借贷金额
	private Integer  loanTerm;//借贷期限
	private BigDecimal  lentNumber;//放款金额
	private String  interestMode;//利息模式
	private BigDecimal  interestValue;//利息值
	private String  penaltyMode;//罚息模式
	private BigDecimal  penaltyValue;//罚息值
	private BigDecimal  reliefAmount;//减免金额
	private BigDecimal  chargeValue;//服务费值
	private BigDecimal  postponeUnitCharge;//延期单位服务费
	private Integer  postponeCount;//延期次数
	private Date  lendTime;//放款时间
	private Date  payoffTime;//还清时间
	private Date  repaymentRime;//还款日
	private String  dunningPeopleId;//催讨人id
	private String  dunningPeopleName;//催讨人名
	private Date  taskStartTime;//任务起始时间
	private Date  taskEndTime;//任务结束时间
	private Integer  dunningPeriodBegin;//催讨周期-逾期周期起始
	private Integer  dunningPeriodEnd;//催讨周期-逾期周期截至
	private String  dunningTaskStatus;//催款任务状态
	private String  dunningTelRemark;//催收备注
	private Date  dunningTime;//操作时间
	private String  dunningCycle;//催收队列
	private String  remark;//备注
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(BigDecimal loanNumber) {
		this.loanNumber = loanNumber;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public BigDecimal getLentNumber() {
		return lentNumber;
	}
	public void setLentNumber(BigDecimal lentNumber) {
		this.lentNumber = lentNumber;
	}
	public String getInterestMode() {
		return interestMode;
	}
	public void setInterestMode(String interestMode) {
		this.interestMode = interestMode;
	}
	public BigDecimal getInterestValue() {
		return interestValue;
	}
	public void setInterestValue(BigDecimal interestValue) {
		this.interestValue = interestValue;
	}
	public String getPenaltyMode() {
		return penaltyMode;
	}
	public void setPenaltyMode(String penaltyMode) {
		this.penaltyMode = penaltyMode;
	}
	public BigDecimal getPenaltyValue() {
		return penaltyValue;
	}
	public void setPenaltyValue(BigDecimal penaltyValue) {
		this.penaltyValue = penaltyValue;
	}
	public BigDecimal getReliefAmount() {
		return reliefAmount;
	}
	public void setReliefAmount(BigDecimal reliefAmount) {
		this.reliefAmount = reliefAmount;
	}
	public BigDecimal getChargeValue() {
		return chargeValue;
	}
	public void setChargeValue(BigDecimal chargeValue) {
		this.chargeValue = chargeValue;
	}
	public BigDecimal getPostponeUnitCharge() {
		return postponeUnitCharge;
	}
	public void setPostponeUnitCharge(BigDecimal postponeUnitCharge) {
		this.postponeUnitCharge = postponeUnitCharge;
	}
	public Integer getPostponeCount() {
		return postponeCount;
	}
	public void setPostponeCount(Integer postponeCount) {
		this.postponeCount = postponeCount;
	}
	public Date getLendTime() {
		return lendTime;
	}
	public void setLendTime(Date lendTime) {
		this.lendTime = lendTime;
	}
	public Date getPayoffTime() {
		return payoffTime;
	}
	public void setPayoffTime(Date payoffTime) {
		this.payoffTime = payoffTime;
	}
	public Date getRepaymentRime() {
		return repaymentRime;
	}
	public void setRepaymentRime(Date repaymentRime) {
		this.repaymentRime = repaymentRime;
	}
	public String getDunningPeopleId() {
		return dunningPeopleId;
	}
	public void setDunningPeopleId(String dunningPeopleId) {
		this.dunningPeopleId = dunningPeopleId;
	}
	public String getDunningPeopleName() {
		return dunningPeopleName;
	}
	public void setDunningPeopleName(String dunningPeopleName) {
		this.dunningPeopleName = dunningPeopleName;
	}
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public Integer getDunningPeriodBegin() {
		return dunningPeriodBegin;
	}
	public void setDunningPeriodBegin(Integer dunningPeriodBegin) {
		this.dunningPeriodBegin = dunningPeriodBegin;
	}
	public Integer getDunningPeriodEnd() {
		return dunningPeriodEnd;
	}
	public void setDunningPeriodEnd(Integer dunningPeriodEnd) {
		this.dunningPeriodEnd = dunningPeriodEnd;
	}
	public String getDunningTaskStatus() {
		return dunningTaskStatus;
	}
	public void setDunningTaskStatus(String dunningTaskStatus) {
		this.dunningTaskStatus = dunningTaskStatus;
	}
	public String getDunningTelRemark() {
		return dunningTelRemark;
	}
	public void setDunningTelRemark(String dunningTelRemark) {
		this.dunningTelRemark = dunningTelRemark;
	}
	public Date getDunningTime() {
		return dunningTime;
	}
	public void setDunningTime(Date dunningTime) {
		this.dunningTime = dunningTime;
	}
	public String getDunningCycle() {
		return dunningCycle;
	}
	public void setDunningCycle(String dunningCycle) {
		this.dunningCycle = dunningCycle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
