package com.exercise.scregistration.dao.query.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exercise.scregistration.dao.query.QueryService;
import com.exercise.scregistration.db.model.Course;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.validator.EntityType;

public class CourseQueryImpl implements QueryService<Course>{
	
	private static final Logger LOG = Logger.getLogger(CourseQueryImpl.class.getName());

	private String COURSE_ID = "COURSE_ID";
	private String COURSE_NAME = "NAME";
	private String DETAIL_INFO = "DETAIL_INFO";
	
	private static final String CREATE_QUERY = "INSERT INTO COURSE (NAME,DETAIL_INFO) VALUES (?,?)";
	
	private static final String UPDATE_QUERY = "UPDATE COURSE set NAME=? , DETAIL_INFO=? where COURSE_ID=?";
	
	private static final String DELETE_QUERY = "DELETE FROM COURSE where COURSE_ID = ?";
	
	private static final String SELETE_BY_ID_QUERY = "SELECT * FROM COURSE where COURSE_ID = ?";
	
	private static final String SELETE_PAGINATED_QUERY = "SELECT * FROM COURSE limit ? offset ?";
	
	private static final String RECORDS_COUNT_QUERY = "SELECT count(*) as totalCount FROM COURSE ";

	@Override
	public int create(Connection connection, Course obj) {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);
			statement =  connection.prepareStatement(CREATE_QUERY);
			statement.setString(1, obj.getCourseName());
			statement.setString(2, obj.getCourseDetail());
			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}		
		return result;
	}

	@Override
	public int update(Connection connection, Course obj) {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);
			statement =  connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, obj.getCourseName());
			statement.setString(2, obj.getCourseDetail());
			statement.setInt(3, obj.getCourseId());
			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return result;
	}

	@Override
	public int delete(Connection connection, int recordId) throws NotFoundException {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);
			statement =  connection.prepareStatement(DELETE_QUERY);
			statement.setInt(1, recordId);
			result = statement.executeUpdate();
			connection.commit();
			if(result==0)
				throw new NotFoundException(EntityType.Student.toString(), recordId);
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return result;
	}

	@Override
	public Course byId(Connection connection, int recordId) throws NotFoundException {
		PreparedStatement statement = null;
		Course course = null;
		try {
			statement =  connection.prepareStatement(SELETE_BY_ID_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			statement.setInt(1, recordId);
			ResultSet result =  statement.executeQuery();
			while(result.next()){
				course = new Course();
				course.setCourseId(result.getInt(COURSE_ID));
				course.setCourseName(result.getString(COURSE_NAME));
				course.setCourseDetail( result.getString(DETAIL_INFO) );
				break;
			}
			if(course==null)
				throw new NotFoundException(EntityType.Student.toString(), recordId);
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return course;
	}

	@Override
	public PaginatedResults<Course> fetchPaginatedRecords(Connection connection, int limit, int pageNumber){
		PreparedStatement statement = null;
		PaginatedResults<Course> results= null;
		try {
			int offset = (pageNumber-1)*limit;
			statement =  connection.prepareStatement(SELETE_PAGINATED_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet result =  statement.executeQuery();
			List<Course> courses = new ArrayList<>();
			while(result.next()){
				Course course = new Course();
				course.setCourseId(result.getInt(COURSE_ID));
				course.setCourseName(result.getString(COURSE_NAME));
				course.setCourseDetail( result.getString(DETAIL_INFO) );
				courses.add(course);
			}
			statement = connection.prepareStatement(RECORDS_COUNT_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			ResultSet rs = statement.executeQuery();
		    rs.next();
		    results = new PaginatedResults<>(rs.getInt("totalCount"), courses);
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return results;
	}

	@Override
	public PaginatedResults<Course> getAll(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

}
