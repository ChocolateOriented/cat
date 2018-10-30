package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent findTopByCollectorId(String collectorId);
}
