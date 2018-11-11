package com.cat.module.dto;

import java.math.BigDecimal;

/**
 * Created by cyuan on 2018/10/29.
 */
public class CurrentOrderDto {
    private String collectorId;
    private String behaviorStatus;
    private Integer count;
    private BigDecimal sumRepaymentAmount;

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getBehaviorStatus() {
        return behaviorStatus;
    }

    public void setBehaviorStatus(String behaviorStatus) {
        this.behaviorStatus = behaviorStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getSumRepaymentAmount() {
        return sumRepaymentAmount;
    }

    public void setSumRepaymentAmount(BigDecimal sumRepaymentAmount) {
        this.sumRepaymentAmount = sumRepaymentAmount;
    }

    public Boolean checkStatus(String status) {
        if (this.behaviorStatus.equals(status)) {
            return true;
        } else {
            return false;
        }
    }
}
