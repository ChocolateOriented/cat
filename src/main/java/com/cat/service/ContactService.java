package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.Action;
import com.cat.module.enums.ContactTargetType;

@Service
public class ContactService extends BaseService {

	@DataSource(DynamicDataSource.RISK_DATASOURCE)
	public Page<Action> findCalllog(String mobile) {
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
}
