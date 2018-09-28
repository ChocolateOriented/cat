package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_organization")
public class Organization extends BaseEntity {

  private String name;
  private String leaderId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(String leaderId) {
    this.leaderId = leaderId;
  }
}
