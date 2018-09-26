package com.cat.module.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/9/25.
 */
public class EmailDto {
  @NotBlank
  @Email(message = "请输入有效邮箱")
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
