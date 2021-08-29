package com.exercise.scregistration.service.impl;

import com.exercise.scregistration.dao.impl.StudentCourseDaoProcessor;
import com.exercise.scregistration.db.model.StudentCourse;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.impl.ValidationProcessor;

public class StudentCourseServiceImpl implements CRUDService<StudentCourse>{
	
	private final StudentCourseDaoProcessor daoProcessor = new StudentCourseDaoProcessor();
	
	@Override
	public int create(StudentCourse entity) throws AlreadyExistsException, ValidationException {
		ValidationProcessor.getValidator(EntityType.StudentCourse).validate(OperationType.CREATE, entity);
		return daoProcessor.create(EntityType.StudentCourse, entity);
	}

	@Override
	public int update(StudentCourse entity) throws AlreadyExistsException, ValidationException {
 		return 1; 
	}

	@Override
	public StudentCourse getById(int recordId) throws NotFoundException {
		return null;
	}

	@Override
	public int deleteById(int recordId) throws NotFoundException {
		return 1;
	}

	@Override
	public PaginatedResults<StudentCourse> fetchPaginatedRecords( int limit, int pageNumber) {
		return daoProcessor.fetchPaginatedRecords(EntityType.StudentCourse, limit,pageNumber);
	}
	
	@Override
	public PaginatedResults<StudentCourse> getAll( ) {
		return daoProcessor.getAll(EntityType.StudentCourse);
	}

}
