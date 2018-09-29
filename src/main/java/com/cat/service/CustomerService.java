package com.cat.service;

import com.cat.manager.RaptorManager;
import com.cat.mapper.CustomerMapper;
import com.cat.module.dto.BlackListDto;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.CustomerBaseInfo;
import com.cat.util.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cyuan
 * @date 2018/9/19
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RaptorManager raptorManager;
    @Value("${feignClient.raptor.secret}")
    private String secret;

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

    @Transactional
    public void blackList(BlackListDto blackListDto) {
        String customerId = blackListDto.getCustomerId();
        String desc = blackListDto.getReason();
        CustomerBaseInfo customerBaseInfo = this.fetchCustomerByCustomerId(customerId);
        if (customerBaseInfo == null){
            throw new RuntimeException("用户不存在");
        }
        customerBaseInfo.setBlacklistReason(desc);
        customerBaseInfo.setBlacklist(true);
        this.updateCustomer(customerBaseInfo);

        String sign = EncryptionUtils.md5Encode(customerId+desc+secret);
        Results result = raptorManager.blacklistCustomer(customerId,desc,sign);
        if (!result.isSuccess()){
            throw new RuntimeException(result.getMessage());
        }
    }
}
