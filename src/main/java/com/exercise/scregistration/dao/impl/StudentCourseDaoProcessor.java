package com.exercise.scregistration.dao.impl;

import java.sql.Connection;

import com.exercise.scregistration.dao.DAOProcessor;
import com.exercise.scregistration.db.model.StudentCourse;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.validator.EntityType;

public class StudentCourseDaoProcessor extends DAOProcessor<StudentCourse> {
	
	@SuppressWarnings("unchecked")
	@Override
	public int create(EntityType entityType, StudentCourse obj){
		Connection connection = getConnection();
		try{
			return getQueryService(entityType).create(connection, obj);
		}finally{
			closeConnection(connection);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int update(EntityType entityType, StudentCourse obj){
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
	public StudentCourse getById(EntityType entityType, int recordId) throws NotFoundException{
		Connection connection = getConnection();
		try{
			return (StudentCourse) getQueryService(entityType).byId(connection, recordId);
		}finally{
			closeConnection(connection);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResults<StudentCourse> fetchPaginatedRecords(EntityType entityType, int limit, int pageNumber) {
		Connection connection = getConnection();
		try{
			return (PaginatedResults<StudentCourse>) getQueryService(entityType).fetchPaginatedRecords(connection, limit, pageNumber);
		}finally{
			closeConnection(connection);
		}
	}
	
	@SuppressWarnings("unchecked")
	public PaginatedResults<StudentCourse> getAll(EntityType entityType) {
		Connection connection = getConnection();
		try{
			return (PaginatedResults<StudentCourse>) getQueryService(entityType).getAll(connection);
		}finally{
			closeConnection(connection);
		}
	}

}
