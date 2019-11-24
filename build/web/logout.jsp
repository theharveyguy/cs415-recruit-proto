<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logging Out ...</title>
        
    </head>
    
    <body>
        
        <%@ page session="true" %>
        
        <%
        
            session.invalidate();
            
            response.sendRedirect(".");
        
        %>
        
    </body>
    
</html>
