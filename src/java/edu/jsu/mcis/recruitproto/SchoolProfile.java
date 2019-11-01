package edu.jsu.mcis.recruitproto;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SchoolProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.err.println("*** GET SCHOOLPROFILE");
        
        try {
            
            RecruitDatabase db = new RecruitDatabase();
            
            PrintWriter out = response.getWriter();
            
            String output = db.getSchoolProfile(request.getRemoteUser());
            
            out.println(output);
            
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Not yet supported
        
    }

    @Override
    public String getServletInfo() {
        
        return "School Profile Servlet";
        
    }

}