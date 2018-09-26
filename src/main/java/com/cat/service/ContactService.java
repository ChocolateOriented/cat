package com.cat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cat.mapper.ContactMapper;
import com.cat.module.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.risk.CallLog;
import com.cat.module.enums.ContactType;
import com.cat.module.vo.ContactVo;
import com.cat.repository.CallLogRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContactService extends BaseService {

	@Autowired
	private CallLogRepository callLogRepository;

	@Autowired
	private ContactMapper contactMapper;

	/**
	 * 查询风控通话记录
	 * @param mobile
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@DataSource(DynamicDataSource.RISK_DATASOURCE)
	public Page<ContactVo> findCalllog(String mobile, int pageNum, int pageSize) {
		List<CallLog> callLogs = callLogRepository.findByMobile(mobile);
		
		List<ContactVo> cotactVos = callLogs.stream().distinct()
			.map(callLog -> {
				ContactVo cv = new ContactVo();
				cv.setTel(callLog.getCallTel());
				return cv;
			})
			.sorted((ContactVo c1, ContactVo c2) -> c1.getTel().compareTo(c2.getTel()))
			.collect(Collectors.toList());
		
		int from = (pageNum - 1) * pageSize;
		int to = Math.min(pageNum * pageSize, cotactVos.size());

		Page<ContactVo> page = new Page<>();
		page.setEntities(cotactVos.subList(from, to));
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		page.setTotal(Long.valueOf(cotactVos.size()));

		return page;
	}

	/**
	 * 获取所有联系人类型
	 * @return
	 */
	public List<Code> listTargetType() {
		List<Code> codes = new ArrayList<>();
		for (ContactType targetType : ContactType.values()) {
			Code code = new Code();
			code.setCode(String.valueOf(targetType.getValue()));
			code.setDesc(targetType.getDesc());
			codes.add(code);
		}
		return codes;
	}

	/**
	 * 通讯录
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResponse<ContactVo> findListAddressbook(String customerId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContactVo> list = contactMapper.findListByCustomerId(customerId);
		PageInfo<ContactVo> pageInfo = new PageInfo<>(list);
		return new PageResponse<ContactVo>(list, pageNum, pageSize, pageInfo.getTotal());
	}

	/**
	 * 根据借款人Id查询通讯录
	 * @param customerId
	 * @return
	 */
	public List<Contact> fetchContactsByCustomerId(String customerId) {
		return contactMapper.fetchContactsByCustomerId(customerId);
	}

	public void insert(com.cat.module.entity.Contact  contact) {
		contactMapper.insert(contact);
	}

}
