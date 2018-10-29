package com.cat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.exception.ServiceException;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.entity.Agent;
import com.cat.module.entity.CollectorCallLog;
import com.cat.module.enums.AgentStatus;
import com.cat.service.AgentService;
import com.cat.service.CollectorCallLogService;

@RestController
@RequestMapping(value = "/cat/v1/agent")
public class AgentController extends BaseController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private CollectorCallLogService collectorCallLogService;

	@PostMapping(value = "/originate_call")
	public BaseResponse originateCall(@RequestHeader("User-Id") String userId, @RequestBody @Validated CollectorCallLog callLog,
			BindingResult bindingResul) {
		if (bindingResul.hasErrors()) {
			return new BaseResponse((int) ResultConstant.EMPTY_PARAM.code, getFieldErrorsMessages(bindingResul));
		}
		
		Agent agent = agentService.findTopByCollectorId(userId);
		
		if (agent == null) {
			return new BaseResponse((int) ResultConstant.EMPTY_ENTITY.code, "当前用户未绑定坐席");
		}
		
		if (agent.getStatus() == AgentStatus.LOGGED_OUT) {
			return new BaseResponse((int) ResultConstant.EMPTY_ENTITY.code, "当前坐席已离线，不能外呼");
		}
		
		callLog.setAgent(agent.getAgent());
		callLog.setCollectorId(agent.getCollectorId());
		callLog.setExtension(agent.getExtension());
		
		try {
			collectorCallLogService.orignateCall(callLog);
		} catch (ServiceException e) {
			return new BaseResponse((int) ResultConstant.INNER_ERROR.code, e.getMessage());
		}
		
		return BaseResponse.success();
	}

	@PostMapping(value = "/change_agent_status")
	public BaseResponse changeAgentStatus(@RequestHeader("User-Id") String userId, @RequestBody Agent changeAgent) {
		if (changeAgent.getStatus() == null) {
			return new BaseResponse((int) ResultConstant.EMPTY_PARAM.code, "无效的坐席状态");
		}
		
		Agent currentAgent = agentService.findTopByCollectorId(userId);
		
		if (currentAgent == null) {
			return new BaseResponse((int) ResultConstant.EMPTY_ENTITY.code, "当前用户未绑定坐席");
		}
		
		if (currentAgent.getStatus() == changeAgent.getStatus()) {
			return BaseResponse.success();
		}
		
		try {
			agentService.changAgentStatus(currentAgent, changeAgent.getStatus());
		} catch (ServiceException e) {
			return new BaseResponse((int) ResultConstant.INNER_ERROR.code, e.getMessage());
		}
		
		return BaseResponse.success();
	}
}
