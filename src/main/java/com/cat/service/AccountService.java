package com.cat.service;

import com.alibaba.fastjson.JSON;
import com.cat.module.dto.LoginInfo;
import com.cat.module.dto.RegisterDto;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.repository.UserRepository;
import com.mo9.nest.client.AuthClient;
import com.mo9.nest.client.result.RegisterResults;
import com.mo9.nest.client.result.RegisterResults.CodeEnum;
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

  public LoginInfo registerByEmail(RegisterDto registerDto, HttpServletRequest request){
    String email = registerDto.getEmail();
    String validateCode = registerDto.getValidateCode();
    if (!isValidValidateCode(email,validateCode)){
      throw new RuntimeException("无效验证码");
    }

    RegisterResults registerResults;
    try {
     registerResults = authClient.register(request,registerDto.getEmail(),
          registerDto.getPassword());
     if (registerResults==null || !registerResults.getCode().equals(CodeEnum.SUCCESS)){
       logger.info("注册失败"+ registerDto.toString()+JSON.toJSONString(registerResults));
       throw new RuntimeException("注册失败");
     }
      logger.debug(JSON.toJSONString(registerResults));
    } catch (IOException e) {
      logger.info("调用用户中心失败"+ registerDto.toString(),e);
      throw new RuntimeException("注册失败",e);
    }
    String accountCode = registerResults.getAccount().getCode();
    User user = new User();
    user.setName(registerDto.getName());
    user.setEmail(registerDto.getEmail());
    user.setStatus(UserStatus.NORMAL);
    user.setId(accountCode);
    userRepository.save(user);
    return new LoginInfo(accountCode, registerResults.getToken());
  }

  public LoginInfo login(String email,String password){

    //TODO
    return null;
  }
  public void sendValidateCode(String email) {
    //TODO
  }

  public boolean isValidValidateCode(String email,String validateCode) {
    //TODO
    return true;
  }

}
