package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by cyuan on 2018/9/21.
 */
@Entity
public class Contact {
    @Id
    private Long id;
    /**
     * 用户code
     */
    private String customerId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
