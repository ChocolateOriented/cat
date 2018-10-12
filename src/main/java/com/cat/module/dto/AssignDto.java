package com.cat.module.dto;

import java.util.List;

public class AssignDto{
	private List<String> collectIds; //催收员id集合
	private List<String > orderIds;//订单id集合
	private String collectCycle; //案件所属队列 
	private String productType; //产品类型 
	public List<String> getCollectIds() {
		return collectIds;
	}
	public void setCollectIds(List<String> collectIds) {
		this.collectIds = collectIds;
	}
	public List<String> getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
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
