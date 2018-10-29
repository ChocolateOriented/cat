package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.CollectorCallLog;

public interface CollectorCallLogRepository extends JpaRepository<CollectorCallLog, Long> {

	CollectorCallLog findTopByCtiUuid(String ctiUuid);
}
