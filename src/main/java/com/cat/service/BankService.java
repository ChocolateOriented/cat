package com.cat.service;

import com.cat.mapper.BankMapper;
import com.cat.module.entity.Bank;
import com.cat.module.enums.BankType;
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

    /**
     * 获取银行卡信息
     * @param bankNo
     * @param bankType
     * @return
     */
    public Bank findBankByBankNoAndType(String bankNo, BankType bankType) {
        return bankMapper.findBankByBankNoAndType(bankNo, bankType.name());
    }

    public void insertBank(Bank bank) {
        bankMapper.insert(bank);
    }
}
