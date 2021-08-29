package com.exercise.scregistration.service.impl;

import com.exercise.scregistration.dao.impl.CourseDAOProcessor;
import com.exercise.scregistration.db.model.Course;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.impl.ValidationProcessor;

public class CourseServiceImpl implements CRUDService<Course>{
	
	private final CourseDAOProcessor daoProcessor = new CourseDAOProcessor();
	
	@Override
	public int create(Course entity) throws AlreadyExistsException, ValidationException {
		ValidationProcessor.getValidator(EntityType.Course).validate(OperationType.CREATE, entity);
		return daoProcessor.create(EntityType.Course, entity);
	}

	@Override
	public int update(Course entity) throws AlreadyExistsException, ValidationException {
		ValidationProcessor.getValidator(EntityType.Course).validate(OperationType.UPDATE, entity);
		return daoProcessor.update(EntityType.Course, entity);
	}

	@Override
	public Course getById(int recordId) throws NotFoundException {
		return daoProcessor.getById(EntityType.Course, recordId);
	}

	@Override
	public int deleteById(int recordId) throws NotFoundException {
		return daoProcessor.delete(EntityType.Course, recordId);
	}

	@Override
	public PaginatedResults<Course> fetchPaginatedRecords( int limit, int pageNumber) {
		return daoProcessor.fetchPaginatedRecords(EntityType.Course, limit,pageNumber);
	}

	@Override
	public PaginatedResults<Course> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
