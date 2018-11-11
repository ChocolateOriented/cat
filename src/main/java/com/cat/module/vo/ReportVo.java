package com.cat.module.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cyuan on 2018/10/29.
 */
public class ReportVo {
    /**
     * 日期
     */
    private Date date;
    /**
     * 催收员
     */
    private String collectorName;
    /**
     * 产品
     */
    private String productType;
    /**
     * 队列
     */
    private String collectCycle;
    /**
     * 应催户数
     */
    private Integer shouldPushCount;
    /**
     * 应催金额
     */
    private BigDecimal shouldPushAmount;
    /**
     * 新增户数
     */
    private Integer newOrderCount;
    /**
     * 新增金额
     */
    private BigDecimal newOrderAmount;
    /**
     * 回收户数-全额
     */
    private Integer payoffCount;
    /**
     * 回收户数-延期
     */
    private Integer postponeCount;
    /**
     * 总回收户数
     */
    private Integer allCollectCount;
    /**
     * 回收金额-全额
     */
    private BigDecimal payoffAmount;
    /**
     * 回收金额-延期
     */
    private BigDecimal postponeAmount;
    /**
     * 总回收金额
     */
    private BigDecimal allCollectAmount;


    public String getCollectCycle() {
        return collectCycle;
    }

    public void setCollectCycle(String collectCycle) {
        this.collectCycle = collectCycle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getShouldPushCount() {
        return shouldPushCount;
    }

    public void setShouldPushCount(Integer shouldPushCount) {
        this.shouldPushCount = shouldPushCount;
    }

    public BigDecimal getShouldPushAmount() {
        return shouldPushAmount;
    }

    public void setShouldPushAmount(BigDecimal shouldPushAmount) {
        this.shouldPushAmount = shouldPushAmount;
    }

    public Integer getNewOrderCount() {
        return newOrderCount;
    }

    public void setNewOrderCount(Integer newOrderCount) {
        this.newOrderCount = newOrderCount;
    }

    public BigDecimal getNewOrderAmount() {
        return newOrderAmount;
    }

    public void setNewOrderAmount(BigDecimal newOrderAmount) {
        this.newOrderAmount = newOrderAmount;
    }

    public Integer getPayoffCount() {
        return payoffCount;
    }

    public void setPayoffCount(Integer payoffCount) {
        this.payoffCount = payoffCount;
    }

    public Integer getPostponeCount() {
        return postponeCount;
    }

    public void setPostponeCount(Integer postponeCount) {
        this.postponeCount = postponeCount;
    }

    public Integer getAllCollectCount() {
        return allCollectCount;
    }

    public void setAllCollectCount(Integer allCollectCount) {
        this.allCollectCount = allCollectCount;
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

    public BigDecimal getAllCollectAmount() {
        return allCollectAmount;
    }

    public void setAllCollectAmount(BigDecimal allCollectAmount) {
        this.allCollectAmount = allCollectAmount;
    }
}
