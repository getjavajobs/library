package com.getjavajobs.library.exceptions;

/**
 * Класс-исключение при работе с сервисными классами.
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
