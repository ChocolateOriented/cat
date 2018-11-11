package com.cat.module.vo;

import java.math.BigDecimal;

/**
 * Created by cyuan on 2018/10/26.
 */
public class DayTaskVo {
    /**
     * 催收员id
     */
    private String collectorId;
    /**
     * 催收员姓名
     */
    private String collectorName;
    /**
     * 机构名称
     */
    private String organizationName;
    /**
     * 1-7
     */
    private String taskPeriod;
    /**
     * 今日还清订单
     */
    private Integer payoffOrder;
    /**
     * 今日延期订单
     */
    private Integer postponeOrder;
    /**
     * 今日催回金额
     */
    private BigDecimal collectAmount;
    /**
     * 新进订单
     */
    private Integer newOrder;
    /**
     * 已处理订单
     */
    private Integer dealOrder;
    /**
     * 今日已处理订单占比
     */
    private BigDecimal percent;
    /**
     * 今日还款订单
     */
    private Integer repaymentOrder;
    /**
     * 应催订单数量
     */
    private Integer shouldPushOrder;
    /**
     * 还清金额
     */
    private BigDecimal payoffAmount;
    /**
     * 续期金额
     */
    private BigDecimal postponeAmount;

    public BigDecimal getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(BigDecimal collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTaskPeriod() {
        return taskPeriod;
    }

    public void setTaskPeriod(String taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public Integer getPayoffOrder() {
        return payoffOrder;
    }

    public void setPayoffOrder(Integer payoffOrder) {
        this.payoffOrder = payoffOrder;
    }

    public Integer getPostponeOrder() {
        return postponeOrder;
    }

    public void setPostponeOrder(Integer postponeOrder) {
        this.postponeOrder = postponeOrder;
    }



    public Integer getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Integer newOrder) {
        this.newOrder = newOrder;
    }

    public Integer getDealOrder() {
        return dealOrder;
    }

    public void setDealOrder(Integer dealOrder) {
        this.dealOrder = dealOrder;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Integer getRepaymentOrder() {
        return repaymentOrder;
    }

    public void setRepaymentOrder(Integer repaymentOrder) {
        this.repaymentOrder = repaymentOrder;
    }

    public Integer getShouldPushOrder() {
        return shouldPushOrder;
    }

    public void setShouldPushOrder(Integer shouldPushOrder) {
        this.shouldPushOrder = shouldPushOrder;
    }

    public BigDecimal getPayoffAmount() {
        return payoffAmount;
    }

    public void setPayoffAmount(BigDecimal payoffAmount) {
        this.payoffAmount = payoffAmount;
    }

    public BigDecimal getPostponeAmount() {
        return postponeAmount;
    }

    public void setPostponeAmount(BigDecimal postponeAmount) {
        this.postponeAmount = postponeAmount;
    }
}
