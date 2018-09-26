package com.cat.module.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/9/25.
 */
public class LoginDto {
  @NotBlank(message = "请输入有效邮箱")
  @Email(message = "请输入有效邮箱")
  private String email;
  @NotBlank(message = "密码不能为空")
  private String password;

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

  @Override
  public String toString() {
    return "LoginDto{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
