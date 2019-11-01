package edu.jsu.mcis.recruitproto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.json.simple.*;

public class RecruitDatabase {
    
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public RecruitDatabase() throws NamingException {
        
        try {
            
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/recruit_db");
            conn = ds.getConnection();
            System.err.println("*** RECRUIT DATABASE CONNECTION ESTABLISHED!");
            
        }
        
        catch (SQLException e) {
            
            System.err.println("*** RECRUIT DATABASE SQL EXCEPTION: " + e.toString());
            
        }

    }
    
    protected String getSchoolProfile(String username) {
        
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        
        HashMap<String, Object> results = new HashMap<>();
        ArrayList<String> sports_men = new ArrayList<>();
        ArrayList<String> sports_women = new ArrayList<>();
        
        int id = getUserId(username);
        int schoolid = getSchoolId(id);
        
        try {
            
            /* Get School Profile Info */
            
            query = "SELECT * FROM (school JOIN location ON school.locationid = location.id) WHERE school.id=?";
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, schoolid);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                results.put("schoolname", resultset.getString("name"));
                results.put("schooltype", resultset.getInt("schooltypeid"));
                
                results.put("num_students", resultset.getInt("num_students"));
                results.put("sat", resultset.getInt("min_sat"));
                results.put("toefl", resultset.getInt("min_toefl"));
                results.put("min_expenses", resultset.getInt("min_expenses"));
                results.put("max_expenses", resultset.getInt("max_expenses"));
                results.put("scholarships", (resultset.getBoolean("scholarships") ? 1 : 0));
                
                results.put("teamname", resultset.getString("team").trim());

                results.put("address", resultset.getString("address"));
                results.put("address2", resultset.getString("address2"));

                results.put("city", resultset.getString("city"));
                results.put("state", resultset.getString("state"));
                results.put("zip", resultset.getString("zip"));
                results.put("country", resultset.getString("country"));

            }
            
            /* Get Recruiter Contact Info */
            
