package com.cat.service;

import com.alibaba.fastjson.JSON;
import com.cat.module.dto.RegisterDto;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.repository.UserRepository;
import com.mo9.nest.client.AuthClient;
import com.mo9.nest.client.result.RegisterResults;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jxli on 2018/9/21.
 */
@Service
public class AccountService extends BaseService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthClient authClient;

  public void registerByEmail(RegisterDto registerDto, HttpServletRequest request){
    //TODO 检测验证码, 根据邮箱
    RegisterResults registerResults;
    try {
     registerResults = authClient.register(request,registerDto.getEmail(),
          registerDto.getPassword());
      logger.debug(JSON.toJSONString(registerResults));
    } catch (IOException e) {
      logger.info("调用用户中心失败"+ registerDto.toString(),e);
      throw new RuntimeException("注册失败",e);
    }
    User user = new User();
    user.setName(registerDto.getName());
    user.setEmail(registerDto.getEmail());
    user.setStatus(UserStatus.NORMAL);
    user.setId(Long.valueOf(registerResults.getAccount().getCode()));
    userRepository.save(user);
  }

  public void sendValidateCode(String email) {

  }
}
