package com.exercise.scregistration.dao.query.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exercise.scregistration.dao.query.QueryService;
import com.exercise.scregistration.db.model.Student;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.validator.EntityType;

public class StudentQueryImpl implements QueryService<Student>{
	
	private static final Logger LOG = Logger.getLogger(StudentQueryImpl.class.getName());
	
	private static final String FIRST_NAME = "FIRST_NAME";
	private static final String LAST_NAME = "LAST_NAME";
	private static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
	private static final String MOBILE_NO = "MOBILE_NO";
	private static final String STUDENT_ID = "STUDENT_ID";
	
	private static final String CREATE_QUERY = "INSERT INTO STUDENT (FIRST_NAME,LAST_NAME,DATE_OF_BIRTH, MOBILE_NO) VALUES (?,?,?,?)";
	
	private static final String UPDATE_QUERY = "UPDATE STUDENT set FIRST_NAME=? , LAST_NAME=?, DATE_OF_BIRTH=?, MOBILE_NO=?  where STUDENT_ID=?";
	
	private static final String DELETE_QUERY = "DELETE FROM STUDENT where STUDENT_ID = ?";
	
	private static final String SELETE_BY_ID_QUERY = "SELECT * FROM STUDENT where STUDENT_ID = ?";
	
	private static final String SELETE_PAGINATED_QUERY = "SELECT * FROM STUDENT limit ? offset ?";
	
	private static final String RECORDS_COUNT_QUERY = "SELECT count(*) as totalCount FROM STUDENT ";

	
	@Override
	public int create(Connection connection, Student obj) {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);			
			statement =  connection.prepareStatement(CREATE_QUERY);
			statement.setString(1, obj.getFirstName());
			statement.setString(2, obj.getLastName());
			statement.setDate(3,  Date.valueOf(obj.getDateOfBirth() ));
			statement.setString(4, obj.getContactNumber());
			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return result;
	}

	@Override
	public int update(Connection connection, Student obj) {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);
			statement =  connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, obj.getFirstName());
			statement.setString(2, obj.getLastName());
			statement.setDate(3,  Date.valueOf(obj.getDateOfBirth() ));
			statement.setString(4, obj.getContactNumber());
			statement.setInt(5, obj.getStudentId());
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
	public Student byId(Connection connection, int recordId) throws NotFoundException{
		Student student= null;
		PreparedStatement statement = null;
		try {
			statement =  connection.prepareStatement(SELETE_BY_ID_QUERY);
			statement.setInt(1, recordId);
			ResultSet result =  statement.executeQuery();
			while(result.next()){
				student = new Student();
				student.setFirstName(result.getString(FIRST_NAME));
				student.setLastName(result.getString(LAST_NAME));
				student.setContactNumber(result.getString(MOBILE_NO));
				student.setDateOfBirth(result.getDate(DATE_OF_BIRTH).toLocalDate());
				student.setStudentId(result.getInt(STUDENT_ID));
				break;
			}
			if(student==null)
				throw new NotFoundException(EntityType.Student.toString(), recordId);				
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return student;
	}
	
	@Override
	public PaginatedResults<Student> fetchPaginatedRecords(Connection connection, int limit, int pageNumber){
		PreparedStatement statement = null;
		PaginatedResults<Student> results= null;
		try {
			int offset = (pageNumber-1)*limit;
			statement =  connection.prepareStatement(SELETE_PAGINATED_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet result =  statement.executeQuery();
			List<Student> students = new ArrayList<>();
			while(result.next()){
				Student student = new Student();
				student.setFirstName(result.getString(FIRST_NAME));
				student.setLastName(result.getString(LAST_NAME));
				student.setContactNumber(result.getString(MOBILE_NO));
				student.setDateOfBirth(result.getDate(DATE_OF_BIRTH).toLocalDate());
				student.setStudentId(result.getInt(STUDENT_ID));
				students.add(student);
			}
			statement = connection.prepareStatement(RECORDS_COUNT_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			ResultSet rs = statement.executeQuery();
		    rs.next();
		    results = new PaginatedResults<>(rs.getInt("totalCount"), students);
		} catch (SQLException e) {
			LOG.severe(e.getMessage());
			throw new DatabaseException(e.getMessage() ); 
		}
		return results;
	}

	@Override
	public PaginatedResults<Student> getAll(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

}
