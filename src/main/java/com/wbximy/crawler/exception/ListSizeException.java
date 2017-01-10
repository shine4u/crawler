package com.wbximy.crawler.exception;

public class ListSizeException extends Exception {

	private static final long serialVersionUID = -54190179608077122L;

	private String message;
	
	public ListSizeException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
