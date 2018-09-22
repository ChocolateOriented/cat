package com.cat.module.enums;

/**
 *
 * @author cyuan
 * @date 2018/9/21
 */
public enum BehaviorStatus {
    IN(""),
    OUT(""),
    FINISHED("结束"),
    PARTIAL(""),
    POSTPONE("延期");
    private String desc;

    private BehaviorStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
