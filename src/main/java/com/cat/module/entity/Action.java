package com.cat.module.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cat.module.enums.ActionCode;

@Entity
@Table(name = "t_cat_action")
public class Action extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String orderId;

    private String ownerId;

    private String dunnerId;

    private String dunnerName;

    private String targetTel;

    private String targetName;

    private Integer targetType;

    @Enumerated(EnumType.STRING)
    private ActionCode actionCode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDunnerId() {
        return dunnerId;
    }

    public void setDunnerId(String dunnerId) {
        this.dunnerId = dunnerId;
    }

    public String getDunnerName() {
		return dunnerName;
	}

	public void setDunnerName(String dunnerName) {
		this.dunnerName = dunnerName;
	}

	public String getTargetTel() {
        return targetTel;
    }

    public void setTargetTel(String targetTel) {
        this.targetTel = targetTel;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public ActionCode getActionCode() {
        return actionCode;
    }

    public void setActionCode(ActionCode actionCode) {
        this.actionCode = actionCode;
    }

}