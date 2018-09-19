package com.cat.module.entity;


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
