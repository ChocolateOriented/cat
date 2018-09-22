package com.cat.repository;

import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by cjw on 2018/9/19.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task,Long> {
/*
  @Query(value = "SELECT a,"
      + "  SUM(t.loanAmount) AS sumCorpusAmount"
      + " FROM  Task t left join t.collector a"
      + " WHERE a.collectCycle = concat('%',?1,'%') "
      + "   AND t.collectCycle = ?1 "
      + "   AND a.status = 0 AND a.autoDivision = 1"
      + " GROUP BY a.id "
      + " ORDER BY sumCorpusAmount")
*/

  @Query(value = "SELECT a.id, a.status, a.organization_id, a.email,a.name,a.collect_cycle,"
      + " a.role,a.auto_division, "
      + "  SUM(t.loan_amount) AS sum_corpus_amount"
      + " FROM  t_user a "
      + "   left join t_task t on t.collector_id = a.id"
      + " WHERE a.collect_cycle LIKE concat('%',?1,'%') "
      + "   AND t.collect_cycle = ?1 "
      + "   AND a.status = 0 AND a.auto_division = 1"
      + " GROUP BY a.id "
      + " ORDER BY sum_corpus_amount", nativeQuery = true)
  List<DivisionUserDto> findPeopleSumcorpusamountByDunningcycle(String dunningcycle);

}
