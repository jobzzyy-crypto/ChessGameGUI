/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jayden Tosul
 */
public class PlayerDB {
    
    //OBJECT CLASS
    private final DataBaseManager dbmanager;
    private final Connection conn;
    
    //constructor
    public PlayerDB() {
        dbmanager = new DataBaseManager();
        conn = dbmanager.getConnection();
        
        //creates the chessPlayerInfo table
        createChessPlayerInfoTable();
    }
    
    //get player score
    public int getPlayerScore(String name) {
        int playerScore = 0;
        ResultSet rs = getPlayerData();
        
        try {
            rs.beforeFirst();
            while (rs.next()) {
                String playerName = rs.getString(1);//first column
                
                //gets player name and check if the same
                if (playerName.equalsIgnoreCase(name) && !name.isEmpty()) {
                    playerScore = rs.getInt(3);//gets the playerscore in column 3
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playerScore;
    }
    
    //retrieves userdata from the database
    public ResultSet getPlayerData() {
        return dbmanager.queryPlayerDB("SELECT * FROM CHESSPLAYERDB");
    }
    
    //creates the createchessPlayerInfoTable table
    public final void createChessPlayerInfoTable() {
        if (!checkTableExist("CHESSPLAYERDB")) {
            String createTableSQL = "CREATE TABLE CHESSPLAYERDB"
                    + "(USERID VARCHAR(12), "
                    + "PASSWORD VARCHAR(12), "
                    + "SCORE INT)";
            
            //creates the userinfo table
            dbmanager.executeStatement(createTableSQL);
            System.out.println("CHESSPLAYERDB TABLE CREATED");
            
        } else {
            System.out.println("CHESSPLAYERDB TABLE EXISTS");
        }
        
    }
    //check if a table exist already
    private boolean checkTableExist(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            
            try (ResultSet rsdbmd = dbmd.getTables(null, null, null, types)) {
                while (rsdbmd.next()) {
                    String table_name = rsdbmd.getString("TABLE_NAME");
                    
                    //if table exist return true;
                    if (table_name.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;//returns false if table doesnot exist
    }
    
    //close connection to the database
    public void closeConnection() {
        dbmanager.closeConnection();
    }
    
    //creates a new user/player
    public void createNewUser(String name, String password, int score) {
        String sql = "INSERT INTO CHESSPLAYERDB VALUES"
                + "('" + name + "', '" + password + "', " + score + ")";
        
        dbmanager.executeStatement(sql);
        System.out.println("New User Created");
        
    }
    
    //checcks if a certain user exist in the database
    public boolean checkUserNameAndPassword(String name, String password) {
        ResultSet rs = getPlayerData();
        
        try {
            rs.beforeFirst();
            while (rs.next()) {
                String playerName = rs.getString(1);//first column
                String playerPassword = rs.getString(2);//second column
                
                //gets player name and check if the same
                if (playerName.equalsIgnoreCase(name) && !name.isEmpty() &&
                        playerPassword.equals(password) && !password.isEmpty()) {
                    System.err.println(playerPassword);
                    return true;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;//returns false if player doesnt exists
    }
    
    //updates the user informations (score)
    public void updateUserScore(String name, int score) {
        String updateSQL = "UPDATE CHESSPLAYERDB "
                + "SET SCORE = " + score + " "
                + "WHERE USERID = '" + name.toLowerCase() + "'";
        
        //sends sql update to the database
        dbmanager.executeStatement(updateSQL);
        
    }
    
}
