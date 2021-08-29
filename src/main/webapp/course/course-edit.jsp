<%@page import="com.exercise.scregistration.db.model.Course"%>
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
	<h1>Edit Student</h1>
	<table border="1" width="500" align="center">

		<%
			Course item = (Course) request.getAttribute("course");
		%>

		<tr>
			<form action="courseservice" method="post">
			<input type="hidden" name="operation" value="put"/>
				<input type="hidden" name="courseId" value="<%=item.getCourseId()%>"/>
				<tr>
					<td><b>Course Name</b></td>
					<td><input type="text" name="courseName"
						value="<%=item.getCourseName()%>" maxlength="50"></td>
				</tr>
				<tr>
					<td><b>Course Detail</b></td>
					<td><input type="text" name="courseDetail"
						value="<%=item.getCourseDetail()%>" maxlength="200"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Edit" class="link">
					</td>
				</tr>
			</form>
		</tr>
	</table>
	<hr />
</body>
</html>