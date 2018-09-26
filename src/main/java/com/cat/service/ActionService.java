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

@Service
public class ActionService extends BaseService {

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private TaskService taskService;

	/**
	 * 根据借款人Id查询催收历史记录分页
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Action> findPage(String customerId, int pageNum, int pageSize) {
		Page<Action> actions = actionRepository.findByCustomerIdOrderByCreateTimeDesc(customerId, new PageRequest(pageNum, pageSize));
		return actions;
	}

	/**
	 * 保存催收记录
	 * @param action
	 */
	@Transactional
	public void save(Action action) {
		action.setId(generateId());
		action = actionRepository.save(action);
		
		String actionFeedback = action.getActionFeedback() == null ? null : action.getActionFeedback().name();
		taskService.updateTaskActionFeedback(action.getOrderId(), actionFeedback, action.getRemark(), action.getCreateTime());
	}

	/**
	 * 获取所有行动码列表
	 * @return
	 */
	public List<Code> listActionFeedback() {
		List<Code> codes = new ArrayList<>();
		for (ActionFeedback actionFeedback : ActionFeedback.values()) {
			Code code = new Code();
			code.setCode(actionFeedback.name());
			code.setDesc(actionFeedback.getDesc());
			codes.add(code);
		}
		return codes;
	}
}
