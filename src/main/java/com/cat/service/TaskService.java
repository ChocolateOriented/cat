package com.cat.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.mapper.TaskMapper;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.TaskDto;
import com.cat.module.entity.Organization;
import com.cat.module.entity.User;
import com.cat.module.enums.Role;
import com.cat.module.vo.ContactVo;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.UserRepository;
import com.github.pagehelper.PageHelper;
@Service
public class TaskService extends BaseService {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	TaskMapper taskMapper;
	public PageResponse<TaskDto> list(TaskDto taskDto, Integer pageNum, Integer pageSize, String userId) {
		//userid值为是管理员身份,不然去查userid是否是主管
		Long id = Long.parseLong(userId);
		User user = userRepository.findOne(id);
		if(user == null){
			logger.warn("该用户不存在,userID={}",userId);
			return null;
		}
		//是主管
		if(user.getRole() == Role.ORGANIZATION_LEADER){
			taskDto.setOrganizationId(user.getOrganizationId());
		 }
		//是催收员
		if(user.getRole() == Role.COLLECTOR){
			taskDto.setUserId(id);
		}
		//进行查询
		PageHelper.startPage(pageNum, pageSize);
		List<TaskDto> list = taskMapper.findList(taskDto);
		return null;
		
	}
		

	public List<TaskDto> findByOrderIds(List<String> orderIds) {
		// TODO Auto-generated method stub
		return null;
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
			taskDto.setUserId(iterator.next().getId());
			taskDto.setDunningpeopleName(iterator.next().getName());
			list.add(taskDto);
		}
		
		return list;
	}
	/**
	 * 手动分案操作
	 * 
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	public boolean manualDivisionOperation(List<String> orderIds, String userId) {
		return false;
	}

	public List<ContactVo> findListAddressbook(String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}


}
