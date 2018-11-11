package com.cat.module.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cyuan on 2018/11/2.
 */
public class OrderDetailsReportDto {

    /**
     * 报表日期
     */
    private Date reportTime;
    /**
     * 催收员
     */
    private String collectorName;
    /**
     * 队列
     */
    private String collectCycle;
    /**
     * 产品名称
     */
    private String  productType;
    /**
     * 产品渠道
     */
    private String platformext;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 放款时间
     */
    private Date  lendTime;
    /**
     * 应催金额
     */
    private BigDecimal creditamount;
    /**
     * 逾期天数
     */
    private Integer overdueDays;
    /**
     * 借款时间
     */
    private Date borrowTime;
    /**
     * 延期次数
     */
    private Integer postponeCount;
    /**
     * 延期日期
     */
    private Date postponeTime;
    /**
     * 延期到期日期
     */
    private Date repaymentTime;
    /**
     * 延期金额
     */
    private BigDecimal postponeAmount;
    /**
     * 还清日期
     */
    private Date payoffTime;
    /**
     * 还清金额
     */
    private BigDecimal repaymentAmount;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 入催日期
     */
    private Date createdTime;

    /**
     * 手机
     * @return
     */
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public Date getLendTime() {
        return lendTime;
    }

    public void setLendTime(Date lendTime) {
        this.lendTime = lendTime;
    }

    public BigDecimal getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(BigDecimal creditamount) {
        this.creditamount = creditamount;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Integer getPostponeCount() {
        return postponeCount;
    }

    public void setPostponeCount(Integer postponeCount) {
        this.postponeCount = postponeCount;
    }

    public Date getPostponeTime() {
        return postponeTime;
    }

    public void setPostponeTime(Date postponeTime) {
        this.postponeTime = postponeTime;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public BigDecimal getPostponeAmount() {
        return postponeAmount;
    }

    public void setPostponeAmount(BigDecimal postponeAmount) {
        this.postponeAmount = postponeAmount;
    }

    public Date getPayoffTime() {
        return payoffTime;
    }

    public void setPayoffTime(Date payoffTime) {
        this.payoffTime = payoffTime;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
