package edu.jsu.mcis.recruitproto;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AthleteProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        
        PrintWriter output = response.getWriter();
        
        // decide what to do based on request
        String[] data = request.getParameterValues("data");
        try {
            RecruitDatabase db = new RecruitDatabase();
            
            switch (data[0]){
            case "country":
                output.println(db.getCountryAsHTML());
                break;
            case "region":
                output.println(db.getRegionAsHTML(data[1]));
                break;
            case "city":
                output.println(db.getCityAsHTML(data[1]));
                break;
            case "sports":
                output.println(db.getSportsAsHTML());
                break;
            default:
                break;
            }
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Not yet supported
        doGet(request,response);
        
    }
        
    @Override
    public String getServletInfo() {
        
        return "Athlete Profile Servlet";
        
    }

}