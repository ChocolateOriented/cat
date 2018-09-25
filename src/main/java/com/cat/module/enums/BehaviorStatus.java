package com.cat.module.enums;

/**
 *
 * @author cyuan
 * @date 2018/9/21
 */
public enum BehaviorStatus {
    IN("移入"),
    OUT("移出"),
    FINISHED("已还款"),
    PARTIAL("部分还款"),
    POSTPONE("已延期"),
    
    
	OUT_ERROR("异常"),
	IN_WARN("警告");
	
    private String desc;

    private BehaviorStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
