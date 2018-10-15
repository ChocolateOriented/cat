package com.cat.module.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cyuan on 2018/10/12.
 */
public class PostponeHistoryVo {

    private Long id;
    private BigDecimal postponeAmount;
    private Date postponeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPostponeAmount() {
        return postponeAmount;
    }

    public void setPostponeAmount(BigDecimal postponeAmount) {
        this.postponeAmount = postponeAmount;
    }

    public Date getPostponeTime() {
        return postponeTime;
    }

    public void setPostponeTime(Date postponeTime) {
        this.postponeTime = postponeTime;
    }
}
