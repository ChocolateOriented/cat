package com.cat.module.dto;

import com.cat.module.enums.UserStatus;
import javax.management.relation.Role;

public interface DivisionUserDto {

  public Long getId();

  public Long getOrganizationId();

  public String getEmail();

  public String getName();

  public UserStatus getStatus();

  public String getCollectCycle();

  public Role getRole();

  public Boolean getAutoDivision();

  public String getSumCorpusAmount();

}
