package edu.jsu.mcis.cs425.project2;

import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public Database() throws NamingException {
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
    
    public HashMap getUserInfo(String username){
        HashMap <String,String> results = null;
        
        //db pool variables
        Database db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        boolean hasResult;
        
        try{
            connection = getConnection();
            
            query = "SELECT * FROM 'user' WHERE username = ?";
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1,username);
            
            hasResult = pstatement.execute();
            
            if (hasResult) {
                resultset = pstatement.getResultSet();
                
                if (resultset.next()) {
                    // initialize HashMap
                    // add user data from resultset
                    // use key name "id" for the ID and "displayname" for the display name
                }
            }
        }
        catch (Exception e) { System.err.println( e.toString() ); }
    }
}
