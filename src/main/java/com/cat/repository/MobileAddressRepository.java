package com.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.MobileAddress;

public interface MobileAddressRepository extends JpaRepository<MobileAddress, Long> {

	MobileAddress findTopByPre7(String pre7);
}
