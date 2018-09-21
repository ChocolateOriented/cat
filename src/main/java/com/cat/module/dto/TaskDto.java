package com.cat.module.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cat.module.entity.BaseEntity;
import com.cat.util.NumberUtil;
import com.cat.util.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "t_cat_task")
public class TaskDto extends BaseEntity{
  
	private String  orderId;//订单ID - 业务流水号
	private String  realName;//用户姓名
	private String  mobile;//用户手机号
	private String  orderStatus;//订单状态
	private BigDecimal  loanAmount;//欠款金额
	private Integer  repayAmount;//应催金额
	private Date repaymentTime; //到期还款日
	private Integer overdueDays;//逾期天数
	private String dunningTelRemark;//催收备注
	private String dunningpeopleName;//催收人
	private Date payoffTime;//还清日期
	private Long organizationId;//机构id
	private Long userId;//催收员id
	
	
	//------------请求参数----------
	private Integer overdueDaysStart;//逾期天数第1个值
	private Integer overdueDaysEnd;//逾期天数第2个值
	
	@ExcelField(title = "订单编号", type = 1, align = 2, sort = 110)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@ExcelField(title = "姓名", type = 1, align = 2, sort = 10)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	@ExcelField(title = "手机号", type = 1, align = 2, sort = 20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ExcelField(title = "订单状态", type = 1, align = 2, sort = 70)
	public String getOrderStatusText() {
		return "payment" == this.orderStatus ? "未还清" :"已还清"; 
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@ExcelField(title = "欠款金额", type = 1, align = 2, sort = 30)
	public String getLoanAmountText() {
		return null != this.loanAmount ? NumberUtil.formatTosepara(this.loanAmount) : "";
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	@ExcelField(title = "应催金额", type = 1, align = 2, sort = 40)
	public String getRepayAmountText() {
		return null != this.repayAmount ? NumberUtil.formatTosepara(this.repayAmount) : "";
	}
	public Integer getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(Integer repayAmount) {
		this.repayAmount = repayAmount;
	}
	@ExcelField(title = "到期还款日", type = 1, align = 2, sort = 50)
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	@ExcelField(title = "逾期天数", type = 1, align = 2, sort = 60)
	public Integer getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	@ExcelField(title = "催收备注", type = 1, align = 2, sort = 80)
	public String getDunningTelRemark() {
		return dunningTelRemark;
	}
	public void setDunningTelRemark(String dunningTelRemark) {
		this.dunningTelRemark = dunningTelRemark;
	}
	@ExcelField(title = "催收人", type = 1, align = 2, sort = 90)
	public String getDunningpeopleName() {
		return dunningpeopleName;
	}
	public void setDunningpeopleName(String dunningpeopleName) {
		this.dunningpeopleName = dunningpeopleName;
	}
	@ExcelField(title = "还清日期", type = 1, align = 2, sort = 100)
	public Date getPayoffTime() {
		return payoffTime;
	}
	public void setPayoffTime(Date payoffTime) {
		this.payoffTime = payoffTime;
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
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
