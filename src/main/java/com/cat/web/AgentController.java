package com.cat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.annotation.RoleAuth;
import com.cat.exception.ServiceException;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.CommonResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.entity.Agent;
import com.cat.module.entity.CollectorCallLog;
import com.cat.module.enums.AgentStatus;
import com.cat.module.enums.Role;
import com.cat.module.vo.AgentStatisticVo;
import com.cat.module.vo.CollectorCallLogVo;
import com.cat.service.AgentService;
import com.cat.service.CollectorCallLogService;

@RestController
@RequestMapping(value = "/cat/v1/agent")
public class AgentController extends BaseController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private CollectorCallLogService collectorCallLogService;

	/**
	 * 发起呼叫
	 * @param userId
	 * @param callLog
	 * @param bindingResul
	 * @return
	 */
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

	/**
	 * 变更坐席状态
	 * @param userId
	 * @param changeAgent
	 * @return
	 */
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
	/**
	 * 获取催收员通话记录
	 * @param collectorCallLogVo
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@RoleAuth(include = Role.COLLECTOR)
	@GetMapping(value="/list_collector_call_log")
	public BaseResponse list(CollectorCallLogVo collectorCallLogVo, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize,@RequestHeader("User-Id") String userId){
		PageResponse<CollectorCallLogVo> pageResponse =  agentService.list(collectorCallLogVo,pageNum,pageSize,userId);
		return pageResponse;
	} 
	/**
	 * 获取坐席信息
	 * @param userId
	 * @return
	 */
	@RoleAuth(include = Role.COLLECTOR)
	@GetMapping(value="/get_agent_info")
	public BaseResponse getAgentInfo(@RequestHeader("User-Id") String userId){
		Agent agent =  agentService.findTopByCollectorId(userId);
		return new CommonResponse<Agent>(agent);
	} 
	/**
	 * 获取坐席统计信息
	 * @param userId
	 * @return
	 */
	@RoleAuth(include = Role.COLLECTOR)
	@GetMapping(value="/get_agent_statistic")
	public BaseResponse getAgentStatistic(@RequestHeader("User-Id") String userId){
		AgentStatisticVo agentStatisticVo =  agentService.getAgentStatistic(userId);
		return new CommonResponse<AgentStatisticVo>(agentStatisticVo);
	} 
}
