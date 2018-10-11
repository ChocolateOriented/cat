package com.cat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.Code;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.entity.Action;
import com.cat.module.entity.Task;
import com.cat.module.vo.ActionVo;
import com.cat.service.ActionService;
import com.cat.service.TaskService;

@RestController
@RequestMapping(value = "/cat/v1/action")
public class ActionController extends BaseController {

	@Autowired
	private ActionService actionService;

	@Autowired
	private TaskService taskService;

	/**
	 * 查询催收记录列表
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/list_action_record")
	public PageResponse<ActionVo> list(String customerId, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		Page<ActionVo> page = actionService.findPage(customerId, pageNum - 1, pageSize);
		PageResponse<ActionVo> pageResp = new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements());
		return pageResp;
	}

	/**
	 * 添加催收记录
	 * @param action
	 * @param bindingResul
	 * @return
	 */
	@PostMapping(value = "/add_action_record")
	public BaseResponse save(@RequestBody @Validated Action action, BindingResult bindingResul) {
		if (bindingResul.hasErrors()) {
			return new BaseResponse((int) ResultConstant.EMPTY_PARAM.code, getFieldErrorsMessages(bindingResul));
		}
		
		Task task = taskService.findTaskByOrderId(action.getOrderId());
		if (task == null) {
			BaseResponse response = new BaseResponse((int) ResultConstant.EMPTY_ENTITY.code, "此订单不存在");
			return response;
		}
		
		action.setCustomerId(task.getCustomerId());
		action.setCollectorId(task.getCollectorId());
		action.setCollectorName(task.getCollectorName());
		
		actionService.save(action);
		return BaseResponse.success();
	}

	/**
	 * 查询催收记录行动代码列表
	 * @return
	 */
	@GetMapping(value = "/list_action_feedback")
	public EntitiesResponse<Code> listCode() {
		List<Code> actionCodes = actionService.listActionFeedback();
		return new EntitiesResponse<Code>(actionCodes);
	}
	/**
	 * 催收记录次数详情
	 * @return
	 */
	@GetMapping(value = "/list_action_record_detail")
	public EntitiesResponse<ActionVo> listCodeDetail(String customerId,String mobile) {
		List<ActionVo> actionCodes = actionService.listCodeDetail(customerId,mobile);
		return new EntitiesResponse<ActionVo>(actionCodes);
	}
}
