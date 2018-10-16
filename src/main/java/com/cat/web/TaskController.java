package com.cat.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.AssignDto;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.CollectDto;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.TaskDto;
import com.cat.module.entity.Organization;
import com.cat.module.entity.User;
import com.cat.service.ScheduledTaskService;
import com.cat.service.TaskService;
import com.cat.util.DateUtils;
import com.cat.util.excel.ExportExcel;

@RestController
@RequestMapping(value = "/cat/v1/task")
public class TaskController extends BaseController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private ScheduledTaskService scheduledTaskService;
	/**
	 * 查询列表
	 * @param taskDto
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@GetMapping(value="list_task")
	public BaseResponse list(TaskDto taskDto, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize,HttpServletRequest request){
		String userId = request.getHeader("User-Id");
		PageResponse<TaskDto> pageResponse =  taskService.list(taskDto,pageNum,pageSize,userId);
		return pageResponse;
	}

	/**
	 * 获取催收员信息
	 * @return
	 */
	@GetMapping(value="list_collector_info")
	public BaseResponse listCollectorInfo(){
		EntitiesResponse<User> response = new EntitiesResponse<>();
		List<User>  list = taskService.findUserList();
		response.setEntitese(list);
		return response;
	}
	
	/**
	 *手动分案
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	@PostMapping(value="assign")
	public BaseResponse assign(@RequestBody AssignDto assignDto,@RequestHeader("User-Id")String  userId){
		BaseResponse baseResponse = taskService.assign(assignDto,userId);
		return baseResponse;
	}
	
	/**
	 * 导出
	 * @param orderIds
	 */
//	@RequestMapping(value="export_task")
	public void export(@RequestBody List<String> orderIds, HttpServletResponse response){
		TaskDto taskDto = new TaskDto();
		List<TaskDto> exportList = taskService.findList(taskDto);
		if (null == exportList || exportList.isEmpty()) {
			logger.warn("未查询到数据,orderIds={}",orderIds);
			return;
		}
		try {
			String fileName = DateUtils.getDate("yyyy-MM-dd HHmmss") + ".xlsx";
			new ExportExcel("逾期催收数据", TaskDto.class).setDataList(exportList).write(response, fileName).dispose();
		} catch (Exception e) {
			logger.warn("导出失败！失败信息：" + e.getMessage());
		}
	}
	@RequestMapping(value="auto_assign")
	public void autoAssign(){
		logger.info("手动开始自动分案");
		taskService.autoAssign();
		logger.info("手动自动分案结束");
	}
//	@RequestMapping(value="auto_assign_new_order")
//	public void autoAssignNewOrder(){
//		logger.info("开始新增未生成催收任务(task)的订单");
//		scheduledTaskService.autoAssignNewOrder();
//		logger.info("新增未生成催收任务(task)的订单结束");
//	}
	@RequestMapping(value="syn_address_book")
	public void synAddressBook(){
		logger.info("手动同步通讯录");
		taskService.synAddressBook();
		logger.info("手动同步通讯录结束");
	}

	@PostMapping(value="reload_address_book")
	public void reloadAddressBook(@RequestBody List<String> customerIds){
		//手动补拿通讯录
		taskService.reloadAddressBook(customerIds);
	}
	/**
	 * 手动分案获取所有机构
	 * @return
	 */
	@GetMapping(value="list_organization")
	public BaseResponse listOrganization(){
		EntitiesResponse<Organization> response = new EntitiesResponse<>();
		List<Organization>  list = taskService.findOrganizationList();
		response.setEntitese(list);
		return response;
	}
	/**
	 * 手动分案查询催收人员
	 * @return
	 */
	@GetMapping(value="list_collector")
	public BaseResponse listCollector(CollectDto collectDto){
		EntitiesResponse<CollectDto> response = new EntitiesResponse<>();
		List<CollectDto>  list = taskService.findCollectList(collectDto);
		response.setEntitese(list);
		return response;
	}
}
