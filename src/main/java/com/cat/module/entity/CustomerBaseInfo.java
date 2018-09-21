package com.cat.module.entity;

import javax.persistence.Entity;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 *
 * @author cyuan
 * @date 2018/9/19
 */
@Entity
public class CustomerBaseInfo extends BaseEntity {
    /**
     * 用户code
     */
    private String customerId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 性别
     */
    private String gender;
    /**
     * 身份证地址
     */
    private String idCardAddress;
    /**
     * 实际住址
     */
    private String permanentAddress;
    /**
     * 发薪水时间
     */
    private Date salaryTime;
    /**
     * 薪水
     */
    private Integer salary;
    /**
     * 账户信用状态
     */
    private String creditStatus;
    /**
     * 手机通讯录是否上传
     */
    private int mobileContacts;
    /**
     * 通话记录是否授权
     */
    private int callHistory;
    /**
     * 用户ip
     */
    private String userIp;
    /**
     * 是否可登录
     */
    private int loginEnable;
    /**
     * 是否删除
     */
    private int deleted;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    private String contactsList;

    public String getContactsList() {
        return contactsList;
    }

    public void setContactsList(String contactsList) {
        this.contactsList = contactsList;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardAddress() {
        return idCardAddress;
    }

    public void setIdCardAddress(String idCardAddress) {
        this.idCardAddress = idCardAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Date getSalaryTime() {
        return salaryTime;
    }

    public void setSalaryTime(Date salaryTime) {
        this.salaryTime = salaryTime;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public int getMobileContacts() {
        return mobileContacts;
    }

    public void setMobileContacts(int mobileContacts) {
        this.mobileContacts = mobileContacts;
    }

    public int getCallHistory() {
        return callHistory;
    }

    public void setCallHistory(int callHistory) {
        this.callHistory = callHistory;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public int getLoginEnable() {
        return loginEnable;
    }

    public void setLoginEnable(int loginEnable) {
        this.loginEnable = loginEnable;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


}
