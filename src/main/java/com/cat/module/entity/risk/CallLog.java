package com.cat.module.entity.risk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_risk_call_log")
public class CallLog {

	@Id
	@GeneratedValue
	private Long id;

	private String mobile;

	private String callTel;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCallTel() {
		return callTel;
	}

	public void setCallTel(String callTel) {
		this.callTel = callTel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callTel == null) ? 0 : callTel.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
			
		}
		CallLog other = (CallLog) obj;
		
		if (callTel == null) {
			if (other.callTel != null) {
				return false;
			}
		} else if (!callTel.equals(other.callTel)) {
			return false;
		}
		
		if (mobile == null) {
			if (other.mobile != null) {
				return false;
			}
		} else if (!mobile.equals(other.mobile)) {
			return false;
		}
		return true;
	}

}
