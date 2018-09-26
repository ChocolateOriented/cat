/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cat.module.entity.TaskLog;

/**
 * 催收任务logDAO接口
 */
public interface TaskLogMapper  {
	
	
	/**
	 * 批量保存任务日志Log
	 * @param TaskLog
	 * @return
	 */
	public int batchInsertTaskLog(@Param("list")List<TaskLog> list);

	int deleteByPrimaryKey(Long id);

	int insert(TaskLog record);

	TaskLog selectByPrimaryKey(Long id);

	List<TaskLog> selectAll();

	int updateByPrimaryKey(TaskLog record);
}