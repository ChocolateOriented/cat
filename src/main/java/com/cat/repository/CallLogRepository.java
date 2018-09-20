package com.cat.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cat.module.entity.risk.CallLog;

public interface CallLogRepository extends PagingAndSortingRepository<CallLog, Long> {

	List<CallLog> findByMobile(String mobile);
}
