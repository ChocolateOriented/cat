package com.cat.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;

/**
 * @author cyuan
 */
@Entity
public class Bank extends BaseEntity {

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户code
     */
    private String customerId;
    /**
     * 银行账号
     */
    private String bankCard;
    /**
     * 银行名字
     */
    private String bankName;

    private String idCard;

    private String userName;

    private String channel;
    /**
     * 类型 (放款 , 还款)
     */
    private String type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getCustomerId() {
        return customerId;
    }
    @JSONField(name = "userCode")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }
    @JSONField(name = "realName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}