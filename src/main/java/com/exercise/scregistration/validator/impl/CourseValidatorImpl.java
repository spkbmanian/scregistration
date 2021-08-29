package com.exercise.scregistration.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.exercise.scregistration.db.model.Course;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.ValidatorService;

class CourseValidatorImpl implements ValidatorService<Course>{

	@Override
	public boolean validate(OperationType operationType, Course valueObj) throws ValidationException {
		List<String> emptyMandatoryFields = new ArrayList<>();
		if( valueObj.getCourseName()== null || valueObj.getCourseName().trim().length()==0  ){
			emptyMandatoryFields.add("Course Name");
		}
		
		if(OperationType.UPDATE.equals(operationType) ){
			if( valueObj.getCourseId()==0 ){
				emptyMandatoryFields.add("Course Id");
			}			
		}
		
		
		if(emptyMandatoryFields.size() >0){
			throw new ValidationException(EntityType.Course.toString(), operationType.toString() ,emptyMandatoryFields ); 
		}
		return true;	
		
	}

}
