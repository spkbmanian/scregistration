package com.exercise.scregistration.service;

import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;

public interface CRUDService<T> {
	
	public int create(T entity) throws AlreadyExistsException, ValidationException;
	
	public int update(T entity) throws AlreadyExistsException, ValidationException;
	
	public T getById(int id) throws NotFoundException;
	
	public int deleteById(int id) throws NotFoundException;
	
	public PaginatedResults<T> fetchPaginatedRecords( int limit, int pageNumber);
	
	public PaginatedResults<T> getAll();
}
