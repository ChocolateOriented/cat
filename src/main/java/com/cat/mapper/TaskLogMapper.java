/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cat.module.entity.TaskLog;

/**
 * 催收任务logDAO接口
 * @author 徐盛
 * @version 2017-03-01
 */
public interface TaskLogMapper  {
	
	
	/**
	 * 批量保存任务日志Log
	 * @param TaskLog
	 * @return
	 */
	public int batchInsertTaskLog(@Param("list")List<TaskLog> list);
	
	
}