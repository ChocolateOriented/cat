package com.cat.mapper;

import com.cat.module.entity.CustomerBaseInfo;
import com.cat.module.vo.OrderInfo;

import java.util.List;

/**
 * Created by cyuan on 2018/9/21.
 */
public interface CustomerMapper {
    OrderInfo selectAllInfo(String customerId);

    CustomerBaseInfo fetchCustomerByCustomerId(String customerId);

    void insert(CustomerBaseInfo customerBaseInfo);

    void updateByPrimaryKey(CustomerBaseInfo customerBaseInfo);

    int deleteByPrimaryKey(Long id);

    CustomerBaseInfo selectByPrimaryKey(Long id);

    List<CustomerBaseInfo> selectAll();

}
