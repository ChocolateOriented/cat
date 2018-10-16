package com.cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.Relief;

public interface ReliefRepository extends JpaRepository<Relief, Long> {

	public List<Relief> findByOrderIdOrderByCreateTimeDesc(String orderId);
}
