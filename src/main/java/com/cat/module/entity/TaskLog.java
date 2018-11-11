/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 催收任务logEntity
 * @author 徐盛
 * @version 2017-03-01
 */
public class TaskLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单号
	private String collectorId;		// 催收人id
	private String collectorName;		// 催收人姓名
	private String collectCycle;		// 催收周期(队列)
	private String collectRulesType;		// 催收分案策略规则
		
	private String orderStatus;		// 订单状态
	private Date payoffTime;		// 还清时间
	private Integer loanTerm;		// 借贷期限
	private String  customerId;//用户code
	private String customerName;		// 用户姓名
	private String mobile;		// 手机号
	private Date repaymentTime;		// 到期还款日期
	private BigDecimal loanAmount;		// 本金
	private String  orderType;//订单类型
	private String  productType;//产品类型	
	private BigDecimal  lentAmount;//放款金额
	private String  interestMode;//利息模式
	private BigDecimal  interestValue;//利息值
	private String  penaltyMode;//罚息模式
	private BigDecimal  penaltyValue;//罚息值
	private BigDecimal  reliefAmount;//减免金额
	private BigDecimal  chargeValue;//服务费值
	private BigDecimal  repaymentAmount;//本次还款金额
	private Integer  postponeCount;//延期次数

		private BehaviorStatus behaviorStatus;		// 催收员行为状态（in,out,finished,partial,postpone）
		private CollectTaskStatus  collectTaskStatus;//催款任务状态(未开启任务，任务进行中，任务结束，延期)
		private Long taskId;	 // 任务ID
		private Integer overdueDays;		// 逾期天数========需要计算
	/**
	 * 上次还款日期
	 */
	private Date lastPaymentTime;
	/**
	 * 创建延期时间
	 * @return
	 */
	private Date postponeTime;

	private String platformext; // 渠道
	private BigDecimal creditamount;		// 实际应还金额 (当前应催金额)
	private String remark;

	public TaskLog() {
		super();
	}

	
	public TaskLog(String orderId, String collectorId, String collectorName, String collectCycle,
			String collectRulesType,BehaviorStatus behaviorStatus) {
		super();
		this.orderId = orderId;
		this.collectorId = collectorId;
		this.collectorName = collectorName;
		this.collectCycle = collectCycle;
		this.collectRulesType = collectRulesType;
		this.behaviorStatus = behaviorStatus;
	}

	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

	public Date getPostponeTime() {
		return postponeTime;
	}

	public void setPostponeTime(Date postponeTime) {
		this.postponeTime = postponeTime;
	}

	public String getCollectRulesType() {
		return collectRulesType;
	}


	public void setCollectRulesType(String collectRulesType) {
		this.collectRulesType = collectRulesType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public BigDecimal getCreditamount() {
		return creditamount;
	}

	public void setCreditamount(BigDecimal creditamount) {
		this.creditamount = creditamount;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
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



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepaymentTime() {
		return repaymentTime;
	}


	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
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




	public Integer getPostponeCount() {
		return postponeCount;
	}


	public void setPostponeCount(Integer postponeCount) {
		this.postponeCount = postponeCount;
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


	public String getCollectCycle() {
		return collectCycle;
	}


	public void setCollectCycle(String collectCycle) {
		this.collectCycle = collectCycle;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public BigDecimal getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}


	public BigDecimal getLentAmount() {
		return lentAmount;
	}


	public void setLentAmount(BigDecimal lentAmount) {
		this.lentAmount = lentAmount;
	}


	public BehaviorStatus getBehaviorStatus() {
		return behaviorStatus;
	}


	public void setBehaviorStatus(BehaviorStatus behaviorStatus) {
		this.behaviorStatus = behaviorStatus;
	}


	public CollectTaskStatus getCollectTaskStatus() {
		return collectTaskStatus;
	}


	public void setCollectTaskStatus(CollectTaskStatus collectTaskStatus) {
		this.collectTaskStatus = collectTaskStatus;
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
	public TaskLog(Task task){
		this.setOrderId(task.getOrderId());
		this.setCollectorId(task.getCollectorId());
		this.setCollectorName(task.getCollectorName());
		this.setCollectCycle(task.getCollectCycle());
		this.setOrderStatus(task.getOrderStatus());
		this.setLoanTerm(task.getLoanTerm());
		this.setCustomerId(task.getCustomerId());
		this.setCustomerName(task.getCustomerName());
		this.setMobile(task.getMobile());
		this.setRepaymentTime(task.getRepaymentTime());
		this.setLoanAmount(task.getLoanAmount());
		this.setInterestMode(task.getInterestMode());
		this.setInterestValue(task.getInterestValue());
		this.setPenaltyMode(task.getPenaltyMode());
		this.setPenaltyValue(task.getPenaltyValue());
		this.setReliefAmount(task.getReliefAmount());
		this.setChargeValue(task.getChargeValue());
		this.setCollectTaskStatus(task.getCollectTaskStatus());
		this.setTaskId(task.getId());
		this.setProductType(task.getProductType());
		this.setOrderType(task.getOrderType());
		this.setCreateBy(task.getUpdateBy());
		this.setUpdateBy(task.getUpdateBy());
	}
	
}