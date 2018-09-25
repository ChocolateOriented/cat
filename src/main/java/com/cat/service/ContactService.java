package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import com.cat.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.risk.CallLog;
import com.cat.module.enums.ContactTargetType;
import com.cat.module.vo.Contact;
import com.cat.repository.CallLogRepository;

@Service
public class ContactService extends BaseService {

	@Autowired
	private CallLogRepository callLogRepository;

	@Autowired
	private ContactMapper contactMapper;
	@DataSource(DynamicDataSource.RISK_DATASOURCE)
	public Page<Contact> findCalllog(String mobile, int pageNum, int pageSize) {
		List<CallLog> callLogs = callLogRepository.findByMobile(mobile);
		List<Contact> contacts = new ArrayList<>();
		
		for (CallLog callLog : callLogs) {
			Contact contact = new Contact();
		}
		
		return null;
	}

	public List<Code> listTargetType() {
		List<Code> codes = new ArrayList<>();
		for (ContactTargetType targetType : ContactTargetType.values()) {
			Code code = new Code();
			code.setCode(targetType.name());
			code.setDesc(targetType.getDesc());
			codes.add(code);
		}
		return codes;
	}

	public Integer countByCustomerId(String customerId) {
		return contactMapper.countByCustomerId(customerId);
	}

	public void deleteContact(String customerId) {
		contactMapper.deleteContact(customerId);
	}


	public void insert(com.cat.module.entity.Contact  contact) {
		contactMapper.insert(contact);
	}
}
