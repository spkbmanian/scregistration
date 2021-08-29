package com.exercise.scregistration.dao.query;

import java.sql.Connection;

import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.NotFoundException;

public interface QueryService<T> {

	public  int create(Connection connection, T objg);
	
	public int update(Connection connection, T objg);
	
	public int delete(Connection connection,int recordId)throws NotFoundException;
	
	public T byId(Connection connection,int recordId)throws NotFoundException;
	
	public PaginatedResults<T> fetchPaginatedRecords(Connection connection,int limit, int offset);
	
	public PaginatedResults<T> getAll(Connection connection);
}
