package com.tmp;

import com.cat.CatApplication;
import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cat.module.enums.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatApplication.class)
@EnableAutoConfiguration
public class UserTester {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	
	@Test
	public void testSearch() {
//		List<DivisionUserDto> users = taskRepository.findPeopleSumcorpusamountByDunningcycle("Q1");
//		System.out.println(users);

	}

	@Test
	public void testInsert() {
		for (int i=0; i<=200; i++){
			User user = new User();
			user.setId("102"+i);
			user.setName("test101"+i);
			user.setOrganizationId(2L);
			user.setStatus(UserStatus.NORMAL);
			user.setAutoDivision(false);
			user.setRole(Role.COLLECTOR);
			user.setAutoDivision(false);
			String collectCycle = "Q0,Q1";
			if (i>100){
				user.setOrganizationId(1L);
				/*collectCycle = "Q3,Q4";*/
				user.setAutoDivision(true);
			}
			user.setCollectCycle(collectCycle);
			userRepository.save(user);
		}
	}
}
