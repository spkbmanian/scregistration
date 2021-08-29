package com.exercise.scregistration.dao.query.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.exercise.scregistration.dao.query.QueryService;
import com.exercise.scregistration.db.model.StudentCourse;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;

public class StudentCourseQueryImpl implements QueryService<StudentCourse>{

	private String COURSE_ID = "COURSE_ID";
	private String STUDENT_ID = "STUDENT_ID";
	
	
	private static final String CREATE_QUERY = "INSERT INTO STUDENT_COURSE (COURSE_ID,STUDENT_ID) VALUES (?,?)";
	
	private static final String SELETE_PAGINATED_QUERY = "SELECT student.student_id as student_id, concat(student.FIRST_NAME,student.LAST_NAME) as student_name, "
			+ "course.course_id as course_id, course.NAME as course_name FROM Student student CROSS JOIN Course course "
			+ "where "
			+ "student.student_id in (select student_id from STUDENT_COURSE) "
			+ " and course.course_id in (select course_id from STUDENT_COURSE) limit ? offset ?";
	
	private static final String RECORDS_COUNT_QUERY = "SELECT count(*) as totalCount FROM STUDENT_COURSE ";
	
	private static final String GET_ALL_QUERY = "select student_id, student_name,course_id,course_name from ( "
			+ " select student.student_id as student_id, concat(student.first_name, ' ', student.last_name) as student_name, course.course_id as course_id,course.name as course_name "
			+ " from student student, course course "
			+ " ) all_student_course";

	@Override
	public int create(Connection connection, StudentCourse obj) {
		PreparedStatement statement = null;
		int result = 0;
		try {
			connection.setAutoCommit(false);
			statement =  connection.prepareStatement(CREATE_QUERY);
			statement.setInt(1, obj.getCourseId());
			statement.setInt(2, obj.getStudentId());
			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage() ); 
		}		
		return result;
	}

	@Override
	public int update(Connection connection, StudentCourse obj) {
		return  1; 
	}

	@Override
	public int delete(Connection connection, int recordId) throws NotFoundException {
		return 1;
	}

	@Override
	public StudentCourse byId(Connection connection, int recordId) throws NotFoundException {
		return null;
	}

	@Override
	public PaginatedResults<StudentCourse> fetchPaginatedRecords(Connection connection, int limit, int pageNumber){
		PreparedStatement statement = null;
		PaginatedResults<StudentCourse> results= null;
		try {
			int offset = (limit * pageNumber) - limit;
			statement =  connection.prepareStatement(SELETE_PAGINATED_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet result =  statement.executeQuery();
			List<StudentCourse> studentCourses = new ArrayList<>();
			while(result.next()){
				StudentCourse item = new StudentCourse();
				item.setCourseId(result.getInt(COURSE_ID));
				item.setCourseName(result.getString("COURSE_NAME"));
				item.setStudentId(result.getInt(STUDENT_ID));
				item.setStudentName(result.getString("STUDENT_NAME"));
				studentCourses.add(item);
			}
			statement = connection.prepareStatement(RECORDS_COUNT_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			ResultSet rs = statement.executeQuery();
		    rs.next();
		    results = new PaginatedResults<>(rs.getInt("totalCount"), studentCourses);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage() ); 
		}
		return results;
	}

	@Override
	public PaginatedResults<StudentCourse> getAll(Connection connection) {
		PreparedStatement statement = null;
		PaginatedResults<StudentCourse> results= null;
		try {
			statement =  connection.prepareStatement(GET_ALL_QUERY,  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
			ResultSet result =  statement.executeQuery();
			List<StudentCourse> studentCourses = new ArrayList<>();
			int recordCount = 0;
			while(result.next()){
				StudentCourse item = new StudentCourse();
				item.setCourseId(result.getInt(COURSE_ID));
				item.setCourseName(result.getString("COURSE_NAME"));
				item.setStudentId(result.getInt(STUDENT_ID));
				item.setStudentName(result.getString("STUDENT_NAME"));
				studentCourses.add(item);
				recordCount++;
			}
		    results = new PaginatedResults<>(recordCount, studentCourses);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage() ); 
		}
		return results;
	}

}
