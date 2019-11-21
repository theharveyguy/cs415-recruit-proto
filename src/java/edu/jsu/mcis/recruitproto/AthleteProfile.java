package edu.jsu.mcis.recruitproto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AthleteProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.err.println("*** GET ATHLETEPROFILE");
        
        try {
            
            RecruitDatabase db = new RecruitDatabase();
            
            PrintWriter out = response.getWriter();
            
            String output = db.getAthleteProfile(request.getRemoteUser());
            
            out.println(output);
            
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.err.println("*** POST ATHLETEPROFILE");

        try {
            
            RecruitDatabase db = new RecruitDatabase();
            
            PrintWriter out = response.getWriter();
            
            HashMap<String, Object> data = new HashMap(request.getParameterMap());
            
            for (HashMap.Entry<String, Object> e : data.entrySet()) {
                
                String key = e.getKey();
                String value = ((String[])e.getValue())[0];
                data.put(key, value);
                
            }
            
            //String output = JSONValue.toJSONString(results);
            
            int result = db.postAthleteProfile(request.getRemoteUser(), data);
            
            out.println("{\"result\":" + result + "}");
            
        }
        
        catch (Exception e) {
            
            System.err.println(e.toString());
            
        }
        
    }
    
    public String getCountry() throws NamingException{
        RecruitDatabase db = new RecruitDatabase();
        // TODO
    }
    
    public String getRegion() throws NamingException{
        RecruitDatabase db = new RecruitDatabase();
        // TODO
    }
    
    public String getCity() throws NamingException{
        RecruitDatabase db = new RecruitDatabase();
        // TODO
    }
    
    public String getSports() throws NamingException{
        RecruitDatabase db = new RecruitDatabase();
        // TODO
    }

    @Override
    public String getServletInfo() {
        
        return "Athlete Profile Servlet";
        
    }

}