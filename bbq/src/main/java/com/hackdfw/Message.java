package com.hackdfw;

public class Message {

	private String message;
	private boolean error;
	
	public Message(String message, boolean error) {
		super();
		this.message = message;
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	
}
