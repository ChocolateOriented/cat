package com.cat.module.dto;

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
    private String userCode;
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
    private Date repaymentDate;
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
    private String bankCard;
    /**
     * 银行预留手机
     */
    private String bankMobile;
    /**
     * 用户真实姓名
     */
    private String userName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 放款流水号, 比如 mo9WDAFERANBPKCHOOA
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

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
