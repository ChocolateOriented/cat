package com.cat.module.enums;

/**
 * Created by cyuan on 2018/9/21.
 */
public enum  BankType {
    LEND("放款"),
    REPAYMENT("还款");
    private String desc;

    private BankType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
