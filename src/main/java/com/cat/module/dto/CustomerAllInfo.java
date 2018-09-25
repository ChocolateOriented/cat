package com.cat.module.dto;

import com.cat.module.entity.Bank;
import com.cat.module.entity.Contact;
import com.cat.module.entity.CustomerBaseInfo;
import com.cat.module.entity.Task;

import java.util.List;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
public class CustomerAllInfo {
    private CustomerBaseInfo customerBaseInfo;
    private Bank bank;
    private Task task;
    private List<Contact> contactList;

    public CustomerBaseInfo getCustomerBaseInfo() {
        return customerBaseInfo;
    }

    public void setCustomerBaseInfo(CustomerBaseInfo customerBaseInfo) {
        this.customerBaseInfo = customerBaseInfo;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
