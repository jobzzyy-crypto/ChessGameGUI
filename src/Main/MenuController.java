/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DataBase.PlayerDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jayden Tosul
 */
public class MenuController implements ActionListener {
    
    //instance variables
    private int playerCount = 0;
    
    //instance Objects
    private final PlayerDB playerDB;
    private final MenuPanel mp;
    
    //Static Variables
    private static boolean oneJFrameInstance;
    private static String p1Name;
    private static String p2Name;
    private static int p1Score;
    private static int p2Score;
    
    //constructor
    public MenuController(MenuPanel mp) {
        this.mp  = mp;
        playerDB = new PlayerDB();
    }
    
    //getters for playerName and playerScore
    public static String getP1Name() {return p1Name;}
    public static String getP2Name() {return p2Name;}
    public static int getP1Score() {return p1Score;}
    public static int getP2Score() {return p2Score;}

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        switch(cmd) {
            case "PLAY"     -> login();
            case "RULES"    -> rules();
            case "EXIT"     -> closeGame();
            case "LOGIN"    -> checkLoginDetails();
        }
        
    }
    
    //updates the player score
    
    
    //calls login for both players
    private void login() {
        if (playerCount < 2 && playerCount >= 0) {
            playerCount++;//increase count first
            mp.login(playerCount);
        } else {//reset count
            playerCount = 0;
        }
    }
    
    //checking player login
    private void checkLoginDetails() {
        //handles null/ basically if, sets it to empty string " " so can display wrong credentials
        String name = (!mp.getUserName().trim().isEmpty()) ? mp.getUserName().trim() : " ";
        String password = (!mp.getPassword().isEmpty()) ? mp.getPassword() : " ";
        
        //if wrong display wrong | guards against same player twice
        if (name.equalsIgnoreCase(p1Name) || name.equalsIgnoreCase(p2Name) || name.equalsIgnoreCase(" ")
                || !playerDB.checkUserNameAndPassword(name, password))//checks players name & password
        {
            System.out.println("Password is wrong: " + password);
            mp.wrongCredentials();
        }
        else {//checks users credentials
            if (playerDB.checkUserNameAndPassword(name, password)) {
                System.out.println("correct");
                
                setPlayerNames(name);//sets the name and score

                //dispose frame calls login again but for player 2
                mp.disposeLoginFrame();
                login();
            }
            
        }
        
        launchGame();//checks if both players login then start game
        
    }
    
    //sets the player1 and player2 names and scores
    private void setPlayerNames(String name) {
        //sets name based on playerCount
        switch(playerCount) {
            case 1 -> {//get score first b4 set name coz we capitaliez 1st letter
                p1Score = playerDB.getPlayerScore(name);
                p1Name  = capitalizeFirstLetter(name);
            }
            case 2 -> {
                p2Score = playerDB.getPlayerScore(name);
                p2Name  = capitalizeFirstLetter(name);
            }
        }
        
    }
    
    //this is just a helper method to capitalize the first letter for displaying
    private String capitalizeFirstLetter(String name) {
        String s = (name.charAt(0) + "").toUpperCase();//capitalize first letter
        //adds the rest to string
        for (int i = 1; i < name.length(); i++) {
            s = s + name.charAt(i);
        }
        
        return s;
    }
    
    //closes the game and connection to database
    private void closeGame() {
        playerDB.closeConnection();
        System.exit(0);
    }
    
    //when player selects retry
    public static void resetPlayerNamesScore() {
        p1Name = null;
        p2Name = null;
        p1Score = 0;
        p2Score = 0;
    }
    
    //launches game when both players are logged in
    private void launchGame() {
        if (p1Name != null && p2Name!= null) startGame();
    }
    
    //resets the boolean so can play again
    public static void setOneJFrameInstanceFalse() {
        oneJFrameInstance = false;
    }
    
    //starts the chessGame | calls boardPanel
    private void startGame() {
        //make sures theres only one instance of this
        if (!oneJFrameInstance) {
            mp.startBoardPanel();
            oneJFrameInstance = true;
            
        }
        
    }
    
    //displays the rules
    private void rules() {
        String helpText = """
            CHESS RULES
            
            BASIC MOVES:
            • Pawn: Moves forward one square, captures diagonally
            • Rook: Moves horizontally or vertically any number of squares
            • Knight: Moves in L-shape (2 squares in one direction, then 1 perpendicular)
            • Bishop: Moves diagonally any number of squares
            • Queen: Moves any number of squares in any direction
            • King: Moves one square in any direction
            
            SPECIAL MOVES:
            • Castling: King moves 2 squares toward rook, rook jumps over
            • En Passant: Special pawn capture
            • Promotion: Pawn reaches opposite side becomes Queen, Rook, Bishop, or Knight
            
            GAME OBJECTIVE:
            • Checkmate opponent's King
            • Stalemate results in draw
            
            HOW TO PLAY:
            • Click and drag pieces to move
            • Legal moves highlighted in yellow
            • Illegal moves highlighted in red
            """;
            
        mp.rulesWindow(helpText);//opens textArea
    }
    
}
