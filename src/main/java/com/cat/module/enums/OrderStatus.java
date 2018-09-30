package com.cat.module.enums;

/**
 * Created by cyuan on 2018/9/29.
 */
public enum OrderStatus {
    PAYMENT("未还清"),
    PAYOFF("已还清");

    String desc;
    OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
