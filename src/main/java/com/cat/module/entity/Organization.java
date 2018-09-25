package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_organization")
public class Organization extends BaseEntity {

  private String name;
  private Long leaderId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(Long leaderId) {
    this.leaderId = leaderId;
  }

}
