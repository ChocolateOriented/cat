package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.CollectorCallLog;
import com.cat.module.enums.CallType;

public interface CollectorCallLogRepository extends JpaRepository<CollectorCallLog, Long> {

	CollectorCallLog findTopByCtiUuidAndCallType(String ctiUuid, CallType callType);

}
