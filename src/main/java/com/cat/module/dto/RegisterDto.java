package com.cat.module.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/9/21.
 */
public class RegisterDto {
  @NotBlank(message = "请输入有效邮箱")
  @Email(message = "请输入有效邮箱")
  private String email;
  @NotBlank(message = "密码不能为空")
  private String password;
  @NotBlank(message = "昵称不能为空")
  private String name;
  @NotBlank(message = "请输入有效验证码")
  private String validateCode;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValidateCode() {
    return validateCode;
  }

  public void setValidateCode(String validateCode) {
    this.validateCode = validateCode;
  }

  @Override
  public String toString() {
    return "RegisterDto{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", validateCode='" + validateCode + '\'' +
        '}';
  }
}
