package com.exercise.scregistration.validator.impl;

import java.util.HashMap;
import java.util.Map;

import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.ValidatorService;

public class ValidationProcessor {
	
	@SuppressWarnings("rawtypes")
	private static final Map<EntityType, ValidatorService> registeredValidators = new HashMap<>();
	
	
	static{
		registeredValidators.put(EntityType.Student, new StudentValidatorImpl());
		registeredValidators.put(EntityType.Course, new CourseValidatorImpl());
		registeredValidators.put(EntityType.StudentCourse, new StudentCourseValidatorImpl());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ValidatorService<T> getValidator(EntityType entityType){
		return registeredValidators.get(entityType);
	}

}
