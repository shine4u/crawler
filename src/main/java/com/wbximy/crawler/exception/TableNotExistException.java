package com.wbximy.crawler.exception;

public class TableNotExistException extends Exception {
	
	private static final long serialVersionUID = 7691966271173352945L;
	
	private String message;
	
	public TableNotExistException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
