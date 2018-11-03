/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.mapper;

import java.util.List;

import com.cat.module.dto.CurrentOrderDto;
import com.cat.module.dto.OrderDetailsReportDto;
import org.apache.ibatis.annotations.Param;

import com.cat.module.entity.TaskLog;

/**
 * 催收任务logDAO接口
 */
public interface TaskLogMapper  {
	
	
	/**
	 * 批量保存任务日志Log
	 * @param
	 * @return
	 */
	public int batchInsertTaskLog(@Param("list")List<TaskLog> list);

	int deleteByPrimaryKey(Long id);

	int insert(TaskLog record);

	TaskLog selectByPrimaryKey(Long id);

	List<TaskLog> selectAll();

	int updateByPrimaryKey(TaskLog record);

	/**
	 * 获取催收员每天任务次数和总金额
	 * @param collectorId
	 * @return
	 */
	List<CurrentOrderDto> getDayTaskCount(String collectorId);

	Integer getShouldPayOrder(@Param("collectorId") String collectorId, @Param("orderStatus")String orderStatus);

	/**
	 * 查出催收员当天行为状态为'POSTPONE', 'FINISHED'的所有订单
	 * @param collectorId
	 * @return
	 */
	List<TaskLog> getListOfDayOrder(String collectorId);

    List<OrderDetailsReportDto> getAllOrderDetails();
}