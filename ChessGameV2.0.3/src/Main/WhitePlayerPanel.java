/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Jayden Tosul and Job Rotoava
 * 
 * this just display the bottom panel that displays the playerName & score
 */
public class WhitePlayerPanel extends JPanel {
    
    //objects instance
    private final BoardPanel board;
    
    //instance variables
    private String w_name;
    private int w_score;
    
    //integer sizes
    private final int panelWidth;
    private final int panelHeight = 60;
    private final int halfPanelHeight = panelHeight / 2;
    
    //Java BUttons
    private final JButton abortButton = new JButton("X");
    
    //font
    private final Font STATUS_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 30);
    private final Font KIC_FONT = new Font(Font.DIALOG, Font.PLAIN, 28);//kingInCheckFont
    private final Font SCORE_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    public WhitePlayerPanel(BoardPanel board) {
        this.board = board;
        this.panelWidth = board.panelWidth;
        setB_name();//sets playerName
        setB_score();//sets the player score
        
        setBackground(new Color(0x222222));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setFont(STATUS_FONT);
        
        //-- add JButton --
        setLayout(null);
        abortButton.setFocusable(false);
        abortButton.setMargin(new Insets(0, 0, 0, 0));
        abortButton.setBounds(panelWidth - 225, 10, 35, 35);
        add(abortButton);
        
        abortButton.addActionListener(e -> {
            board.setDidWhiteResign(BoardPanel.WHITE);
            board.abortGame();
        });
        
    }
    
    //getters for the playerSCore
    private void setB_score() {
        w_score = MenuController.getP1Score();
    }
    public void updateScore() {//when restart game
        w_score = MenuController.getP1Score();
    }
    //sets the player name
    private void setB_name() {
        w_name = capitalizeFirstLetter(MenuController.getP1Name());
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
    
    //gets Image
    private Image getProfileImage() {
        Image img = null;
        
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/Image/profile2.png"));
        } catch (IOException e) {
            System.err.println("IMAGE NOT FOUND: " + e.getMessage());
        }
        
        return img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//converts to Graphics2D for drawing
        Graphics2D g2 = (Graphics2D) g.create();
        
        //-------------------- drawing playerProfile -------------------------
        g2.setColor(Color.white);
        g2.drawImage(getProfileImage(), 5, 5, 50, 50, this);
        
        
        //--------------------- drawing the playerName --------------------
        g2.setColor(Color.WHITE);
        g2.drawString(w_name, 60, halfPanelHeight);
        g2.setFont(SCORE_FONT);
        
        switch (BoardPanel.WINNER) {
            case 0:
                g2.drawString("Score: " + w_score + " - 5", 60, panelHeight - 7);
                break;
            case 1:
                g2.drawString("Score: " + w_score + " + 10", 60, panelHeight - 7);
                break;
            default:
                g2.drawString("Score: " + w_score, 60, panelHeight - 7);
                break;
        }
        
        //----------------- drawing indicating for playerTurn -----------------
        //gettingt he fontMetrics
        g2.setFont(KIC_FONT);
        FontMetrics fmCheck = g2.getFontMetrics();
        int checkTextHeight = fmCheck.getAscent() - fmCheck.getDescent();
        
        //drawing the check indicator
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(panelWidth - 180, 10, 100, 35, 10, 7);
        //drawing 'check'
        g2.setColor(Color.black);
        g2.drawString("Check", panelWidth - 172, halfPanelHeight + (checkTextHeight / 2) - 3);
        
        if (board.currentColor == BoardPanel.WHITE) {
            g2.setColor(Color.GREEN);//turns green when it white to play
            g2.fillRoundRect(panelWidth - 70, 10, 60, 35, 10, 7);
            
            //display message if king is in check
            if (board.checkingP != null && board.checkingP.color == BoardPanel.BLACK) {
                //lighting the check indicator red when in check
                g2.setColor(Color.red);
                g2.fillRoundRect(panelWidth - 180, 10, 100, 35, 10, 7);
                
                g2.setColor(Color.white);
                g2.drawString("Check", panelWidth - 172, halfPanelHeight + (checkTextHeight / 2) - 3);
                
            }
            
        } else {
            g2.setColor(Color.GRAY);//turns gray when black to play
            g2.fillRoundRect(panelWidth - 70, 10, 60, 35, 10, 7);
        }
        
    }
}
