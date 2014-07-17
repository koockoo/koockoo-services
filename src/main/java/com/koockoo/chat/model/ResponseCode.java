package com.koockoo.chat.model;

public enum ResponseCode {
	OK(200, "Success", true),
	BAD_REQUEST(400, "Invalid arguments or missing values", false),
	UNAUTHORIZED(401, "Must be authorized to run this action", false),
	INVALID_CREDENTIALS(4011, "Invalid credentials provided", false),
	INTERNAL_ERROR(500, "Unexpected error", false);
	
	private int code = 200;
	private String description = "";
	private boolean success = false;
		
	private ResponseCode(int code, String name, boolean success) {
		this.code = code;
		this.description = name;
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return description;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setName(String name) {
		this.description = name;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
