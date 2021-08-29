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
	<h1>Displaying Students List</h1>
	<table border="1" width="500" style="text-align: center">


		<form action="../courseservice" method="post">
			<tr>
				<td><b>First Name</b></td>
				<td><input type="text" name="courseName" value=""
					maxlength="50"></td>
			</tr>
			<tr>
				<td><b>First Name</b></td>
				<td><input type="text" name="courseDetail" value=""
					maxlength="200"></td>
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