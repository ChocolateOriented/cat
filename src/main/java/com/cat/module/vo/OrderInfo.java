package com.cat.module.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cyuan
 * @date 2018/9/21
 */
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 收款时间
     */
    private Long collectionTime;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String bankNo;
    /**
     * 用户id
     */
    private String customerId;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户手机归属地
     */
    private String mobileLocation;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 身份证地址
     */
    private String idCardAddress;
    /**
     * 应催金额
     */
    private BigDecimal repayAmount;
    /**
     * 本金
     */
    private BigDecimal loanAmount;
    /**
     * 还款日
     */
    private Date repaymentTime;
    /**
     * 逾期费
     */
    private BigDecimal overDueAmount;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 实际放款金额
     */
    private BigDecimal lentAmount;
    /**
     * 利息
     */
    private BigDecimal interestValue;
    /**
     * 账期
     */
    private Integer loanTerm;
    /**
     * 续期次数
     */
    private Integer postponeCount;
    /**
     * 续期总额
     */
    private BigDecimal postponeAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Long collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    public String getMobileLocation() {
        return mobileLocation;
    }

    public void setMobileLocation(String mobileLocation) {
        this.mobileLocation = mobileLocation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardAddress() {
        return idCardAddress;
    }

    public void setIdCardAddress(String idCardAddress) {
        this.idCardAddress = idCardAddress;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public BigDecimal getOverDueAmount() {
        return overDueAmount;
    }

    public void setOverDueAmount(BigDecimal overDueAmount) {
        this.overDueAmount = overDueAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getLentAmount() {
        return lentAmount;
    }

    public void setLentAmount(BigDecimal lentAmount) {
        this.lentAmount = lentAmount;
    }

    public BigDecimal getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(BigDecimal interestValue) {
        this.interestValue = interestValue;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getPostponeCount() {
        return postponeCount;
    }

    public void setPostponeCount(Integer postponeCount) {
        this.postponeCount = postponeCount;
    }

    public BigDecimal getPostponeAmount() {
        return postponeAmount;
    }

    public void setPostponeAmount(BigDecimal postponeAmount) {
        this.postponeAmount = postponeAmount;
    }
}
