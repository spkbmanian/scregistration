<%@ page isErrorPage="true" import="java.io.*" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Error Page</title>
  </head>
  <body>
  <h3>Sorry an exception occured!</h3>  
      <h1>Error while performing :   		<%if(request.getAttribute("errorMessage")!=null) { %>
  			<%=request.getAttribute("errorMessage")%>
  		<%} %> </h1> 
      <hr/>
      

Exception is: <%= exception %>
      
    </body>
</html>