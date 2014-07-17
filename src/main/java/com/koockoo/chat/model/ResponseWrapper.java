package com.koockoo.chat.model; 

public class ResponseWrapper<T> {
	
	private String type ="event";
	private String name = "message";
	private int code = 200;
	private String message;
	private boolean success;
	private T data;

	public ResponseWrapper() {
		this(null);
	}
	
	public ResponseWrapper(T data) {
		this(data, ResponseCode.OK, "");
	}

	public ResponseWrapper(ResponseCode code, String message) {
		this(null, code, message);
	}
	
	public ResponseWrapper(T object, ResponseCode code, String message) {
		this.data = object;
		this.success = code.isSuccess();
		this.setCode(code.getCode());
		this.message = message;
	}

	public T getData() {
		return data;
	}
	public void setData(T object) {
		this.data = object;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
