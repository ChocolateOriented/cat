package com.cat.repository;

import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.Task;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by cjw on 2018/9/19.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

  @Query(value = "SELECT a.id, a.organization_id, a.`status`,a.`name`,a.collect_cycle,"
      + " a.role,a.auto_division, "
      + " SUM(IFNULL(t.loan_amount,0)) AS sum_corpus_amount"
      + " FROM  t_user a "
      + "   left join t_task t on t.collector_id = a.id"
      + "   AND t.collect_cycle = ?1 "
      + " WHERE a.collect_cycle LIKE concat('%',?1,'%') "
      + "   AND a.status = 'NORMAL' AND a.auto_division = 1"
      + "   AND a.product_type = ?2 "
      + " GROUP BY a.id "
      + " ORDER BY sum_corpus_amount", nativeQuery = true)
  List<DivisionUserDto> findPeopleSumcorpusamountByDunningcycle(String dunningcycle,String
      productType);

  @Modifying
  @Query("update Task t set t.actionFeedback = ?2, t.collectTelRemark = ?3, t.collectTime = ?4"
      + " where t.orderId = ?1")
  int updateTaskActionByOrderId(String orderId, String actionFeedback, String remark,
      Date collectTime);

  Task findTopByOrderId(String orderId);

  Task findTopByMobileOrderByOrderIdDesc(String mobile);
}
