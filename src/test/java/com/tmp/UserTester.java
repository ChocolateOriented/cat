package com.tmp;

import com.cat.CatApplication;
import com.cat.module.entity.User;
import com.cat.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatApplication.class)
@EnableAutoConfiguration
public class UserTester {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testSearch() {
		User user = userRepository.findOne(1L);
		System.out.println(user  );
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setName("test");
		user.setStatus(1);
		userRepository.save(user);
		System.out.println(user);
	}

}