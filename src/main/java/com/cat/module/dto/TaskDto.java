package com.cat.module.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class TaskDto implements Serializable{
  
	private static final long serialVersionUID = 1L;
	
	private String  orderId;//订单ID - 业务流水号
	private String customerId;//客户id
	private String  name;//客户姓名
	private String  mobile;//用户手机号
	private String  orderStatus;//订单状态
	private BigDecimal  principalAndInterest;//欠款金额
	private BigDecimal  principal;//本金
	private Integer  customerTotalAmount;//应催金额
	private Date repaymentTime; //到期还款日
	private Integer overdueDays;//逾期天数
	private String remark;//催收备注
	private String collectorName;//催收人
	private Date payoffTime;//还清日期
	private Date collectTime;//最近催收时间
	private Long organizationId;//机构id
	private String organizationLeaderId;//机构组长id
	private String collectorId;//催收员id
	private String actionFeedback;//行动码
	private String productType;//产品类型
	private String collectCycle;//案件所属队列
	private BigDecimal reliefAmount; //减免金额
	//------------请求参数----------
	private Integer overdueDaysStart;//逾期天数第1个值
	private Integer overdueDaysEnd;//逾期天数第2个值
	private Long payoffTimeStart;//还清日期第一个值
	private Long payoffTimeEnd;//还清日期第二个值
	private Long collectTimeStart;//最近催收时间第一个值
	private Long collectTimeEnd;//最近催收时间第二个值
	private List<String> collectorNames;//最近催收时间第二个值
	
	
	public List<String> getCollectorNames() {
		return collectorNames;
	}
	public void setCollectorNames(List<String> collectorNames) {
		this.collectorNames = collectorNames;
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
	public BigDecimal getPrincipalAndInterest() {
		return principalAndInterest;
	}
	public void setPrincipalAndInterest(BigDecimal principalAndInterest) {
		this.principalAndInterest = principalAndInterest;
	}
	public Integer getCustomerTotalAmount() {
		return customerTotalAmount;
	}
	public void setCustomerTotalAmount(Integer customerTotalAmount) {
		this.customerTotalAmount = customerTotalAmount;
	}
	public Long getRepaymentTime() {
		return repaymentTime == null ? null : repaymentTime.getTime();
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
	public Long getPayoffTime() {
		return  payoffTime == null ? null : payoffTime.getTime();
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

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getActionFeedback() {
		return actionFeedback;
	}
	public void setActionFeedback(String actionFeedback) {
		this.actionFeedback = actionFeedback;
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
	public String getPayoffTimeStart() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return this.payoffTimeStart == null ? null : sd.format(new Date(this.payoffTimeStart));
	}
	public void setPayoffTimeStart(Long payoffTimeStart) {
		this.payoffTimeStart = payoffTimeStart;
	}
	public String getPayoffTimeEnd() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return this.payoffTimeEnd == null ? null : sd.format(new Date(this.payoffTimeEnd)) ;
	}
	public void setPayoffTimeEnd(Long payoffTimeEnd) {
		this.payoffTimeEnd = payoffTimeEnd;
	}
	public BigDecimal getReliefAmount() {
		return reliefAmount;
	}
	public void setReliefAmount(BigDecimal reliefAmount) {
		this.reliefAmount = reliefAmount;
	}
	public String getOrganizationLeaderId() {
		return organizationLeaderId;
	}
	public void setOrganizationLeaderId(String organizationLeaderId) {
		this.organizationLeaderId = organizationLeaderId;
	}
	public Long getCollectTime() {
		return  collectTime == null ? null : collectTime.getTime();
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollectTimeStart() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return this.collectTimeStart == null ? null : sd.format(new Date(this.collectTimeStart));
	}
	public void setCollectTimeStart(Long collectTimeStart) {
		this.collectTimeStart = collectTimeStart;
	}
	public String getCollectTimeEnd() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return this.collectTimeEnd == null ? null : sd.format(new Date(this.collectTimeEnd));
	}
	public void setCollectTimeEnd(Long collectTimeEnd) {
		this.collectTimeEnd = collectTimeEnd;
	}
	public BigDecimal getPrincipal() {
		return principal;
	}
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCollectCycle() {
		return collectCycle;
	}
	public void setCollectCycle(String collectCycle) {
		this.collectCycle = collectCycle;
	}
	
}
