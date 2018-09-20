package com.cat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.Code;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.PageResponse;
import com.cat.service.ContactService;

@RestController
@RequestMapping(value = "/cat/contact")
public class ContactController extends BaseController {

	@Autowired
	private ContactService contactService;

	@GetMapping(value = "/list_calllog")
	public PageResponse<String> listCallLog(String mobile, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return null;
	}

	@GetMapping(value = "/list_target_type")
	public EntitiesResponse<Code> listTargetType() {
		List<Code> targetTypes = contactService.listTargetType();
		return new EntitiesResponse<Code>(targetTypes);
	}
}
