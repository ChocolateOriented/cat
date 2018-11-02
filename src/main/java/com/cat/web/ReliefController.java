package com.cat.web;

import com.cat.module.enums.Role_n;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.annotation.RoleAuth;
import com.cat.exception.ServiceException;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.Code;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.entity.Relief;
import com.cat.module.entity.User;
import com.cat.service.ReliefService;

@RestController
@RequestMapping(value = "/cat/v1/task")
public class ReliefController extends BaseController {

	@Autowired
	private ReliefService reliefService;

	/**
	 * 减免操作
	 * @param relief
	 * @param bindingResul
	 * @return
	 */
	@PostMapping(value = "relief_amount")
	@RoleAuth(exclude = Role_n.COLLECTOR)
	public BaseResponse reliefAmount(@RequestBody @Validated Relief relief, BindingResult bindingResul) {
		if (bindingResul.hasErrors()) {
			return new BaseResponse((int) ResultConstant.EMPTY_PARAM.code, getFieldErrorsMessages(bindingResul));
		}
		
		User user = getCurrentUser();
		relief.setCollectorId(user.getId());
		relief.setCollectorName(user.getName());
		
		BaseResponse response = null;
		try {
			reliefService.reliefAmount(relief);
			response = BaseResponse.success();
		} catch (ServiceException e) {
			response = new BaseResponse((int) ResultConstant.INNER_ERROR.code, e.getMessage());
		}
		
		return response;
	}

	/**
	 * 查询减免记录列表
	 * @param orderId
	 * @return
	 */
	@GetMapping(value = "list_relief_record")
	public EntitiesResponse<Relief> listReliefRecord(String orderId) {
		List<Relief> reliefRecords = reliefService.findByOrderIdOrderByCreateTimeDesc(orderId);
		EntitiesResponse<Relief> response = new EntitiesResponse<>(reliefRecords);
		return response;
	}

	/**
	 * 查询减免原因列表
	 * @return
	 */
	@GetMapping(value = "/list_relief_reason")
	public EntitiesResponse<Code> listReliefReason() {
		List<Code> reliefReasons = reliefService.listReliefReason();
		return new EntitiesResponse<Code>(reliefReasons);
	}
}
