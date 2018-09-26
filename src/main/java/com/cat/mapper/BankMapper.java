package com.cat.mapper;

import com.cat.module.entity.Bank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cyuan on 2018/9/21.
 */
public interface BankMapper {
    Bank findBankByBankNoAndType(@Param("bankCard") String bankCard, @Param("bankType") String bankType);

    void insert(Bank bank);

    Bank selectByPrimaryKey(Long id);

    List<Bank> selectAll();

    int updateByPrimaryKey(Bank record);

    int deleteByPrimaryKey(Long id);
}
