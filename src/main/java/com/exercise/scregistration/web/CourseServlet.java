package com.exercise.scregistration.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exercise.scregistration.db.model.Course;
import com.exercise.scregistration.db.vo.PaginatedResults;
import com.exercise.scregistration.exception.AlreadyExistsException;
import com.exercise.scregistration.exception.DatabaseException;
import com.exercise.scregistration.exception.NotFoundException;
import com.exercise.scregistration.exception.ValidationException;
import com.exercise.scregistration.service.CRUDService;
import com.exercise.scregistration.service.impl.CourseServiceImpl;

@WebServlet("/courseservice")
public class CourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private CRUDService<Course> courseService = new CourseServiceImpl();
	

	protected void doGet(HttpServletRequest request, 
                                       HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("courseId")!=null){
			int courseId = Integer.valueOf(request.getParameter("courseId"));
			try {
				Course course = courseService.getById(courseId);
				request.setAttribute("course", course);
				RequestDispatcher rd =  request.getRequestDispatcher("course/course-edit.jsp");
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
		
		if(request.getParameter("limit")!=null && request.getParameter("pageNumber")!=null){
			int limit = Integer.valueOf(request.getParameter("limit"));
			int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			pagenatedResults(request, response, limit, pageNumber);
		}
	}

	private void pagenatedResults(HttpServletRequest request, HttpServletResponse response, int limit, int pageNumber)
			throws ServletException, IOException {
		try {
			PaginatedResults<Course> results = courseService.fetchPaginatedRecords(limit, pageNumber);
			request.setAttribute("courses", results.getResults());
			request.setAttribute("totalCount", results.getTotalCount());
			RequestDispatcher rd =  request.getRequestDispatcher("course/course-list.jsp");
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
		
		if(request.getParameter("courseId")!=null){
			if(request.getParameter("operation")!=null){
				if(((String)request.getParameter("operation")).equalsIgnoreCase("put")){
					doPut(request, response);
				}else{
					doDelete(request, response);
				}
			}
		}else{
			try{
				Course item  = new Course();
				item.setCourseName(request.getParameter("courseName"));
				item.setCourseDetail(request.getParameter("courseDetail"));
				courseService.create(item);
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
		int courseId = Integer.valueOf(request.getParameter("courseId"));
		try{
			courseService.deleteById(courseId);
			pagenatedResults(request, response, 10, 1);
		}catch (NotFoundException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		} catch (DatabaseException e) {
			request.setAttribute("errorMessage", e.getMessage());
		    RequestDispatcher rd =  request.getRequestDispatcher("error.jsp");
		    rd.forward(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		int courseId = Integer.valueOf(request.getParameter("courseId"));
		try{
			Course item = courseService.getById(courseId);
			item.setCourseId(Integer.valueOf(request.getParameter("courseId")));
			item.setCourseName(request.getParameter("courseName"));
			item.setCourseDetail(request.getParameter("courseDetail"));
			courseService.update(item);
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
