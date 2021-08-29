package com.exercise.scregistration.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.exercise.scregistration.db.model.StudentCourse;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.ValidatorService;

class StudentCourseValidatorImpl implements ValidatorService<StudentCourse>{

	@Override
	public boolean validate(OperationType operationType, StudentCourse valueObj) throws ValidationException {
		List<String> emptyMandatoryFields = new ArrayList<>();
		if( valueObj.getCourseId() == 0  ){
			emptyMandatoryFields.add("Course Name");
		}
		
		if( valueObj.getStudentId()== 0 ){
			emptyMandatoryFields.add("Student Name");
		}
		
		if(emptyMandatoryFields.size() >0){
			throw new ValidationException(EntityType.Course.toString(), operationType.toString() ,emptyMandatoryFields ); 
		}
		return true;	
		
	}

}
