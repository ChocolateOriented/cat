package com.cat.module.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jxli on 2018/9/28.
 */
public class BlackListDto {
  @NotBlank
  private String customerId;
  private String reason;

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return "BlackListDto{" +
        "customerId='" + customerId + '\'' +
        ", reason='" + reason + '\'' +
        '}';
  }
}
