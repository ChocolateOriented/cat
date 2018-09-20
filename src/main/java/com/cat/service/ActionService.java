package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cat.module.dto.Code;
import com.cat.module.entity.Action;
import com.cat.module.enums.ActionCode;
import com.cat.repository.ActionRepository;

@Service
public class ActionService extends BaseService {

	@Autowired
	private ActionRepository actionRepository;

	public Page<Action> findPage(String ownerId, int pageNum, int pageSize) {
		Page<Action> actions = actionRepository.findByOwnerIdOrderByCreateTimeDesc(ownerId, new PageRequest(pageNum, pageSize));
		return actions;
	}

	public void save(Action action) {
		actionRepository.save(action);
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
