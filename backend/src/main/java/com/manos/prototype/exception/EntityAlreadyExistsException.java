package com.manos.prototype.exception;

public class EntityAlreadyExistsException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistsException(Class<?> entityType, String field) {
		super(entityType.getSimpleName() + " already exists with field: " + field);
	}
	
	public EntityAlreadyExistsException(Class<?> entityType) {
		super("Entity already exists " + entityType.getSimpleName());
	}
}
