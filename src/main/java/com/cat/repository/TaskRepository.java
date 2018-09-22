package com.cat.repository;

import com.cat.module.dto.TaskDto;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by cjw on 2018/9/19.
 */
public interface TaskRepository extends PagingAndSortingRepository<TaskDto,Long> {

}
