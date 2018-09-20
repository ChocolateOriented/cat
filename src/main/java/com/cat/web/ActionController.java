package com.cat.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.entity.Action;

@RestController
@RequestMapping(value = "/cat/action")
public class ActionController extends BaseController {

	@GetMapping(value = "/list")
	public PageResponse<Action> list(int ownerId, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return null;
	}

	@PostMapping(value = "/save")
	public BaseResponse save(int ownerId, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return BaseResponse.success();
	}
}
