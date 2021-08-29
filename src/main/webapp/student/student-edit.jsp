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
			Student student = (Student) request.getAttribute("student");
		%>

		<tr>
			<form action="studentservice" method="post">
			<input type="hidden" name="operation" value="put"/>
				<input type="hidden" name="studentId" value="<%=student.getStudentId()%>"/>
				<tr>
					<td><b>First Name</b></td>
					<td><input type="text" name="firstName"
						value=<%=student.getFirstName()%> maxlength="50"></td>
				</tr>
				<tr>
					<td><b>Last Name</b></td>
					<td><input type="text" name="lastName"
						value=<%=student.getLastName()%> maxlength="50"></td>
				</tr>
				<tr>
					<td><b>Date of Birth</b></td>
					<td><input type="text" name="dob"
						value=<%=student.getDateOfBirth()%>> <b>format :
							YYYY-MM-dd </b></td>
				</tr>
				<tr>
					<td><b>Contact Number</b></td>
					<td><input type="text" name="contactNumber"
						value=<%=student.getContactNumber()%> maxlength="10"></td>
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