/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 催收任务logEntity
 * @author 徐盛
 * @version 2017-03-01
 */
public class TaskLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long dbid;		// dbid
	private String orderId;		// 订单号
	private String dunningPeopleId;		// 催收人id
	private String dunningPeopleName;		// 催收人姓名
	private String dunningCycle;		// 催收周期(队列)
		
	private String orderStatus;		// 订单状态
	private Date payoffTime;		// 还清时间
	private Integer loanTerm;		// 借贷期限
	private String  ownerId;//用户code
	private String ownerName;		// 用户姓名
	private String mobile;		// 手机号
	private Date repaymentTime;		// 到期还款日期
	private BigDecimal loanNumber;		// 本金
	
	private BigDecimal  lentNumber;//放款金额
	private String  interestMode;//利息模式
	private BigDecimal  interestValue;//利息值
	private String  penaltyMode;//罚息模式
	private BigDecimal  penaltyValue;//罚息值
	private BigDecimal  reliefAmount;//减免金额
	private BigDecimal  chargeValue;//服务费值
	private BigDecimal  postponeUnitCharge;//延期单位服务费
	private Integer  postponeCount;//延期次数
	
		private String behaviorStatus;		// 催收员行为状态（in,out,finished,partial,postpone）
		private String  dunningTaskStatus;//催款任务状态(未开启任务，任务进行中，任务结束，延期)
		private Long taskId;	 // 任务ID
		private Integer overdueDays;		// 逾期天数========需要计算
	
	private String platformext; // 渠道
	private Integer creditamount;		// 实际应还金额 (当前应催金额)


	public TaskLog() {
		super();
	}

	
	public TaskLog(String orderId,String dunningPeopleId,String dunningPeopleName,String dunningCycle,String behaviorStatus){
		this.orderId = orderId;
		this.dunningPeopleId = dunningPeopleId;
		this.dunningPeopleName = dunningPeopleName;
		this.dunningCycle = dunningCycle;
		this.behaviorStatus = behaviorStatus;
//		this.debtbiztype = debtbiztype;
	}

	@NotNull(message="dbid不能为空")
	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public Integer getCreditamount() {
		return creditamount;
	}

	public void setCreditamount(Integer creditamount) {
		this.creditamount = creditamount;
	}


	public String getPlatformext() {
		return platformext;
	}

	public void setPlatformext(String platformext) {
		this.platformext = platformext;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
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


	public String getDunningCycle() {
		return dunningCycle;
	}


	public void setDunningCycle(String dunningCycle) {
		this.dunningCycle = dunningCycle;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayoffTime() {
		return payoffTime;
	}


	public void setPayoffTime(Date payoffTime) {
		this.payoffTime = payoffTime;
	}


	public Integer getLoanTerm() {
		return loanTerm;
	}


	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}


	public String getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepaymentTime() {
		return repaymentTime;
	}


	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}


	public BigDecimal getLoanNumber() {
		return loanNumber;
	}


	public void setLoanNumber(BigDecimal loanNumber) {
		this.loanNumber = loanNumber;
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


	public String getBehaviorStatus() {
		return behaviorStatus;
	}


	public void setBehaviorStatus(String behaviorStatus) {
		this.behaviorStatus = behaviorStatus;
	}


	public Long getTaskId() {
		return taskId;
	}


	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public Integer getOverdueDays() {
		return overdueDays;
	}


	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}


	public String getDunningTaskStatus() {
		return dunningTaskStatus;
	}


	public void setDunningTaskStatus(String dunningTaskStatus) {
		this.dunningTaskStatus = dunningTaskStatus;
	}

	
	

}