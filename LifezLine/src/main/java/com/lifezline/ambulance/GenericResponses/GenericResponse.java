package com.lifezline.ambulance.GenericResponses;

public class GenericResponse<T> {

	private String code;
	private String description;
	// private String OTAC;
	private T data;

	public GenericResponse() {
		this.data = null;
		this.code = "";
		this.description = "";
		// this.OTAC="";
	}

	public GenericResponse(T data) {
		this.data = data;
		this.code = "00";
		this.description = "OK";
		// this.OTAC="424258";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}