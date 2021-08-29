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
	<input type="button" value="Create Course" class="link"
		onClick="javascript:window.location='course/course-create.jsp';">
	<h1>Displaying Courses List</h1>
	<table border="1" width="500" align="center">
		<tr bgcolor="00FF7F">
			<th>Edit Action</th>
			<th><b>Course Name</b></th>
			<th><b>Course Detail</b></th>
			<th>Delete Action</th>
		</tr>

		<%
			ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
			if (courses == null) {
				courses = new ArrayList<>();
			}
			for (Course s : courses) {
		%>

		<tr>
			<td>
				<form action="courseservice" method="get">
				<input type="hidden" name="operation" value="put"/>
					<input type="hidden" name="courseId" value="<%=s.getCourseId()%>"/>
					<input type="submit" value="Edit" class="link"/>
				</form>
			</td>
			<td><%=s.getCourseName()%></td>
			<td><%=s.getCourseDetail()%></td>
			<td>
				<form action="courseservice" method="post">
				<input type="hidden" name="operation" value="delete"/>
					<input type="hidden" name="courseId" value="<%=s.getCourseId()%>"/>
					<input type="submit" value="Delete" class="link"/>
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<table border="1" width="500" align="center">
		<tr align="center">
			<%
				if (request.getAttribute("totalCount") != null) {
					long totalCount = (Long) request.getAttribute("totalCount");
					for (long position = 0; totalCount>0; position++, totalCount-=10) {
			%>
			<form action="courseservice" method="get">
				<input type="hidden" name="limit" value="10"/>
				<input type="hidden" name="pageNumber" value="<%=(position)+1%>"/>  
				<input type="submit" value="<%=(position*10)+1%>-<%=(position+1)*10%>" class="link"/>
			</form> &nbsp;&nbsp;
			<%
					}
				}
			%>
		</tr>
	</table>
	<hr />
</body>
</html>