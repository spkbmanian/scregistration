package com.exercise.scregistration.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exercise.scregistration.db.model.StudentCourse;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.service.impl.StudentCourseServiceImpl;

@WebServlet("/studentCourseService")
public class StudentCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private CRUDService<StudentCourse> service = new StudentCourseServiceImpl();
	

	protected void doGet(HttpServletRequest request, 
                                       HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("limit")!=null && request.getParameter("pageNumber")!=null){
			int limit = Integer.valueOf(request.getParameter("limit"));
			int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			pagenatedResults(request, response, limit, pageNumber);
		}else {
			try {
				PaginatedResults<StudentCourse> results =  service.getAll();
				request.setAttribute("students", results.getResults());
				request.setAttribute("totalCount", results.getTotalCount());
				request.setAttribute("selectedStudentId", 0);
				RequestDispatcher rd =  request.getRequestDispatcher("student_course/student-course-create.jsp");
				rd.forward(request, response);
			}catch (DatabaseException e) {
				request.setAttribute("errorMessage", e.getMessage());
			    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
			    rd.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
				RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
				rd.forward(request, response);
			}
		}
	}

	private void pagenatedResults(HttpServletRequest request, HttpServletResponse response, int limit, int pageNumber)
			throws ServletException, IOException {
		try {
			PaginatedResults<StudentCourse> results = service.fetchPaginatedRecords(limit, pageNumber);
			request.setAttribute("studentcourses", results.getResults());
			request.setAttribute("totalCount", results.getTotalCount());
			RequestDispatcher rd =  request.getRequestDispatcher("student_course/student-course-list.jsp");
			rd.forward(request, response);
		}catch (DatabaseException e) {
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
		try{
			StudentCourse item  = new StudentCourse();
			item.setCourseId(Integer.valueOf(request.getParameter("courseId")));
			item.setStudentId(Integer.valueOf(request.getParameter("studentId")));

			service.create(item);
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
	
	protected void doDelete(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

	}
	
	protected void doPut(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

	}

}
