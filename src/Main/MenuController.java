/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DataBase.PlayerDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jayden Tosul
 */
public class MenuController implements ActionListener {
    
    //instance variables
    private int playerCount = 0;
    private int createUserInstance = 0;
    private int scoreBoardInstance = 0;
    
    //instance Objects
    private static PlayerDB playerDB = null;
    private MenuPanel mp = null;
    
    //Static Variables
    private static boolean oneJFrameInstance;
    private static String p1Name;
    private static String p2Name;
    private static int p1Score;
    private static int p2Score;
    
    //for storing the score
    public static ArrayList<Player> playerList = new ArrayList<>();
    
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
    
    //resetting the counters
    public void resetCreateUserInstanceZero() {this.createUserInstance = 0;}
    public void resetScoreBoardInstance() {this.scoreBoardInstance = 0;}
    public void resetPlayerCount() {
        if (p1Name != null) {
            p1Name = null;
            p2Name = null;
            p1Score = 0;
            p2Score = 0;
        }
        this.playerCount = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        switch(cmd) {
            case "PLAY"     -> login();
            case "RULES"    -> rules();
            case "EXIT"     -> closeGame();
            case "LOGIN"    -> checkLoginDetails();
            case "NEWUSER"  -> newUser();
            case "CREATE"   -> createNewPlayer();
            case "SCORE"    -> scoreBoard();
//            case "QUICK"    -> quickPlay();
        }
        
    }
    
    //creates chessGame without login
    private void quickPlay() {
        /*
        we wanted to add a quickplay option where u do not need to 
        login to play the game but we didnt have enough time to add
        it in sooo.. yeah :)
        */
        
    }
    
    //displaying the scoreBoard
    private void scoreBoard() {
        
        if (scoreBoardInstance == 0) {
            scoreBoardInstance++;
            getPlayerScores();
            mp.displayScoreBoard(playerList);
        }
    }
    
    //retrives playerSCores
    private void getPlayerScores() {
        ResultSet rs = playerDB.getPlayerData();
        playerList.clear();//clears the list first/handles duplicates
        
        try {//retrives playerDatabase and store in list
            rs.beforeFirst();
            while (rs.next()) {
                String name = rs.getString(1);
                int score = rs.getInt(3);

                //adds to the playerList
                playerList.add(new Player(name, score));
            }
            //sorts the arrayList inOrder
            Collections.sort(playerList);

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //============================ DEBUGGING ==================================
    /*
    We didnt have many JUnit tests because it was somehow not working, like we 
    tried to test with the JUnit test to test our methods but it keeps failing
    or not loading up so this is like the other way tested our methods
    */
//    public static void main(String[] args) {
//        MenuController mc = new MenuController(new MenuPanel());
//        Player p1 = new Player("James", 50);
//        Player p2 = new Player("Zinzan", 20);
//        Player p3 = new Player("Nova", 25);
//       
//        mc.scoreBoard();
//        
//        for (int i = 0; i < playerList.size(); i++) {
//            System.out.println(playerList.get(i).name + " : " + playerList.get(i).score);
//        }
//        
//    }
    //=====================================================================
    
    //creates new user
    private void newUser() {
        if (createUserInstance == 0) {
            createUserInstance = 1;
            mp.createNewUser();
        }
        
    }
    
    //creates the new user
    private void createNewPlayer() {
        String name = mp.getUserName().toLowerCase().trim();
        String password = mp.getCreatePassword();
        
        if (name.equalsIgnoreCase(" ") || password.equals(" ")
                || name.isEmpty() || password.isEmpty()) {
            mp.emptyPassword();
        } else if (name.length() > 12 || password.length() > 12) {
            mp.passwordTooLong();
        }
        //creates new user
        else {
            //sets newUser score to 0
            playerDB.createNewUser(name, password, 0);
            mp.disposeLoginFrame();
            
            System.out.println(name + "\n" + password);
        }
        
    }
    
    //updates the player score when press retry
    public static void updateScore() {
        System.out.println(BoardPanel.WINNER);
        
        switch(BoardPanel.WINNER) {
            case 0 -> {
                p2Score += 10;//if below zero then 0
                p1Score = (p1Score - 5 < 0) ? 0 : (p1Score -= 5);
            }
            case 1 -> {
                p1Score += 10;
                p2Score = (p2Score - 5 < 0) ? 0 : (p2Score -= 5);
            }
        }
        
        System.out.println("White PlayerScore: " + p1Score);
        System.out.println("Black PlayerScore: " + p2Score);
    }
    
    //update playerDataBase when press quit
    public static void updatePlayerDatabase() {
        playerDB.updateUserScore(p1Name, p1Score);
        playerDB.updateUserScore(p2Name, p2Score);
    }
    
    //calls login for both players
    private void login() {
        
        if (playerCount < 2 && playerCount >= 0 && !oneJFrameInstance) {
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
            case 1 -> {
                p1Name  = name;
                p1Score = playerDB.getPlayerScore(name);
            }
            case 2 -> {
                p2Name  = name;
                p2Score = playerDB.getPlayerScore(name);
            }
        }
        
    }
    
    //closes the game and connection to database
    private void closeGame() {
        playerDB.closeConnection();
        System.exit(0);
    }
    
    //when player selects quit
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
