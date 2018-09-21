package com.cat.module.enums;

/**
 *
 * @author cyuan
 * @date 2018/9/21
 */
public enum DunningTaskStatus {
    UNOPEND_TASK("未开启的任务"),
    TASK_IN_PROGRESS("任务进行中"),
    TASK_FINISHED("任务结束"),
    TASK_POSTPONE("延期还款");

    private String desc;

    private DunningTaskStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
