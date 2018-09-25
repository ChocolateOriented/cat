package com.cat.service;

import com.cat.mapper.TaskLogMapper;
import com.cat.module.entity.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cyuan on 2018/9/21.
 */
@Service
public class TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;
    public void insert(TaskLog taskLog) {
        taskLogMapper.insert(taskLog);
    }
}
