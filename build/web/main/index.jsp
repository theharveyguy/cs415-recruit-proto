<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RECRUIT (Prototype)</title>
        
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <script type="text/javascript" src="scripts/jquery-3.3.1.min.js"></script>
        
    </head>
    
    <body>
        
        <%
            if ( request.isUserInRole("recruiter") ) {
        %>

        <p>Welcome, <%=request.getRemoteUser() %>! You are logged in as a RECRUITER.</p>
        <p><a href="recruiters/schoolprofile.html">View/Edit School Profile</a></p>

        <%
            }

            else if ( request.isUserInRole("athlete") ) {
        %>

        <p>Welcome, <%=request.getRemoteUser() %>! You are logged in as an ATHLETE.</p>
        <p><a href="athletes/athleteprofile.html">View/Edit Athlete Profile</a></p>

        <%
            }
        %>
        
        <p><a href="../logout.jsp">Log Out</a></p>
        
    </body>
    
</html>