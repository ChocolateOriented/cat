/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.cat.module.enums.CollectTaskStatus;
/*import com.cat.service.ScheduledTaskService;*/
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 催收任务Entity
 * @author 徐盛
 * @version 2016-07-12
 */
@Entity
@Table(name = "t_task")
public class Task  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	/**
	 *  --------------------------催款任务状态----------------------------------------------
	 */
	public static final String STATUS_DUNNING = "dunning"; //代表催款任务正在催收中
	public static final String STATUS_EXPIRED = "expired";  //代表催款任务超出催收周期并未催回
	public static final String STATUS_END = "end"; //代表催款任务被结束，但并没有被还清
	public static final String STATUS_FINISHED = "finished"; //代表催款任务的订单在催收周期内已还清
	public static final String STATUS_TRANSFER = "transfer"; //代表催款任务在催收周期内转移给了另一个同周期催款用户

	private String  orderId;//订单ID - 业务流水号
	private String  customerId;//用户code
	private String customerName;		//客户姓名
	private String mobile;		// 手机号
	private String  orderType;//订单类型
	private String  productType;//产品类型
	private String  orderStatus;//订单状态
	private BigDecimal  loanAmount;//借贷金额
	private Integer  loanTerm;//借贷期限
	private BigDecimal  lentAmount;//放款金额
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
	private Date  repaymentTime;//到期还款日期
	private String collectorId;
	private String  collectorName;//催讨人名
	private Date  taskStartTime;//任务起始时间
	private Date  taskEndTime;//任务结束时间
	private Integer  collectPeriodBegin;//催讨周期-逾期周期起始
	private Integer  collectPeriodEnd;//催讨周期-逾期周期截至
	private CollectTaskStatus  collectTaskStatus;//催款任务状态(未开启任务，任务进行中，任务结束，延期)
	private String  collectTelRemark;//催收备注
	private Date  collectTime;//操作时间
	private String  collectCycle;//催收队列
	private String  remark;//备注
	
	private boolean ispayoff;		// 任务所对应的订单是否还清
	
	
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
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendTime() {
		return lendTime;
	}
	public void setLendTime(Date lendTime) {
		this.lendTime = lendTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayoffTime() {
		return payoffTime;
	}
	public void setPayoffTime(Date payoffTime) {
		this.payoffTime = payoffTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIspayoff() {
		return ispayoff;
	}
	public void setIspayoff(boolean ispayoff) {
		this.ispayoff = ispayoff;
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
	public Integer getCollectPeriodBegin() {
		return collectPeriodBegin;
	}
	public void setCollectPeriodBegin(Integer collectPeriodBegin) {
		this.collectPeriodBegin = collectPeriodBegin;
	}
	public Integer getCollectPeriodEnd() {
		return collectPeriodEnd;
	}
	public void setCollectPeriodEnd(Integer collectPeriodEnd) {
		this.collectPeriodEnd = collectPeriodEnd;
	}
	public CollectTaskStatus getCollectTaskStatus() {
		return collectTaskStatus;
	}
	public void setCollectTaskStatus(CollectTaskStatus collectTaskStatus) {
		this.collectTaskStatus = collectTaskStatus;
	}
	public String getCollectTelRemark() {
		return collectTelRemark;
	}
	public void setCollectTelRemark(String collectTelRemark) {
		this.collectTelRemark = collectTelRemark;
	}
	public String getCollectCycle() {
		return collectCycle;
	}
	public void setCollectCycle(String collectCycle) {
		this.collectCycle = collectCycle;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 *  获取当前逾期天数
	 * @return
	 */
/*	public int getCurrentOverdueDays()
	{
//		Date now  = new Date();
		return ScheduledTaskService.GetOverdueDay(repaymentTime);
//		return (int)((toDate(now).getTime() - toDate(repaymentTime).getTime()) / (24 * 60 * 60 * 1000));
	}*/

	private Date toDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}