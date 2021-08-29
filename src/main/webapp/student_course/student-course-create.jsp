<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.exercise.scregistration.db.model.StudentCourse"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.exercise.scregistration.db.model.Student"%>
<%@ page errorPage="/error.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student List</title>
<style type="text/css">
.link {
	margin: 0;
	border: 0;
	background: none;
	overflow: visible;
	color: blue;
	cursor: pointer;
}
</style>
</head>
<body>
	<%
		if (request.getAttribute("errorMessage") != null) {
	%>
	<%=request.getAttribute("errorMessage")%>
	<%
		}
	%>
	<h1> Students Subscribe to Course</h1>
	<table border="1" width="500" style="text-align: center">
		<form action="studentCourseService" method="post">
			<tr>
				<td><b>Student Name</b></td>
				<td><select name="studentId">
						<%
							ArrayList<StudentCourse> students = (ArrayList<StudentCourse>) request.getAttribute("students");
							Set<Integer> studentIds = new HashSet<>();
							for (StudentCourse student : students) {
								if(!studentIds.contains(student.getStudentId())){
						%>

						<option value="<%=student.getStudentId()%>" >
							<%=student.getStudentName()%></option>
						<%
									studentIds.add(student.getStudentId());
								}
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td><b>Course Name</b></td>
				<td><select name="courseId">
						<%
							Set<Integer> courseIds = new HashSet<>();
							for (StudentCourse student : students) {
								if(!courseIds.contains(student.getCourseId())){
						%>

						<option value="<%=student.getCourseId()%>" >
							<%=student.getCourseName()%></option>
						<%
									courseIds.add(student.getCourseId());
								}
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Create"
					class="link"></td>
			</tr>
		</form>

	</table>
	<hr />
</body>
</html>