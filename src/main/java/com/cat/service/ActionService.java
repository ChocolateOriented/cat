package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.entity.Action;
import com.cat.module.enums.ActionCode;

@Service
public class ActionService extends BaseService {

	public Page<Action> findPage(int ownerId, int pageNum, int pageSize) {
		return null;
	}

	public void save(Action action) {
		
	}

	public List<Code> listActionCode() {
		List<Code> codes = new ArrayList<>();
		for (ActionCode actionCode : ActionCode.values()) {
			Code code = new Code();
			code.setCode(actionCode.name());
			code.setDesc(actionCode.getDesc());
			codes.add(code);
		}
		return codes;
	}
}
