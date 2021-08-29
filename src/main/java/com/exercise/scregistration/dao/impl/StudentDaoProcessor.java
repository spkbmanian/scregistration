package com.exercise.scregistration.dao.impl;

import java.sql.Connection;

import com.exercise.scregistration.dao.DAOProcessor;
import com.exercise.scregistration.db.model.Student;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.validator.EntityType;

public class StudentDaoProcessor extends DAOProcessor<Student> {
	
	@SuppressWarnings("unchecked")
	@Override
	public int create(EntityType entityType, Student obj){
		Connection connection = getConnection();
		try{
			return getQueryService(entityType).create(connection, obj);
		}finally{
			closeConnection(connection);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int update(EntityType entityType, Student obj){
		Connection connection = getConnection();
		try{
			return getQueryService(entityType).update(connection, obj);
		}finally{
			closeConnection(connection);
		}
	}
	
	@Override
	public int delete(EntityType entityType, int recordId) throws NotFoundException{
		Connection connection = getConnection();
		try{
			return getQueryService(entityType).delete(connection, recordId);
		}finally{
			closeConnection(connection);
		}
	}
	
	@Override
	public Student getById(EntityType entityType, int recordId) throws NotFoundException{
		Connection connection = getConnection();
		try{
			return (Student) getQueryService(entityType).byId(connection, recordId);
		}finally{
			closeConnection(connection);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResults<Student> fetchPaginatedRecords(EntityType entityType, int limit, int pageNumber) {
		Connection connection = getConnection();
		try{
			return (PaginatedResults<Student>) getQueryService(entityType).fetchPaginatedRecords(connection, limit, pageNumber);
		}finally{
			closeConnection(connection);
		}
	}

}
