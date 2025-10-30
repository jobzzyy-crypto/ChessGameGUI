/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DataBase.PlayerDB;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

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
    
    //JFrame
    private JFrame frame;

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
            case "EXIT"     -> System.exit(0);
            case "LOGIN"    -> checkLoginDetails();
        }
        
    }
    
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
        switch(playerCount) {//sets name based on playerCount
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
    
    //launches game when both players are logged in
    private void launchGame() {
        if (p1Name != null && p2Name!= null) startBoardPanel();
    }
    
    //resets the boolean so can play again
    public static void setOneJFrameInstanceFalse() {
        oneJFrameInstance = false;
    }
    
    //starts the chessGame | calls boardPanel
    private void startBoardPanel() {
        
        if (!oneJFrameInstance) {
            frame = new JFrame("MyChessGame");

            BoardPanel board = new BoardPanel();//temporary
            WhitePlayerPanel whitePanel = new WhitePlayerPanel(board);
            BlackPlayerPanel blackPanel = new BlackPlayerPanel(board);

            board.setWhiteBlackPanel(whitePanel, blackPanel);

            frame.setSize(720, 760);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);

            frame.add(board, BorderLayout.CENTER);
            frame.add(blackPanel, BorderLayout.NORTH);
            frame.add(whitePanel, BorderLayout.SOUTH);
            board.launchGame();
            frame.pack();
            
            oneJFrameInstance = true;//make sures theres only one instance of this
            
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
