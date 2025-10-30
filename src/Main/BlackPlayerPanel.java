/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Job Rotoava and Jayden Tosul
 * 
 * this just display the top panel that displays the playerName & score
 */

public class BlackPlayerPanel extends JPanel {
    
    //objects instance
    private final BoardPanel board;
    
    //instance variables
    private final String b_name = MenuController.getP2Name();
    private final int b_score = MenuController.getP2Score();
    
    //integer sizes
    private final int panelWidth;
    private final int panelHeight = 60;
    private final int halfPanelHeight = panelHeight / 2;
    private final int fontSize = 32;
    
    //Java BUttons
    private final JButton abortButton = new JButton("X");
    
    //font
    private final Font STATUS_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, fontSize);
    private final Font KIC_FONT = new Font(Font.DIALOG, Font.PLAIN, 28);//kingInCheckFont
    private final Font SCORE_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 14);

    public BlackPlayerPanel(BoardPanel board) {
        this.board = board;
        this.panelWidth = board.panelWidth;
        
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
            board.setDidWhiteResign(BoardPanel.BLACK);
            board.abortGame();
        });
        
    }
    
    //gets Image
    private Image getProfileImage() {
        Image img = null;
        
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/Image/profile.png"));
        } catch (IOException e) {
            System.err.println("IMAGE NOT FOUND: " + e.getMessage());
        }
        
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//converts to Graphics2D for drawing
        Graphics2D g2 = (Graphics2D) g.create();
        
        //--------------------- drawing playerProfile -------------------------
        g2.setColor(Color.white);
        g2.drawImage(getProfileImage(), 5, 5, 50, 50, this);
        
        
        //-------------------- drawing the playerName ------------------------
        g2.setColor(Color.WHITE);
        g2.drawString(b_name, 60, halfPanelHeight);
        g2.setFont(SCORE_FONT);
        g2.drawString("Score: " + b_score, 60, panelHeight - 7);
        
        
        //---------------- drawing indicating for playerTurn -----------------
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
        
        if (board.currentColor == BoardPanel.BLACK) {
            g2.setColor(Color.GREEN);//turns green when it white to play
            g2.fillRoundRect(panelWidth - 70, 10, 60, 35, 10, 7);
            
            //display message if king is in check
            if (board.checkingP != null && board.checkingP.color == BoardPanel.WHITE) {
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

