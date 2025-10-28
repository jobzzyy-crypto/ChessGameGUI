/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;

/**
 *
 * @author Jayden Tosul, Job Rotoava
 */

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        
        //display the playerInputName
//        PlayerLogin playerDialog = new PlayerLogin(frame);
//        playerDialog.setVisible(true);

        // Create board with player names 
        BoardPanel board = new BoardPanel();//temporary
        WhitePlayerPanel whitePanel = new WhitePlayerPanel(board);
        BlackPlayerPanel blackPanel = new BlackPlayerPanel(board);
        
        board.setWhiteBlackPanel(whitePanel, blackPanel);

        frame.setSize(720, 760);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
        frame.add(board, BorderLayout.CENTER);
        frame.add(blackPanel, BorderLayout.NORTH);
        frame.add(whitePanel, BorderLayout.SOUTH);
        board.launchGame();
        
        
        frame.pack();

    }
    
}