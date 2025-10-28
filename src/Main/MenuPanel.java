/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author Jayden Tosul and JOb Rotoava
 */
public class MenuPanel extends JPanel {
    
    //JLabels & JButtons
    private final JLabel titleLabel;
    private JButton button;
    
    //font
    private final Font bFont = new Font("Arial", Font.BOLD, 24);
    
    //JPanel for menu buttons
    private final JPanel buttonPanel;
    
    public MenuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        //draw title
        titleLabel = new JLabel("ChessGameGUI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        //buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));
        
        //create ranked button
        JButton rankedButton = createButton("Play");
        rankedButton.addActionListener(e -> startBoardPanel());
        
        //create options button
        JButton optionButton = createButton("Rules");
        optionButton.addActionListener(e -> rules());
        
        //create exit button
        JButton exitButton = createButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        
        //add buttons to panel
        buttonPanel.add(rankedButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(exitButton);
        
        //add panel and jlabel to frame
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        
    }
    
    //method for creating the buttons
    private JButton createButton(String name) {
        button = new JButton(name);
        button.setFont(bFont);
        button.setPreferredSize(new Dimension(200, 60));
        
        return button;
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
