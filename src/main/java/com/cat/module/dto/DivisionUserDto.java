package com.cat.module.dto;

import com.cat.module.enums.UserStatus;
import javax.management.relation.Role;

public interface DivisionUserDto {


  Long getId();
  Long getOrganizationId();
  UserStatus getStatus();
  String getName();
  String getCollectCycle();
  Role getRole();
  Boolean getAutoDivision();
  String getSumCorpusAmount();

}
