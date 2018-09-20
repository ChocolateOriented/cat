package com.cat.web;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.TaskDto;
import com.cat.service.TaskService;
import com.cat.util.DateUtils;
import com.cat.util.excel.ExportExcel;

@RestController
@RequestMapping(value = "/cat/task")
public class taskController extends BaseController {

	@Autowired
	private TaskService taskService;
	/**
	 * 查询列表
	 * @param taskDto
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public BaseResponse list(TaskDto taskDto, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize,HttpServletRequest request){
		String userId = request.getHeader("User-Id");
		PageResponse<TaskDto> pageResponse =  taskService.list(taskDto,pageNum,pageSize,userId);
		return pageResponse;
	}
	/**
	 * 导出
	 * @param orderIds
	 */
	@RequestMapping(value="export")
	public void export(@RequestBody List<String> orderIds, HttpServletResponse response){
		List<TaskDto> exportList = taskService.findByOrderIds(orderIds);
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
	/**
	 * 手动分案页面所需数据
	 * @return
	 */
	@RequestMapping(value="manual_division_page")
	public BaseResponse manualDivisionPage(){
		EntitiesResponse<TaskDto> response = new EntitiesResponse<>();
		List<TaskDto>  list = taskService.findUserList();
		response.setEntitese(list);
		return response;
	}
	/**
	 *手动分案操作
	 * @param orderIds
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="manual_division_operation")
	public BaseResponse manualDivisionOperation(@RequestBody List<String> orderIds,String userId){
	boolean bol = taskService.manualDivisionOperation(orderIds,userId);
		return bol ? BaseResponse.success() : BaseResponse.fail();
	}
	/**
	 * 用户的通讯录
	 * @param ownerId
	 * @return
	 */
	@RequestMapping(value="list_addressbook")
	public BaseResponse listAddressbook(String ownerId){
		EntitiesResponse<TaskDto> response = new EntitiesResponse<>();
		List<TaskDto>  list = taskService.findUserList();
		response.setEntitese(list);
		return response;
	}
}
