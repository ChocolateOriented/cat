package com.cat.service;

import com.cat.mapper.CustomerMapper;
import com.cat.module.entity.CustomerBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cyuan
 * @date 2018/9/19
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 获取客户信息
     * @param customerId
     * @return
     */
    public CustomerBaseInfo fetchCustomerByCustomerId(String customerId) {
        return customerMapper.fetchCustomerByCustomerId(customerId);
    }

    public void insertCustomer(CustomerBaseInfo customerBaseInfo) {
        customerMapper.insert(customerBaseInfo);
    }

    public void updateCustomer(CustomerBaseInfo customerBaseInfo) {
        customerMapper.updateByPrimaryKey(customerBaseInfo);
    }


}
