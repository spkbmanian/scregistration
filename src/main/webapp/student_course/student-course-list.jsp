<%@page import="com.exercise.scregistration.db.model.StudentCourse"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.exercise.scregistration.db.model.Student"%>
<%@ page errorPage="/error.jsp" %>  
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
  		<%if(request.getAttribute("errorMessage")!=null) { %>
  			<%=request.getAttribute("errorMessage")%>
  		<%} %>
  		
      <h1>Displaying Students Course List</h1>
	<form action="studentCourseService" method="get">
		<input type="submit" value="Create Student's Courses" class="link" />
	</form>
      <table border ="1" width="500" align="center">
         <tr bgcolor="00FF7F">
          <th><b>First Name</b></th>
          <th><b>Last Name</b></th>
         </tr>

        <%ArrayList<StudentCourse> std = (ArrayList<StudentCourse>)request.getAttribute("studentcourses");
		if (std == null) {
			std = new ArrayList<>();
		}
        for(StudentCourse s:std){%>

            <tr>
                <td><%=s.getStudentName()%></td>
                <td><%=s.getCourseName()%></td>
            </tr>
            <%}%>
        </table>
<table border="1" width="500" align="center">
		<tr align="center">
			<%
				if (request.getAttribute("totalCount") != null) {
					long totalCount = (Long) request.getAttribute("totalCount");
					for (long position = 0; totalCount>0; position++, totalCount-=10) {
			%>
			<form action="studentCourseService" method="get">
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
        <hr/>
    </body>
</html>