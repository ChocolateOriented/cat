package com.cat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.Code;
import com.cat.module.dto.EntitiesResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.vo.CallLogVo;
import com.cat.module.vo.ContactVo;
import com.cat.service.ContactService;

@RestController
@RequestMapping(value = "/cat/v1/contact")
public class ContactController extends BaseController {

	@Autowired
	private ContactService contactService;

	/**
	 * 查询通话记录列表
	 * @param mobile
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/list_call_log")
	public PageResponse<CallLogVo> listCallLog(String customerId,String mobile, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize) {
		Page<CallLogVo> page = contactService.findCalllog(customerId,mobile, pageNum, pageSize);
		PageResponse<CallLogVo> pageResp = new PageResponse<>();
		pageResp.setData(page);
		return pageResp;
	}

	/**
	 * 查询联系人类型列表
	 * @return
	 */
	@GetMapping(value = "/list_contact_type")
	public EntitiesResponse<Code> listTargetType() {
		List<Code> targetTypes = contactService.listTargetType();
		return new EntitiesResponse<Code>(targetTypes);
	}
	/**
	 * 客户的通讯录
	 * @param customerId
	 * @return
	 */
	@GetMapping(value="list_addressbook")
	public BaseResponse listAddressbook(String customerId, @RequestParam(defaultValue = BaseController.DEFAULT_PAGE_NUM) Integer pageNum,
			@RequestParam(defaultValue = BaseController.DEFAULT_PAGE_SIZE) Integer pageSize){
		PageResponse<ContactVo> pageResp = contactService.findListAddressbook(customerId, pageNum, pageSize);
		return pageResp;
	}
}
