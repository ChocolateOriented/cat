package com.cat.web;

import com.cat.module.dto.RegisterDto;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.service.AccountService;
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
public class AccountController extends BaseController {
  @Autowired
  AccountService accountService;

  @PostMapping("login")
  public Results Login(String email, String password){
    String Key = "user";
    return Results.ok();
  }

  @PostMapping("register")
  public Results register(@RequestBody RegisterDto registerDto,HttpServletRequest request){
    try {
      accountService.registerByEmail(registerDto,request);
    }catch (Exception e){
      logger.info("注册失败"+registerDto,e);
      return new Results(ResultConstant.INNER_ERROR,"注册失败");
    }
    return Results.ok();
  }

  @PostMapping("send_validate_code")
  public Results sendValidateCode(@RequestBody String email,HttpServletRequest request){
    try {
      accountService.sendValidateCode(email);
    }catch (Exception e){
      logger.info("发送验证码失败"+email,e);
      return new Results(ResultConstant.INNER_ERROR,"发送验证码失败");
    }
    return Results.ok();
  }
}
