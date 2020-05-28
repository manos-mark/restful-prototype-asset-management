package com.manos.prototype.exception;

import java.io.Serializable;

public class EntityNotFoundException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Class<?> entityType, Serializable id) {
		super("Could not find entity of type " + entityType.getSimpleName() + " with id=" + id);
	}
	
	public EntityNotFoundException(Class<?> entityType) {
		super("Could not find entity of type " + entityType.getSimpleName());
	}
}
