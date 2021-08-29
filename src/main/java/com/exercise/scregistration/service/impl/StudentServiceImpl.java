package com.exercise.scregistration.service.impl;

import com.exercise.scregistration.dao.impl.StudentDaoProcessor;
import com.exercise.scregistration.db.model.Student;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.validator.EntityType;
import com.exercise.scregistration.validator.OperationType;
import com.exercise.scregistration.validator.impl.ValidationProcessor;

public class StudentServiceImpl implements CRUDService<Student>{
	
	private final StudentDaoProcessor daoProcessor = new StudentDaoProcessor();
	
	@Override
	public int create(Student entity) throws AlreadyExistsException, ValidationException {
		ValidationProcessor.getValidator(EntityType.Student).validate(OperationType.CREATE, entity);
		return daoProcessor.create(EntityType.Student, entity);
	}

	@Override
	public int update(Student entity) throws AlreadyExistsException, ValidationException {
		ValidationProcessor.getValidator(EntityType.Student).validate(OperationType.UPDATE, entity);
		return daoProcessor.update(EntityType.Student, entity);
	}

	@Override
	public Student getById(int recordId) throws NotFoundException {
		return daoProcessor.getById(EntityType.Student, recordId);
	}

	@Override
	public int deleteById(int recordId) throws NotFoundException {
		return daoProcessor.delete(EntityType.Student, recordId);
	}
	
	@Override
	public PaginatedResults<Student> fetchPaginatedRecords( int limit, int pageNumber){
		return daoProcessor.fetchPaginatedRecords(EntityType.Student, limit,pageNumber);
	}

	@Override
	public PaginatedResults<Student> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
