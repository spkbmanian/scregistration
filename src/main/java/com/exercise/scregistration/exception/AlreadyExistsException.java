package com.exercise.scregistration.exception;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MESSAGE = "Unable to perform : %s operation for entity : %s with recordId : %s error : %s ";
	
	private  String operationName; 
	private String entityName; 
	private int recordId; 
	private String value;
	
	public AlreadyExistsException(String operationName, String entityName, int recordId, String value){
		super(String.format(ERROR_MESSAGE, operationName, entityName, recordId, value));
		this.entityName = entityName;
		this.operationName = operationName;
		this.recordId = recordId;
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return String.format(ERROR_MESSAGE, operationName, entityName, recordId, value);
	}
}
