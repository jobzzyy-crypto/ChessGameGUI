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
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Jayden Tosul and JOb Rotoava
 */
public class MenuPanel extends JPanel {
    
    //object instance
    private final MenuController mc = new MenuController(this);
    
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
        rankedButton.setActionCommand("PLAY");
        rankedButton.addActionListener(mc);
        
        //create options button
        JButton optionButton = createButton("Rules");
        optionButton.setActionCommand("RULES");
        rankedButton.addActionListener(mc);
        
        //create exit button
        JButton exitButton = createButton("Exit");
        optionButton.setActionCommand("EXIT");
        rankedButton.addActionListener(mc);
        
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
    
    
    
}
