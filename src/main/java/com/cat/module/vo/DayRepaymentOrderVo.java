package com.cat.module.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cyuan on 2018/10/26.
 */
public class DayRepaymentOrderVo {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 还款到期日
     */
    private Date repaymentTime;
    /**
     * //订单状态 POSTPONE:延期, PAYOFF:已还清
     */
    private String orderStatus;
    /**
     * 逾期天数
     */
    private Integer overdueDays;
    /**
     * 还款金额
     */
    private BigDecimal repaymentAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }
}
