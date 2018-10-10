package com.cat.module.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cyuan
 */
public class ManualPayments {
    private Long id;
    @NotBlank(message = "订单号不能为空")
    private String orderId;

    private String customerId;

    @NotNull(message = "还款金额不能为空")
    private BigDecimal actualPaymentAmount;

    @NotBlank(message = "还款类型不能为空")
    @Pattern(regexp = "PAYOFF|POSTPONE", message = "请传入正确还款类型参数格式")
    private String paymentStatus;

    private String userId;

    private String userName;

    private String productType;

    private Date createdTime;

    private Date updatedTime;

    private String createBy;

    private String remark;

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

    public BigDecimal getActualPaymentAmount() {
        return actualPaymentAmount;
    }
    public void setActualPaymentAmount(BigDecimal actualPaymentAmount) {
        this.actualPaymentAmount = actualPaymentAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ManualPayments{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", actualPaymentAmount=" + actualPaymentAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", productType='" + productType + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", createBy='" + createBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}