            query = "SELECT * FROM recruiter WHERE userid=?";
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, id);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                results.put("fname", resultset.getString("firstname"));
                results.put("mname", resultset.getString("middlename"));
                results.put("lname", resultset.getString("lastname"));

                results.put("email", resultset.getString("email"));

            }
            
            /* Get Conference Info */
            
            query = "SELECT * FROM conference";
            pstatement = conn.prepareStatement(query);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                results.put("conferences_list", resultset.getString("desc"));

            }
            
            /* Get Division Info */
            
            query = "SELECT * FROM division";
            pstatement = conn.prepareStatement(query);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                results.put("divisions_list", resultset.getString("desc"));

            }
            
            /* Get Mens' Sports */
            
            query = "SELECT sportid, `desc` FROM ((school JOIN schoolsport ON school.id = schoolsport.schoolid) " +
                    "JOIN sport ON sport.id = schoolsport.sportid) WHERE schoolid=? AND genderid=1";
            
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, schoolid);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                sports_men.add(resultset.getString("desc"));

            }
            
            results.put("sports_men_list", sports_men);
            
            /* Get Womens' Sports */
            
            query = "SELECT sportid, `desc` FROM ((school JOIN schoolsport ON school.id = schoolsport.schoolid) " +
                    "JOIN sport ON sport.id = schoolsport.sportid) WHERE schoolid=? AND genderid=2";
            
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, schoolid);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                sports_women.add(resultset.getString("desc"));

            }
            
            results.put("sports_women_list", sports_women);

        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
        }
        
        System.err.println(JSONValue.toJSONString(results));
        return JSONValue.toJSONString(results);
        
    }
    
    protected String getAthleteProfile(String username) {
        
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        
        HashMap<String, Object> results = new HashMap<>();
        int id = getUserId(username);
        
        try {
            
            query = "SELECT *, MONTH(dob) AS dob_m, DAY(dob) AS dob_d, YEAR(dob) AS dob_y FROM athlete JOIN location ON athlete.locationid = location.id WHERE userid=?";
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, id);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                results.put("fname", resultset.getString("firstname").trim());
                results.put("mname", resultset.getString("middlename").trim());
                results.put("lname", resultset.getString("lastname").trim());

                results.put("address", resultset.getString("address").trim());
                results.put("address2", resultset.getString("address2").trim());

                results.put("city", resultset.getString("city").trim());
                results.put("state", resultset.getString("state").trim());
                results.put("zip", resultset.getString("zip").trim());
                results.put("country", resultset.getString("country").trim());

                results.put("gender", resultset.getInt("genderid"));

                results.put("dob_m", resultset.getInt("dob_m"));
                results.put("dob_d", resultset.getInt("dob_d"));
                results.put("dob_y", resultset.getInt("dob_y"));

                results.put("sat", resultset.getInt("sat"));
                results.put("toefl", resultset.getInt("toefl"));
                results.put("budget", resultset.getInt("budget"));

                results.put("email", resultset.getString("email").trim());

                results.put("highschool", resultset.getString("highschool").trim());

                results.put("awards", resultset.getString("awards").trim());
                results.put("social", resultset.getString("social").trim());
                
            }

        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
        }
        
        return JSONValue.toJSONString(results);
        
    }
    
    protected int postAthleteProfile(String username, HashMap<String, Object> data) {
        
        PreparedStatement ps = null;
        ResultSet resultset = null;
        
        int id = 0, locationid = 0, result = 0;
        int userid = getUserId(username);
        
        try {
            
            /* Is this a new profile or an existing profile? Attempt to get ID */
            
            String query = "SELECT id, locationid FROM athlete WHERE userid=?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userid);
            
            resultset = ps.executeQuery();
            
            while(resultset.next()) {
                id = resultset.getInt("id");
                locationid = resultset.getInt("locationid");
            }
            
            /* Were any records found? */
            
            if (id == 0) { // This is a new profile
                
                /* Add Location */
                
                query = "INSERT INTO location (address, address2, city, state, zip, country) VALUES (?,?,?,?,?,?)";
                ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, data.get("address").toString());
                ps.setString(2, data.get("address2").toString());
                ps.setString(3, data.get("city").toString());
                ps.setString(4, data.get("state").toString());
                ps.setString(5, data.get("zip").toString());
                ps.setString(6, data.get("country").toString());
                
                result = ps.executeUpdate();
                
                /* Get New Location ID */
                
                if (result == 1) {
                   resultset = ps.getGeneratedKeys();
                   if (resultset.next()) {
                      locationid = resultset.getInt(1);
                   }
                }
                
                /* Add Athlete Profile */
                
                query = "INSERT INTO athlete (firstname, middlename, lastname, genderid, locationid, sat, toefl, budget, highschool, awards, email, userid, social, dob) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, data.get("fname").toString());
                ps.setString(2, data.get("mname").toString());
                ps.setString(3, data.get("lname").toString());
                ps.setInt(4, Integer.parseInt(data.get("gender").toString()));
                ps.setInt(5, locationid);
                ps.setInt(6, Integer.parseInt(data.get("sat").toString()));
                ps.setInt(7, Integer.parseInt(data.get("toefl").toString()));
                ps.setInt(8, Integer.parseInt(data.get("budget").toString()));
                ps.setString(9, data.get("highschool").toString());
                ps.setString(10, data.get("awards").toString());
                ps.setString(11, data.get("email").toString());
                ps.setInt(12, userid);
                ps.setString(13, data.get("social").toString());
                
                int dob_m = Integer.parseInt(data.get("dob_m").toString());
                int dob_d = Integer.parseInt(data.get("dob_d").toString());
                int dob_y = Integer.parseInt(data.get("dob_y").toString());
                
                GregorianCalendar gc = new GregorianCalendar();
                gc.set(Calendar.MONTH, dob_m - 1);
                gc.set(Calendar.DAY_OF_MONTH, dob_d);
                gc.set(Calendar.YEAR, dob_y);
                
                ps.setDate(14, new Date(gc.getTimeInMillis()));
                
                result = ps.executeUpdate();
                
                /* Get New Location ID */
                
                if (result == 1) {
                   resultset = ps.getGeneratedKeys();
                   if (resultset.next()) {
                      result = resultset.getInt(1);
                   }
                }
                
            }
            
            else { // This is an existing profile
                
                /* Update Location */
                
                query = "UPDATE location SET address=?, address2=?, city=?, state=?, zip=?, country=? WHERE id=?";                ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, data.get("address").toString());
                ps.setString(2, data.get("address2").toString());
                ps.setString(3, data.get("city").toString());
                ps.setString(4, data.get("state").toString());
                ps.setString(5, data.get("zip").toString());
                ps.setString(6, data.get("country").toString());
                ps.setInt(7, locationid);
                
                result = ps.executeUpdate();
                
                /* Get New Location ID */
                
                if (result == 1) {
                   resultset = ps.getGeneratedKeys();
                   if (resultset.next()) {
                      locationid = resultset.getInt(1);
                   }
                }
                
                /* Update Athlete Profile */
                
                query = "UPDATE athlete SET firstname=?, middlename=?, lastname=?, genderid=?, sat=?, toefl=?, budget=?, highschool=?, awards=?, email=?, social=?, dob=? WHERE userid=?";
                ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, data.get("fname").toString());
                ps.setString(2, data.get("mname").toString());
                ps.setString(3, data.get("lname").toString());
                ps.setInt(4, Integer.parseInt(data.get("gender").toString()));
                ps.setInt(5, Integer.parseInt(data.get("sat").toString()));
                ps.setInt(6, Integer.parseInt(data.get("toefl").toString()));
                ps.setInt(7, Integer.parseInt(data.get("budget").toString()));
                ps.setString(8, data.get("highschool").toString());
                ps.setString(9, data.get("awards").toString());
                ps.setString(10, data.get("email").toString());
                ps.setString(11, data.get("social").toString());
                
                int dob_m = Integer.parseInt(data.get("dob_m").toString());
                int dob_d = Integer.parseInt(data.get("dob_d").toString());
                int dob_y = Integer.parseInt(data.get("dob_y").toString());
                
                GregorianCalendar gc = new GregorianCalendar();
                gc.set(Calendar.MONTH, dob_m - 1);
                gc.set(Calendar.DAY_OF_MONTH, dob_d);
                gc.set(Calendar.YEAR, dob_y);
                
                ps.setDate(12, new Date(gc.getTimeInMillis()));
                ps.setInt(13, userid);
                
                result = ps.executeUpdate();
                
                /* Get New Location ID */
                
                if (result == 1) {
                   resultset = ps.getGeneratedKeys();
                   if (resultset.next()) {
                      result = resultset.getInt(1);
                   }
                }
                
            }

        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (ps != null) { try { ps.close(); ps = null; } catch (Exception e) {} }
            
        }
        
        return result;
        
    }

    protected int getUserId(String username) {
        
        /* Look up username; get corresponding user ID */
        
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        int id = 0;
            
        try {
            
            query = "SELECT * FROM user WHERE username=?";
            pstatement = conn.prepareStatement(query);
            pstatement.setString(1, username);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                id = resultset.getInt("id");
                
            }

        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
        }
        
        return id;
        
    }

    protected int getSchoolId(int userid) {
        
        /* Look up username; get corresponding user ID */
        
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        int id = 0;
            
        try {
            
            query = "SELECT * FROM recruiter WHERE userid=?";
            pstatement = conn.prepareStatement(query);
            pstatement.setInt(1, userid);
            
            resultset = pstatement.executeQuery();
            
            while(resultset.next()) {
                
                id = resultset.getInt("schoolid");
                
            }

        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
        }
        
        return id;
        
    }
    
    public void closeConnection() {
        
        if (conn != null) {
            
            try {
                conn.close();
            }
            
            catch (SQLException e) {
                System.err.println("*** LABDATABASE SQL EXCEPTION: " + e.toString());
            }
            
        }
    
    } // End closeConnection()    
    
    public Connection getConnection() { return conn; }
    
}