package com.cat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.Action;
import com.cat.service.ActionService;

@RestController
@RequestMapping(value = "/cat/action")
public class ActionController extends BaseController {

	@Autowired
	private ActionService actionService;

	@GetMapping(value = "/list")
	public PageResponse<Action> list(int ownerId, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		PageResponse<Action> pageResp = new PageResponse<>();
		Page<Action> page = actionService.findPage(ownerId, pageNum, pageSize);
		pageResp.setData(page);
		return pageResp;
	}

	@PostMapping(value = "/save")
	public BaseResponse save(@RequestBody Action action) {
		actionService.save(action);
		return BaseResponse.success();
	}

	@GetMapping(value = "/list_code")
	public EntitiesResponse<Code> listCode() {
		List<Code> actionCodes = actionService.listActionCode();
		return new EntitiesResponse<Code>(actionCodes);
	}
}
