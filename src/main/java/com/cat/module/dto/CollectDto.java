package com.cat.module.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class CollectDto implements Serializable{
  
	private static final long serialVersionUID = 1L;
	
	private String collectorName;//催收人
	private String collectorId;//催收员id
	//------------请求参数----------
	private String collectCycle; //案件所属队列 
	private String productType; //案件所属队列 
	private List<String > organization;//机构 
	private String isAutoAssign; //是否支持自动分案
	
	public String getCollectorName() {
		return collectorName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public List<String> getOrganization() {
		return organization;
	}
	public void setOrganization(List<String> organization) {
		this.organization = organization;
	}
	public String getIsAutoAssign() {
		return isAutoAssign;
	}
	public void setIsAutoAssign(String isAutoAssign) {
		this.isAutoAssign = isAutoAssign;
	}
	public String getCollectCycle() {
		return collectCycle;
	}
	public void setCollectCycle(String collectCycle) {
		this.collectCycle = collectCycle;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
