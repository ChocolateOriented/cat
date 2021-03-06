package com.cat.module.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cyuan on 2018/9/21.
 */
public class RepaymentMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 借款订单号id
     */
    private String orderId;
    /**
     * 用户code
     */
    private String customerId;
    /**
     * 还款类型
     */
    private String payType;
    /**
     * 延期次数
     */
    private Integer postponeCount;
    /**
     * 还款订单号
     */
    private String payOrderId;
    /**
     * 还款申请金额
     */
    private BigDecimal repayAmount;
    /**
     * 还款日期
     */
    private Date repaymentTime;
    /**
     * 渠道返回的还款数目
     */
    private BigDecimal channelRepayNumber;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 客户端Id
     */
    private String clientId;
    /**
     * 客户端版本号
     */
    private String clientVersion;
    /**
     * 还款银行卡
     */
    private String bankNo;
    /**
     * 银行预留手机
     */
    private String bankMobile;
    /**
     * 用户真实姓名
     */
    private String customerName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 放款流水号
     */
    private String dealCode;
    /**
     * 延期天数
     */
    private Integer postponeDays;
    /**
     * 是否入账完成
     */
    private Boolean isEntryDone;
    /**
     * 还清时间
     * @return
     */
    private Date payoffTime;
    /**
     * 减免总金额
     * @return
     */
    private BigDecimal totalReliefAmount;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 创建延期时间
     * @return
     */
    private Date postponeTime;

    /**
     * 单次减免金额
     * @return
     */
    private BigDecimal reliefAmount;

    public BigDecimal getReliefAmount() {
        return reliefAmount;
    }

    public void setReliefAmount(BigDecimal reliefAmount) {
        this.reliefAmount = reliefAmount;
    }

    public Date getPostponeTime() {
        return postponeTime;
    }
    @JSONField(name = "createTime")
    public void setPostponeTime(Date postponeTime) {
        this.postponeTime = postponeTime;
    }

    public String getCustomerId() {
        return customerId;
    }
    @JSONField(name = "userCode")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankNo() {
        return bankNo;
    }
    @JSONField(name = "bankCard")
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    @JSONField(name = "userName")
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getTotalReliefAmount() {
        return totalReliefAmount;
    }

    public void setTotalReliefAmount(BigDecimal totalReliefAmount) {
        this.totalReliefAmount = totalReliefAmount;
    }

    public Date getPayoffTime() {
        return payoffTime;
    }

    public void setPayoffTime(Date payoffTime) {
        this.payoffTime = payoffTime;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }
    @JSONField(name = "repaymentDate")
    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public BigDecimal getChannelRepayNumber() {
        return channelRepayNumber;
    }

    public void setChannelRepayNumber(BigDecimal channelRepayNumber) {
        this.channelRepayNumber = channelRepayNumber;
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

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDealCode() {
        return dealCode;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public Integer getPostponeDays() {
        return postponeDays;
    }

    public void setPostponeDays(Integer postponeDays) {
        this.postponeDays = postponeDays;
    }

    public Boolean getEntryDone() {
        return isEntryDone;
    }

    public void setEntryDone(Boolean entryDone) {
        isEntryDone = entryDone;
    }
}
