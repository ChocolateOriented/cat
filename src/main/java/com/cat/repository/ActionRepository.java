package com.cat.repository;

import java.util.List;

import com.cat.module.vo.ActionVo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.module.entity.Action;
import org.springframework.data.jpa.repository.Query;

public interface ActionRepository extends JpaRepository<Action, Long> {

	List<Action> findByCustomerIdAndContactTel(String customerId, String contactTel);
    @Query(value = "SELECT count(DISTINCT order_id) AS actionCount " +
            "FROM t_action " +
            "WHERE collector_id = ?1 " +
            "AND TO_DAYS(create_time) = TO_DAYS(NOW())",
            nativeQuery = true)
    long getCurrentActionCount(String collectorId);
}
