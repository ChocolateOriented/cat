package com.cat.web;

import com.cat.module.dto.RegisterDto;
import com.cat.module.dto.result.Results;
import com.cat.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jxli on 2018/9/20.
 */
@RestController
@RequestMapping(value = "/cat/account")
public class UserController {
  @Autowired
  UserService userService;

  @PostMapping("login")
  public Results Login(String userName, String password){
    //TODO 密码MD5, 支持原系统密码
    //userService.login();
    //生成token, 返回, 并存入redis
    String Key = "user";
    return Results.ok();
  }

  @PostMapping("register")
  public Results register(@RequestBody RegisterDto registerDto,HttpServletRequest request){
    //TODO 密码MD5, 支持原系统密码
    System.out.println(registerDto);
    userService.registerByEmail(registerDto,request);
    //生成token, 返回, 并存入redis
    String Key = "user";
    return Results.ok();
  }
}
