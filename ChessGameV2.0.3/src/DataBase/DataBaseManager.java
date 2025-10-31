/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jayden Tosul
 */
public class DataBaseManager {
    
    private static final String USER_NAME = "chess";  //username
    private static final String PASSWORD = "chess";   //password
    private static final String URL = "jdbc:derby://localhost:1527/ChessPlayerDataBase;create=true";//url
    
    //OBJECT CLASS
    private Connection conn;
    private Statement statement;
    
    //constructor
    public DataBaseManager() {
        establishConnection();
        
        try {
        //================ helps with the debugging =========================
            this.statement = this.conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
        //==================================================================
            
//            this.statement = this.conn.createStatement();
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //getting the connection
    public Connection getConnection() {
        return this.conn;
    }
    
    //displays the result
    public void printResult(ResultSet rs) {
        if (rs == null) return;
        
        try {
            while(rs.next()) {
                String user = rs.getString(1);
                String password = rs.getString(2);
                int score = rs.getInt(3);
                
                System.out.println(user + " " + password + " " + score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //=================================================================
    
    //establish the connection to the database
    private void establishConnection() {
        if (this.conn == null) {    //check if the connection is null
            try {
                //connects to the database
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            
                System.out.println("Player Database Connected...");
                System.out.println(URL);
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    //closes the connection to the database
    public void closeConnection() {
        if (this.conn != null) {//check if there is connection
            try {
                conn.close();
                
                System.out.println("Connection Closed!!");
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //executes a query & gets data from the database
    public ResultSet queryPlayerDB(String sql) {
        ResultSet result = null;
        
        try {
            //creates an sql query statemetn
            result = statement.executeQuery(sql);//executes the sql statment
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;//results the results
    }
    
    //executes an sql statement
    public void executeStatement(String sql) {
        try {
            statement.executeUpdate(sql);//updates database
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
