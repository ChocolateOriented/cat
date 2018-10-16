package com.cat.module.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PostponeHistory {
    private Long id;

    private String orderId;

    private String customerId;

    private String payType;

    private Integer postponeCount;

    private String payOrderId;

    private BigDecimal repayAmount;

    private Date repaymentTime;

    private BigDecimal channelRepayMumber;

    private String channel;

    private String clientId;

    private String clientVersion;

    private String bankNo;

    private String bankMobile;

    private String customerName;

    private String idcard;

    private String dealCode;

    private String postponeDays;

    private String isEntryDone;

    private Date payoffTime;

    private BigDecimal totalReliefAmount;

    private String productType;

    private Date createdTime;

    private Date updatedTime;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getPostponeCount() {
        return postponeCount;
    }

    public void setPostponeCount(Integer postponeCount) {
        this.postponeCount = postponeCount;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
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

    public BigDecimal getChannelRepayMumber() {
        return channelRepayMumber;
    }

    public void setChannelRepayMumber(BigDecimal channelRepayMumber) {
        this.channelRepayMumber = channelRepayMumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDealCode() {
        return dealCode;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public String getPostponeDays() {
        return postponeDays;
    }

    public void setPostponeDays(String postponeDays) {
        this.postponeDays = postponeDays;
    }

    public String getIsEntryDone() {
        return isEntryDone;
    }

    public void setIsEntryDone(String isEntryDone) {
        this.isEntryDone = isEntryDone;
    }

    public Date getPayoffTime() {
        return payoffTime;
    }

    public void setPayoffTime(Date payoffTime) {
        this.payoffTime = payoffTime;
    }

    public BigDecimal getTotalReliefAmount() {
        return totalReliefAmount;
    }

    public void setTotalReliefAmount(BigDecimal totalReliefAmount) {
        this.totalReliefAmount = totalReliefAmount;
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