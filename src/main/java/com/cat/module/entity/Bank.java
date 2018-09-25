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
    @JSONField(name = "bankCard")
    private String bankNo;
    /**
     * 银行名字
     */
    private String bankName;

    @JSONField(name = "idCard")
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

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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