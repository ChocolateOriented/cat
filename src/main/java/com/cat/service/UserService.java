package com.cat.service;

import com.alibaba.fastjson.JSON;
import com.cat.module.dto.RegisterDto;
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
public class UserService extends BaseService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthClient authClient;

  public void registerByEmail(RegisterDto registerDto, HttpServletRequest request){
    try {
      RegisterResults registerResults = authClient.register(request,registerDto.getEmail(),
          registerDto.getPassword());
      System.out.println(JSON.toJSONString(registerResults));
    } catch (IOException e) {
      logger.info("注册失败",e);
      return;
    }
    //
  }
}
