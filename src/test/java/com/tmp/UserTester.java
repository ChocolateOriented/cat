package com.tmp;

import com.cat.CatApplication;
import com.cat.module.dto.BlackListDto;
import com.cat.module.dto.DivisionUserDto;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.cat.service.AccountService;
import com.cat.service.CustomerService;
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
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;

	@Test
	public void sendResetPasswordToken() throws Exception {
		accountService.sendResetPasswordToken("jxli@mo9.com");
	}


	@Test
	public void testSearch() {
		List<DivisionUserDto> users = taskRepository.findPeopleSumcorpusamountByDunningcycle("Q1");
		System.out.println(users);

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
	@Test
	public void blacklist(){
		BlackListDto blackDto = new BlackListDto();
		blackDto.setCustomerId("AA20A480E526D644D13D9AC5593D2688");
		blackDto.setReason("测试");
		customerService.blackList(blackDto);
	}

	public static void main(String[] args) {
		for (int i=0;i<40;i++){
			String collectCycle;
			String sql = "INSERT INTO `cat`.`t_user` (`id`, `create_by`, `create_time`, `update_by`, "
				+ "`update_time`, `collect_cycle`, `name`, `organization_id`, `status`, `email`, `role`,"
				+ " `auto_division`, `password`) VALUES ("
				+ "'49525123342716108"+i+"', 'sys', '2018-09-28 15:11:35', 'sys', '2018-09-28 15:11:35',"
				+ " 'Q0,Q1', 'caozihao', '1', 'NORMAL', '154791107@qq.com', 'COLLECTOR', '1',"
				+ " '2e36656bbee6a943bfa0ee0c64e88bed4831d2a8');";
			System.out.println(sql);
		}
	}
}
