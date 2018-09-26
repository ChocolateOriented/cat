package com.cat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cat.module.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.mapper.TaskMapper;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.TaskDto;
import com.cat.module.entity.Task;
import com.cat.module.entity.User;
import com.cat.module.enums.Role;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class TaskService extends BaseService {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	TaskMapper taskMapper;
	@Autowired
	private TaskRepository taskRepository;

	/**
	 * 获取任务列表
	 * @param taskDto
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public PageResponse<TaskDto> list(TaskDto taskDto, Integer pageNum, Integer pageSize, String userId) {
		//userid值为是管理员身份,不然去查userid是否是主管
		User user = userRepository.findOne(userId);
		if(user == null){
			logger.warn("该用户不存在,userID={}",userId);
			return null;
		}
		//是主管
		if(user.getRole() == Role.ORGANIZATION_LEADER){
			taskDto.setOrganizationId(user.getOrganizationId());
			taskDto.setCollectorId(null);
		 }
		//是催收员
		if(user.getRole() == Role.COLLECTOR){
			taskDto.setCollectorId(userId);
		}
		//进行查询
		PageHelper.startPage(pageNum, pageSize);
		List<TaskDto> list = this.findList(taskDto);
		PageInfo<TaskDto> pageInfo = new PageInfo<>(list);
		return new PageResponse<TaskDto>(list, pageNum, pageSize, pageInfo.getTotal());
		
	}
		
	public List<TaskDto> findList(TaskDto taskDto) {
		return taskMapper.findList(taskDto);
	}

	/**
	 * 根据订单Id查询task实体
	 * @param orderId
	 * @return
	 */
	public Task findTaskByOrderId(String orderId) {
		return taskRepository.findTopByOrderId(orderId);
	}

	/**
	 * 更新催收记录结果
	 * @param orderId
	 * @param actionFeedback
	 * @param remark
	 * @param collectTime
	 */
	public void updateTaskActionFeedback(String orderId, String actionFeedback, String remark, Date collectTime) {
		taskRepository.updateTaskActionByOrderId(orderId, actionFeedback, remark, collectTime);
	}

	/**
	 * 获取所有的催收员的id和姓名
	 * @return
	 */
		public List<TaskDto> findUserList() {
		List<TaskDto> list = new ArrayList<>();
		Iterable<User> itreable = userRepository.findAll();
		Iterator<User> iterator = itreable.iterator();
		if(iterator.hasNext()){
			TaskDto taskDto = new TaskDto();
			User next = iterator.next();
			taskDto.setCollectorId(next.getId());
			taskDto.setCollectorName(next.getName());
			list.add(taskDto);
		}
		
		return list;
	}
	/**
	 * 手动分案
	 * 
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	public boolean assign(List<String> orderIds, String userId) {
		return false;
	}

	public void insert(Task task) {
		taskMapper.insert(task);
	}

	public Task findByOrderId(String orderId) {
		return taskMapper.findTaskByOrderId(orderId);
	}

	public void updateTaskStatus(Task dbTask) {
		taskMapper.updateByPrimaryKey(dbTask);
	}

}
