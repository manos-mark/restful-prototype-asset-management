package com.manos.prototype.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 4672621249774678197L;

	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(String message, Throwable t) {
		super(message, t);
	}
}
