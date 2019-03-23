package com.manos.prototype.exception;

public class EntityAlreadyExistException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistException(Class<?> entityType, String field) {
		super("Entity " + entityType.getSimpleName() + " already exist with field=" + field);
	}
	
	public EntityAlreadyExistException(Class<?> entityType) {
		super("Entity already exist " + entityType.getSimpleName());
	}
}
