package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PostponeHistory {

    private Long id;

    private String orderId;

    private String customerId;

    private Integer postponeCount;

    private BigDecimal repayAmount;

    private Date repaymentTime;

    private String channel;

    private String bankNo;

    private String collectorName;

    private String collectorId;

    private int postponeDays;

    private String productType;
    /**
     * 创建延期时间
     * @return
     */
    private Date postponeTime;

    private BigDecimal reliefAmount;
    /**
     * 上次还款日期
     */
    private Date lastPaymentTime;

    private Date createdTime;

    private Date updatedTime;

    public BigDecimal getReliefAmount() {
        return reliefAmount;
    }

    public void setReliefAmount(BigDecimal reliefAmount) {
        this.reliefAmount = reliefAmount;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
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

    public Integer getPostponeCount() {
        return postponeCount;
    }

    public void setPostponeCount(Integer postponeCount) {
        this.postponeCount = postponeCount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public int getPostponeDays() {
        return postponeDays;
    }

    public void setPostponeDays(int postponeDays) {
        this.postponeDays = postponeDays;
    }


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}