package com.exercise.scregistration.validator;

import com.exercise.scregistration.exception.ValidationException;

public interface ValidatorService<T> {
	
	public boolean validate(OperationType operationType, T valueObj) throws ValidationException; 

}
