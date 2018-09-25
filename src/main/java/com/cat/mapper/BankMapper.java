package com.cat.mapper;

import com.cat.module.entity.Bank;

import java.util.List;

/**
 * Created by cyuan on 2018/9/21.
 */
public interface BankMapper {
    Bank findBankByBankNo(String bankNo);

    void insert(Bank bank);

    Bank selectByPrimaryKey(Long id);

    List<Bank> selectAll();

    int updateByPrimaryKey(Bank record);

    int deleteByPrimaryKey(Long id);
}
