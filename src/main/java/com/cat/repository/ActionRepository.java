package com.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cat.module.entity.Action;

public interface ActionRepository extends PagingAndSortingRepository<Action, Long> {

	Page<Action> findByOwnerIdOrderByCreateTimeDesc(String ownerId, Pageable pageable);
}
