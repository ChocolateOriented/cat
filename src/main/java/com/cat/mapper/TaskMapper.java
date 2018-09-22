/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cat.module.entity.TaskLog;
import com.cat.module.dto.TaskDto;
import com.cat.module.entity.Task;

/**
 * 催收任务DAO接口
 * @author 徐盛
 * @version 2016-07-12
 */
public interface TaskMapper  {
	
	
	/**
	 * 批量更新过期任务
	 * @param ids
	 * @return
	 */
	public int batchUpdateExpiredTask(List<Task> dunningTasks);
	
	/**
	 * 根据催收周期查询延期任务
	 * @param dunningcycle
	 * @return
	 */
	public List<TaskLog> newfindDelayTaskByDunningcycle(@Param("dunningtaskstatus")String dunningtaskstatus,@Param("dunningcycle")String dunningcycle,@Param("begin")String begin,@Param("end")String end,@Param("debtbiztype")String debtbiztype);
	
	/**
	 * 根据逾期天数查询未生成任务task的订单
	 * @param day
	 * @return
	 */
	public List<TaskLog> newfingDelayOrderByNotTask(@Param("day")String day,@Param("debtbiztype")String debtbiztype);
	
	
	/**
	 * 批量添加任务
	 * @param ids
	 * @return
	 */
	public int batchinsertTask(List<Task> dunningTasks);

	
	/**
	 * atuoq0  催收预提醒订单的优质老用户（历史逾期1天内还清）
	 * @return
	 */
	public List<String> findAtuoQ0Dealcode(@Param("day")String day,@Param("payoffday")String payoffday);
	/**
	 * 查询任务list
	 * @param taskDto
	 * @return
	 */
	public List<TaskDto> findList(TaskDto taskDto);
	

}