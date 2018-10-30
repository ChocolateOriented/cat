package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_mobile_address")
public class MobileAddress {

	@Id
	@GeneratedValue
	private Long id;

	private String pre3;

	private String pre7;

	private String province;

	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPre3() {
		return pre3;
	}

	public void setPre3(String pre3) {
		this.pre3 = pre3;
	}

	public String getPre7() {
		return pre7;
	}

	public void setPre7(String pre7) {
		this.pre7 = pre7;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
