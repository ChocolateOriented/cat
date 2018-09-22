package com.cat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.risk.CallLog;
import com.cat.module.enums.ContactTargetType;
import com.cat.module.vo.ContactVo;
import com.cat.repository.CallLogRepository;

@Service
public class ContactService extends BaseService {

	@Autowired
	private CallLogRepository callLogRepository;

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
}
