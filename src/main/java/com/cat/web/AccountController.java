package com.cat.web;

import com.cat.module.dto.EmailDto;
import com.cat.module.dto.LoginDto;
import com.cat.module.dto.ResetPasswordDto;
import com.cat.module.vo.LoginVo;
import com.cat.module.dto.RegisterDto;
import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.service.AccountService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jxli on 2018/9/20.
 */
@RestController
@RequestMapping(value = "/cat/v1/account")
public class AccountController extends BaseController {
  @Autowired
  AccountService accountService;

  @PostMapping("login")
  public Results Login(HttpServletRequest request, @Validated @RequestBody LoginDto loginDto,
      BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
    }
    LoginVo loginVo;
    try {
      loginVo = accountService.login(loginDto.getEmail(),loginDto.getPassword(), request);
    }catch (Exception e){
      logger.info("登录失败"+loginDto,e);
      return new Results(ResultConstant.INNER_ERROR,"登录失败:"+e.getMessage());
    }
    return Results.ok().putData(loginVo);
  }

  @PostMapping("register")
  public Results register(HttpServletRequest request, @Validated @RequestBody RegisterDto
      registerDto, BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
    }

    try {
      accountService.registerByEmail(registerDto,request);
    }catch (Exception e){
      logger.info("注册失败"+registerDto,e);
      return new Results(ResultConstant.INNER_ERROR,"注册失败:"+e.getMessage());
    }
    return Results.ok();
  }

  @PostMapping("send_validate_code")
  public Results sendValidateCode(@Validated @RequestBody EmailDto
      emailDto, BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
    }

    try {
      accountService.sendValidateCode(emailDto.getEmail());
    }catch (Exception e){
      logger.info("发送验证码失败"+emailDto.getEmail(),e);
      return new Results(ResultConstant.INNER_ERROR,"发送验证码失败");
    }
    return Results.ok("验证码已发送至邮箱");
  }


  @PostMapping("send_reset_password_email")
  public Results sendResetPasswordEmail(@Validated @RequestBody EmailDto
      emailDto, BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
    }

    try {
      accountService.sendResetPasswordEmail(emailDto.getEmail());
    }catch (Exception e){
      logger.info("发送重置密码邮件失败"+emailDto.getEmail(),e);
      return new Results(ResultConstant.INNER_ERROR,"发送重置密码邮件失败");
    }
    return Results.ok();
  }

  @PostMapping("reset_password")
  public Results resetPassword(@Validated @RequestBody ResetPasswordDto resetPasswordDto,
      BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
    }

    try {
      accountService.resetPassword(resetPasswordDto);
    }catch (Exception e){
      logger.info("重置密码失败"+resetPasswordDto,e);
      return new Results(ResultConstant.INNER_ERROR,"重置密码失败:"+e.getMessage());
    }
    return Results.ok();
  }
}
