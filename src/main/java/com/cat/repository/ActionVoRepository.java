package com.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cat.module.vo.ActionVo;

public interface ActionVoRepository extends JpaRepository<ActionVo, Long> {

	@Query(value = "select a.id, a.customer_id, a.order_id, t.order_status, t.repayment_time,"
			+ "a.contact_type, a.contact_name, a.contact_tel, a.action_feedback, a.remark, a.collector_id,"
			+ "a.collector_name, a.create_by, a.create_time, a.update_by, a.update_time"
			+ " from t_action a join t_task t on a.order_id = t.order_id where t.customer_id = ?1"
			+ " order by a.create_time desc /*#pageable*/",
			countQuery = "select count(*) from t_action a where a.customer_id = ?1 /*#pageable*/",
			nativeQuery = true)
	Page<ActionVo> findByCustomerIdOrderByCreateTimeDesc(String customerId, Pageable pageable);
}
