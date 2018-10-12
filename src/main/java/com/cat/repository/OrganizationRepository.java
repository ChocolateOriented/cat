package com.cat.repository;

import com.cat.module.entity.Organization;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jxli on 2018/9/19.
 */
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

	Organization findTop1ByleaderId(Long leaderId);
}
