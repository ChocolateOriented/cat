package com.cat.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

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

    @JSONField(name = "contact_name")
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }
    @JSONField(name = "contact_mobile")
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (customerId != null ? !customerId.equals(contact.customerId) : contact.customerId != null) return false;
        if (mobile != null ? !mobile.equals(contact.mobile) : contact.mobile != null) return false;
        return contactMobile != null ? contactMobile.equals(contact.contactMobile) : contact.contactMobile == null;
    }

    @Override
    public int hashCode() {
        int result = customerId != null ? customerId.hashCode() : 0;
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (contactMobile != null ? contactMobile.hashCode() : 0);
        return result;
    }
}
