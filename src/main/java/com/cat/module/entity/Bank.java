package com.cat.module.entity;

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
    private String ownerId;
    /**
     * 银行账号
     */
    private String bankNo;
    /**
     * 银行名字
     */
    private String bankName;

    private String cardId;

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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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