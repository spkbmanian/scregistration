package com.exercise.scregistration.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.exercise.scregistration.db.model.Student;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.ValidatorService;

class StudentValidatorImpl implements ValidatorService<Student>{

	@Override
	public boolean validate(OperationType operationType, Student valueObj) throws ValidationException {
		List<String> emptyMandatoryFields = new ArrayList<>();
		if( valueObj.getFirstName()== null || valueObj.getFirstName().trim().length()==0  ){
			emptyMandatoryFields.add("First Name");
		}
		if( valueObj.getLastName()== null || valueObj.getLastName().trim().length()==0  ){
			emptyMandatoryFields.add("Last Name");
		}
		
		if( valueObj.getDateOfBirth()==null ){
			emptyMandatoryFields.add("Date of Birth");
		}
		
		if( valueObj.getContactNumber()==null || valueObj.getContactNumber().trim().length()<10 ){
			emptyMandatoryFields.add("Contact Number");
		}
		
		if(OperationType.UPDATE.equals(operationType) ){
			if( valueObj.getStudentId()==0 ){
				emptyMandatoryFields.add("Student Id");
			}			
		}
		
		if(emptyMandatoryFields.size() >0){
			throw new ValidationException(EntityType.Student.toString(), operationType.toString() ,emptyMandatoryFields ); 
		}
		return true;
	}

}
