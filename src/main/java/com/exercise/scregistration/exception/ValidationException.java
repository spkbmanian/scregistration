package com.exercise.scregistration.exception;

import java.util.List;

public class ValidationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MESSAGE = "Validation failed for operation %s on entity %s with fields %s should not be empty";
	
	private String entityName; 
	private String operation;
	private List<String> fields;
	
	public ValidationException(String entityName, String operation, List<String> fields){
		super(String.format(ERROR_MESSAGE, entityName, operation, fields));
		this.entityName = entityName;
		this.operation = operation;
		this.fields = fields;
	}

	@Override
	public String toString() {
		return String.format(ERROR_MESSAGE, entityName, operation, fields);
	}
}
