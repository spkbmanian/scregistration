package com.exercise.scregistration.web;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exercise.scregistration.db.model.Student;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.service.impl.StudentServiceImpl;

@WebServlet("/studentservice")
public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private CRUDService<Student> studentService = new StudentServiceImpl();
	

	protected void doGet(HttpServletRequest request, 
                                       HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentId")!=null){
			int studentId = Integer.valueOf(request.getParameter("studentId"));
			getById(request, response, studentId);
		}else if(request.getParameter("limit")!=null && request.getParameter("pageNumber")!=null){
			int limit = Integer.valueOf(request.getParameter("limit"));
			int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			pagenatedResults(request, response, limit, pageNumber);
		}
	}

	private void getById(HttpServletRequest request, HttpServletResponse response, int studentId)
			throws ServletException, IOException {
		try {
			Student student = studentService.getById(studentId);
			request.setAttribute("student", student);
			RequestDispatcher rd =  request.getRequestDispatcher("student/student-edit.jsp");
			rd.forward(request, response);
		} catch (NotFoundException e) {
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}catch (DatabaseException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
	}

	private void pagenatedResults(HttpServletRequest request, HttpServletResponse response, int limit, int pageNumber)
			throws ServletException, IOException {
		try {
			PaginatedResults<Student> results = studentService.fetchPaginatedRecords(limit, pageNumber);
			request.setAttribute("students", results.getResults());
			request.setAttribute("totalCount", results.getTotalCount());
			RequestDispatcher rd =  request.getRequestDispatcher("student/student-list.jsp");
			rd.forward(request, response);
		} catch (DatabaseException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
                                       HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("studentId")!=null){
			if(request.getParameter("operation")!=null){
				if(((String)request.getParameter("operation")).equalsIgnoreCase("put")){
					doPut(request, response);
				}else{
					doDelete(request, response);
				}
			}
		}else{
			try{
				Student student  = new Student();
				student.setFirstName(request.getParameter("firstName"));
				student.setLastName(request.getParameter("lastName"));
				student.setDateOfBirth(LocalDate.parse( request.getParameter("dob")));
				student.setContactNumber(request.getParameter("contactNumber"));
				studentService.create(student);
				pagenatedResults(request, response, 10, 1);
			} catch (ValidationException e) {
				request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}catch (AlreadyExistsException e) {
				request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}catch (DatabaseException e) {
				request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}
		}
	}
	
	protected void doDelete(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		int studentId = Integer.valueOf(request.getParameter("studentId"));
		try{
			studentService.deleteById(studentId);
			pagenatedResults(request, response, 10, 1);
		}catch (NotFoundException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}catch (DatabaseException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		int studentId = Integer.valueOf(request.getParameter("studentId"));
		try{
			Student student = studentService.getById(studentId);
			student.setStudentId(Integer.valueOf(request.getParameter("studentId")));
			student.setFirstName(request.getParameter("firstName"));
			student.setLastName(request.getParameter("lastName"));
			student.setDateOfBirth(LocalDate.parse( request.getParameter("dob")));
			student.setContactNumber(request.getParameter("contactNumber"));
			studentService.update(student);
			pagenatedResults(request, response, 10, 1);
		} catch (NotFoundException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		} catch (ValidationException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}catch (AlreadyExistsException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}catch (DatabaseException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}

	}

}
