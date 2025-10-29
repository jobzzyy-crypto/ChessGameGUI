/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DataBase.PlayerDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Jayden Tosul
 */
public class MenuController implements ActionListener {
    
    //instance Objects
    private final PlayerDB playerDB;
    private final MenuPanel mp;
    
    //constructor
    public MenuController(MenuPanel mp) {
        this.mp  = mp;
        playerDB = new PlayerDB();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        switch(cmd) {
            case "PLAY" -> startBoardPanel();
            case "RULES" -> rules();
            case "EXIT" -> System.exit(0);
        }
        
    }
    
    //starts the chessGame | calls boardPanel
    private void startBoardPanel() {
        JFrame frame = new JFrame();
         
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
    
    //displays the rules, had help from ChatGPT
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
            
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(
            null,
            scrollPane,
            "Chess Rules & Help",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
}
