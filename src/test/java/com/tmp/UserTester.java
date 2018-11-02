package com.tmp;

import com.alibaba.fastjson.JSON;
import com.cat.CatApplication;
import com.cat.module.dto.BlackListDto;
import com.cat.module.entity.Role;
import com.cat.module.entity.RolePermission;
import com.cat.module.entity.User;
import com.cat.module.enums.Role_n;
import com.cat.module.enums.UserStatus;
import com.cat.repository.RolePermissionRepository;
import com.cat.repository.RoleRepository;
import com.cat.repository.TaskRepository;
import com.cat.repository.UserRepository;
import com.cat.service.AccountService;
import com.cat.service.CustomerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.ManyToOne;
import org.apache.catalina.LifecycleState;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private RolePermissionRepository rolePermissionRepository;
  @Autowired
  private RequestMappingHandlerMapping requestMappingHandlerMapping;

  @Test
  public void insetRole() {
    Role role = new Role();
    role.setName("催收员");
    roleRepository.save(role);
    System.out.println(role.getId());

    RolePermission rolePermission1 = new RolePermission();
    rolePermission1.setPermission("user:test");
    rolePermission1.setRoleId(role.getId());
    rolePermissionRepository.save(rolePermission1);

    System.out.println(
        roleRepository.findOne(role.getId())
    );
  }

  @Test
  public void sendResetPasswordToken() throws Exception {
    accountService.sendResetPasswordEmail("jxli@mo9.com");
  }


  @Test
  public void testSearch() {
//		List<DivisionUserDto> users = taskRepository.findPeopleSumcorpusamountByDunningcycle("Q1");
//		System.out.println(users);

  }

  @Test
  public void testInsert() {
    for (int i = 0; i <= 200; i++) {
      User user = new User();
      user.setId("102" + i);
      user.setName("test101" + i);
      user.setOrganizationId(2L);
      user.setStatus(UserStatus.NORMAL);
      user.setAutoDivision(false);
      user.setRoleN(Role_n.COLLECTOR);
      user.setAutoDivision(false);
      String collectCycle = "Q0,Q1";
      if (i > 100) {
        user.setOrganizationId(1L);
        /*collectCycle = "Q3,Q4";*/
        user.setAutoDivision(true);
      }
      user.setCollectCycle(collectCycle);
      userRepository.save(user);
    }
  }

  @Test
  public void blacklist() {
    BlackListDto blackDto = new BlackListDto();
    blackDto.setCustomerId("AA20A480E526D644D13D9AC5593D2688");
    blackDto.setReason("测试");
    customerService.blackList(blackDto);
  }

  @Test
  public void menulist() {
    Map<String, List<String>> permissionMapping = new HashMap<>();

    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
    for (Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
      PatternsRequestCondition p = entry.getKey().getPatternsCondition();
      HandlerMethod handlerMethod = entry.getValue();
      for (String url : p.getPatterns()) {

        RequiresPermissions permissionsAnnotation = handlerMethod.getMethodAnnotation
            (RequiresPermissions.class);
        if (permissionsAnnotation != null) {
          for (String permission : permissionsAnnotation.value()) {
            putPermissionMapping(permissionMapping, permission,url);
          }
        }else {
          putPermissionMapping(permissionMapping,"anonymous",url);
        }
      }
    }
    System.out.println(JSON.toJSONString(permissionMapping));
  }

  private void putPermissionMapping(
      Map<String, List<String>> permissionMapping,
      String permission, String url) {

    List<String> urls = permissionMapping.get(permission);
    if (urls == null) {
      urls = new ArrayList<>();
      permissionMapping.put(permission, urls);
    }
    urls.add(url);
  }

  public static void main(String[] args) {
    for (int i = 0; i < 40; i++) {
      String collectCycle;
      String sql = "INSERT INTO `cat`.`t_user` (`id`, `create_by`, `create_time`, `update_by`, "
          + "`update_time`, `collect_cycle`, `name`, `organization_id`, `status`, `email`, `role`,"
          + " `auto_division`, `password`) VALUES ("
          + "'49525123342716108" + i
          + "', 'sys', '2018-09-28 15:11:35', 'sys', '2018-09-28 15:11:35',"
          + " 'Q0,Q1', 'caozihao', '1', 'NORMAL', '154791107@qq.com', 'COLLECTOR', '1',"
          + " '2e36656bbee6a943bfa0ee0c64e88bed4831d2a8');";
      System.out.println(sql);
    }
  }
}
