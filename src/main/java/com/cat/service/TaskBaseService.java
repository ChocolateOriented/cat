package com.cat.service;

import com.cat.mapper.TaskMapper;
import com.cat.module.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cyuan on 2018/9/21.
 */
@Service
public class TaskBaseService {
    @Autowired
    private TaskMapper taskMapper;

    public Task findTaskByOrderId(String orderId) {
        return taskMapper.findTaskByOrderId(orderId);
    }

    public void insert(Task task) {
        taskMapper.insert(task);
    }

    public Task findByOrderId(String orderId) {
        return null;
    }

    public void updateTaskStatus(Task dbTask) {

    }
}
