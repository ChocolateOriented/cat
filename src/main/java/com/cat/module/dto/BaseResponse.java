package com.cat.module.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer code = 0;

	private String message = "ok";

	public BaseResponse() {
		super();
	}

	public BaseResponse(Integer code) {
		super();
		this.code = code;
		this.message = "";
	}

	public BaseResponse(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("error_code")
	public void setErrorCode(Integer code) {
		this.code = code;
	}

	@JsonProperty("error_msg")
	public void setErrorMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "[code=" + code + ", message=" + message + "]";
	}

	@JsonIgnore
	public boolean isSuccess() {
		return code != null && code == 0;
	}

	public static BaseResponse success() {
		return new BaseResponse();
	}

	public static BaseResponse fail() {
		return new BaseResponse(-1);
	}
}
