package com.cat.service;

import java.util.List;

import com.cat.module.dto.PageResponse;
import com.cat.module.dto.TaskDto;

public class TaskService extends BaseService {

	public PageResponse<TaskDto> list(TaskDto taskDto, Integer pageNum, Integer pageSize, String userId) {
		//userid值为1是管理员身份不去查,不然去查userid是否是主管
		if(true){
			//不是主管
			taskDto.setUserId(userId);
			
		}else{
			//是主管
			taskDto.setOrganizationId("organizationId");
		}
		//然后去查
		
		
		
		return null;
	}

	public List<TaskDto> findByOrderIds(List<String> orderIds) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskDto> findUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean manualDivisionOperation(List<String> orderIds, String userId) {
		// TODO Auto-generated method stub
		return false;
	}


}
