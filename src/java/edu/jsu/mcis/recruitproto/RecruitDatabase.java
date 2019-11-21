package edu.jsu.mcis.recruitproto;

import java.sql.Connection;
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
        //TODO
        return s.toString();
    }
    public String getRegionAsHTML(){
        StringBuilder s = new StringBuilder();
        //TODO
        return s.toString();
    }
    public String getCityAsHTML(){
        StringBuilder s = new StringBuilder();
        //TODO
        return s.toString();
    }
    public String getSportsAsHTML(){
        StringBuilder s = new StringBuilder();
        //TODO
        return s.toString();
    }
}
