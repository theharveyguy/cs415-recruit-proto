package edu.jsu.mcis.recruitproto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RecruitDatabase {
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public RecruitDatabase() throws NamingException {
        try {
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();   
        }
        catch (SQLException e) {}
    } // Constructor

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {}   
        }
    } // End closeConnection()

    public Connection getConnection() { return conn; }
    
    public String getCountryAsHTML(){
        StringBuilder s = new StringBuilder();
        
        //db pool variables
        RecruitDatabase db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;
        
        try{
            connection = getConnection();
            
            query = "SELECT * FROM country;";
            pstatement = connection.prepareStatement(query);
            
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet();
                if (resultset.next()){
                    //fill HTML form in loop
                    while (resultset.next()){
                        // format
                        // <option value = "name"> Name </option>
                        s.append("<option value = \"");
                        s.append(resultset.getString("name")).append("\">").append(resultset.getString("name"));
                        s.append("</option> \n");
                    }
                }
            }
            
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return s.toString();
    }
    public String getRegionAsHTML(String countryid){
        StringBuilder s = new StringBuilder();
        
        //db pool variables
        RecruitDatabase db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;
        
        try{
            connection = getConnection();
            
            query = "SELECT * FROM region WHERE countryid = ?;";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1,countryid);
            
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet();
                if (resultset.next()){
                    //fill HTML form in loop
                    while (resultset.next()){
                        // format
                        // <option value = "name"> Name </option>
                        s.append("<option value = \"");
                        s.append(resultset.getString("name")).append("\">").append(resultset.getString("name"));
                        s.append("</option> \n");
                    }
                }
            }
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return s.toString();
    }
    public String getCityAsHTML(String regionid){
        StringBuilder s = new StringBuilder();
        
        //db pool variables
        RecruitDatabase db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;
        
        try{
            connection = getConnection();
            
            query = "SELECT * FROM city WHERE regionid = ?;";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1,regionid);
            
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet();
                if (resultset.next()){
                    //fill HTML form in loop
                    while (resultset.next()){
                        // format
                        // <option value = "name"> Name </option>
                        s.append("<option value = \"");
                        s.append(resultset.getString("name")).append("\">").append(resultset.getString("name"));
                        s.append("</option> \n");
                    }
                }
            } 
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return s.toString();
    }
    public String getSportsAsHTML(){
        StringBuilder s = new StringBuilder();
        
        //db pool variables
        RecruitDatabase db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;
        
        try{
            connection = getConnection();
            
            query = "";
            pstatement = connection.prepareStatement(query);
            
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return s.toString();
    }
}
