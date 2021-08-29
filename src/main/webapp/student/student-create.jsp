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
	<table border="1" width="500" align="center">


		<form action="../studentservice" method="post">
			<tr>
				<td><b>First Name</b></td>
				<td><input type="text" name="firstName" value="" maxlength="50">
				</td>
			</tr>
			<tr>
				<td><b>Last Name</b></td>
				<td><input type="text" name="lastName" value="" maxlength="50">
				</td>
			</tr>
			<tr>
				<td><b>Date Of Birth</b></td>
				<td><input type="text" name="dob" value=""> <b>format
						: YYYY-MM-dd </b></td>
			</tr>
			<tr>
				<td><b>Contact No</b></td>
				<td><input type="text" name="contactNumber" value=""
					maxlength="10"></td>
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