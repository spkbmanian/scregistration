package com.exercise.scregistration.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.exercise.scregistration.dao.query.QueryService;
import com.exercise.scregistration.dao.query.impl.CourseQueryImpl;
import com.exercise.scregistration.dao.query.impl.StudentCourseQueryImpl;
import com.exercise.scregistration.dao.query.impl.StudentQueryImpl;
import com.exercise.scregistration.db.connection.ConcurrentBlockingConnectionPool;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.validator.EntityType;

public abstract class DAOProcessor<T> {
	
	@SuppressWarnings("rawtypes")
	private static final Map<EntityType, QueryService> queryServices= new HashMap<>();

	static{
		queryServices.put(EntityType.Course, new CourseQueryImpl());
		queryServices.put(EntityType.Student, new StudentQueryImpl());
		queryServices.put(EntityType.StudentCourse, new StudentCourseQueryImpl());
	}
	
	private static ConcurrentBlockingConnectionPool connectionPool = ConcurrentBlockingConnectionPool.getSingleton();
	
	@SuppressWarnings("rawtypes")
	protected QueryService getQueryService(EntityType entityType){
		return queryServices.get(entityType);
	}
	
	protected static Connection getConnection(){
		try {
			return connectionPool.getConnection();
		} catch (SQLException | InterruptedException e) {
			throw new DatabaseException(e.getMessage() ); 
		}
	}
	
	protected static Connection closeConnection(Connection connection){
		connectionPool.releaseConnection(connection);
		return null;
	}
	
	protected abstract int create(EntityType entityType, T obj);
	
	protected abstract int update(EntityType entityType, T obj);
	
	protected abstract int delete(EntityType entityType, int recordId)throws NotFoundException;
	
	protected abstract T getById(EntityType entityType, int recordId)throws NotFoundException;
	
	protected abstract PaginatedResults<T> fetchPaginatedRecords(EntityType entityType, int limit, int pageNumber);
}
