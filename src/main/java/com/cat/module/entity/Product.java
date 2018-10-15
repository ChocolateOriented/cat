package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by jxli on 2018/10/15.
 */
@Entity
public class Product extends AuditingEntity{
  @Id
  private String code;
  private String name;
  private String divisionStrategy;//分案策略

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDivisionStrategy() {
    return divisionStrategy;
  }

  public void setDivisionStrategy(String divisionStrategy) {
    this.divisionStrategy = divisionStrategy;
  }
}
