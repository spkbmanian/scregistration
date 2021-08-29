package com.exercise.scregistration.exception;

public class NotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MESSAGE = "Unable to find recordId : %s for entity %s";
	
	private String entityName; 
	private int recordId;
	
	public NotFoundException(String entityName, int recordId){
		super(String.format(ERROR_MESSAGE, entityName, recordId));
		this.entityName = entityName;
		this.recordId = recordId;
	}

	@Override
	public String toString() {
		return String.format(ERROR_MESSAGE, entityName, recordId);
	}


}
