package com.cat.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.TaskDto;
import com.cat.service.ReliefService;

@RestController
@RequestMapping(value = "/cat/v1/task")
public class ReliefController extends BaseController {

	@Autowired
	private ReliefService reliefService;

	@PostMapping(value = "relief_amount")
	public BaseResponse reliefAmount(@RequestBody TaskDto taskDto, HttpServletRequest request) {
		String userId = request.getHeader("User-Id");
		BaseResponse baseResponse =	reliefService.reliefAmount(taskDto.getOrderId(), taskDto.getReliefAmount(), userId); 
		
		return baseResponse;
	}
}
