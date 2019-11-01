<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>

        <title>RECRUIT (Prototype) Login</title>

        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">

        <meta http-equiv="Cache-Control" content="no-store,no-cache,must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">

    </head>

    <body>

        <form id="loginform" name="loginform" method="POST" action="j_security_check" accept-charset="UTF-8">
            
            <fieldset>
                
                <legend>Login</legend>
                
                <label for="username">Username:</label>
                <input id="j_username" name="j_username" type="text" value="" tabindex=1 />

                <label for="password">Password:</label>
                <input id="j_password" name="j_password" type="password" value="" tabindex=2 />

                <input type="submit" value="Log In" tabindex=3 />
                
            </fieldset>
            
        </form>
        
        <%

             String result = request.getParameter("result");

             if (result == null)

                result = "";

             if (result.equalsIgnoreCase("false")) {

        %>

        <p><b>There was a problem processing your login request.</b></p>
        
        <%

             }

        %>
        
    </body>

</html>