package com.cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

	List<Action> findByCustomerIdAndContactTel(String customerId, String contactTel);

}
