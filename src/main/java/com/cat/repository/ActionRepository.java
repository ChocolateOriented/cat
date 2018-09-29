package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

}
