package com.cat.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cat.module.dto.TaskDto;
import com.cat.module.entity.Organization;
import com.cat.module.entity.User;
import com.cat.module.vo.Contact;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.UserRepository;
@Service
public class TaskService extends BaseService {
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired 
	UserRepository userRepository;
	public Page<TaskDto> list(TaskDto taskDto, Integer pageNum, Integer pageSize, String userId) {
		//userid值为1是管理员身份不去查,不然去查userid是否是主管
		Long id = Long.parseLong(userId);
		if(id != 1){
			Organization organization = organizationRepository.findTop1ByleaderId(id);
			if(organization != null){
				//是主管
				taskDto.setOrganizationId(organization.getId());
			}else{
				//不是主管
				taskDto.setUserId(id);
			}
		}
		//然后去查
		
		
		
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

	public List<Contact> findListAddressbook(String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}


}
