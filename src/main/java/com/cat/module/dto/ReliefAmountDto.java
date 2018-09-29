package com.cat.module.dto;

import java.math.BigDecimal;


public class ReliefAmountDto {
	/** 优惠金额 */
    private BigDecimal number;

    /** 绑定借贷订单ID */
    private String bundleId;

    /** 创建者 */
    private String creator;

    /** 优惠原因 */
    private String reason;

    /** 签名 */
    private String sign;

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	public ReliefAmountDto(BigDecimal number, String bundleId, String creator, String reason) {
		super();
		this.number = number;
		this.bundleId = bundleId;
		this.creator = creator;
		this.reason = reason;
	}

	public ReliefAmountDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
