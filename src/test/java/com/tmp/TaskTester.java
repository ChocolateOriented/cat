package com.tmp;

import com.cat.CatApplication;
import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.User;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.module.enums.UserStatus;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.cat.service.ScheduledTaskService;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatApplication.class)
@EnableAutoConfiguration
public class TaskTester {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ScheduledTaskService service;
	
	@Test
	public void testSearch() {
		List<DivisionUserDto> users = taskRepository.findPeopleSumcorpusamountByDunningcycle("Q1");
		System.out.println(users);

	}
	public static final String  C0 = "Q0";      //  提醒0-0
	public static final String  C_P1 = "Q1";	 
	public static final String  P1_P2 = "Q2";	 
	public static final String  P2_P3 = "Q3";	 
	public static final String  P3_P4 = "Q4";	 
	public static final String  P4_P5 = "Q5";	 
	
	public static final String  AUTO_ADMIN = "auto_admin";	 

	@Test
	public void testInsert() {
//		scheduledTaskService.autoAssign();
//		service.autoAssignCycle(CollectTaskStatus.TASK_IN_PROGRESS.toString(),C0, "-1", "0");
		service.autoAssignNewOrder();
	}
}
