package com.cat.module.entity;

import javax.persistence.Entity;

/**
 * Created by cyuan on 2018/9/21.
 */
@Entity
public class ContactInfo {
    private Long id;
    /**
     * 用户code
     */
    private String ownerId;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactMobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }
}
