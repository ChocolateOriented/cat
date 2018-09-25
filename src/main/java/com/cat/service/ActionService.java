package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cat.module.dto.Code;
import com.cat.module.entity.Action;
import com.cat.module.enums.ActionFeedback;
import com.cat.repository.ActionRepository;
import com.cat.repository.TaskRepository;

@Service
public class ActionService extends BaseService {

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private TaskRepository taskRepository;

	public Page<Action> findPage(String ownerId, int pageNum, int pageSize) {
		Page<Action> actions = actionRepository.findByOwnerIdOrderByCreateTimeDesc(ownerId, new PageRequest(pageNum, pageSize));
		return actions;
	}

	@Transactional
	public void save(Action action) {
		actionRepository.save(action);
		String actionFeedback = action.getActionFeedback() == null ? null : action.getActionFeedback().name();
		taskRepository.updateTaskActionByOrderId(action.getOrderId(), actionFeedback, action.getRemark());
	}

	public List<Code> listActionCode() {
		List<Code> codes = new ArrayList<>();
		for (ActionFeedback actionCode : ActionFeedback.values()) {
			Code code = new Code();
			code.setCode(actionCode.name());
			code.setDesc(actionCode.getDesc());
			codes.add(code);
		}
		return codes;
	}
}
