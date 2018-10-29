package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.AgentLoginLog;

public interface AgentLoginLogRepository extends JpaRepository<AgentLoginLog, Long> {

}
