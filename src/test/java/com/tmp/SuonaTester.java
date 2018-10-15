package com.tmp;

import com.cat.CatApplication;
import com.cat.manager.MessageSender;
import com.cat.module.dto.DivisionUserDto;
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
public class SuonaTester {

	@Autowired
	private MessageSender messageSender;
	
	@Test
	public void createTemplate() {

	}
}
