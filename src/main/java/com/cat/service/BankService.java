package com.cat.service;

import com.cat.mapper.BankMapper;
import com.cat.module.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
@Service
public class BankService extends BaseService{
    @Autowired
    private BankMapper bankMapper;

    public Bank findBankByBankNo(String bankNo) {
        return bankMapper.findBankByBankNo(bankNo);
    }

    public void insertBank(Bank bank) {
        bankMapper.insert(bank);
    }
}
