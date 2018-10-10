package com.cat.mapper;

import com.cat.module.entity.ManualPayments;
import java.util.List;

public interface ManualPaymentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManualPayments record);

    ManualPayments selectByPrimaryKey(Long id);

    List<ManualPayments> selectAll();

    int updateByPrimaryKey(ManualPayments record);
}