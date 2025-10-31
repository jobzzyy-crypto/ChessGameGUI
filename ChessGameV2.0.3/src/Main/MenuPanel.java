/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextField userName;
    private JPasswordField userPassword;
    private JTextField createPassword;
    private JButton createNewUserButton;
    private JButton loginButton;
    private final JDialog loginDialog = new JDialog();
    
    //JFrames
    public JFrame loginFrame;
    private JFrame frame;
    
    //JPanel
    private final JPanel buttonPanel;
    private JPanel loginPanel;
    
    //font
    private final Font bFont = new Font("Arial", Font.BOLD, 24);
    
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
        buttonPanel.setLayout(new GridLayout(5, 1, 0, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));
        
        //create ranked button
        JButton rankedButton = createButton("Play");
        rankedButton.setActionCommand("PLAY");
        rankedButton.addActionListener(mc);
        
        //create options button
        JButton optionButton = createButton("Rules");
        optionButton.setActionCommand("RULES");
        optionButton.addActionListener(mc);
        
        //create exit button
        JButton exitButton = createButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(mc);
        
        JButton createUserButton = createButton("Create User");
        createUserButton.setActionCommand("NEWUSER");
        createUserButton.addActionListener(mc);
        
        JButton quickPlayButton = createButton("Quick Play");
        quickPlayButton.setActionCommand("QUICK");
        quickPlayButton.addActionListener(mc);
        
        //add buttons to panel
        buttonPanel.add(rankedButton);
        buttonPanel.add(quickPlayButton);
        buttonPanel.add(createUserButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(exitButton);
        
        //add panel and Jlabel to frame
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        
    }
    
    //getters for userName & password
    public String getUserName() { 
        return userName.getText();
    }
    public String getPassword() {
        return userPassword.getText();
    }
    public String getCreatePassword() {//gets new password 4 new user
        return createPassword.getText();
    }
    
    //method for creating the buttons
    private JButton createButton(String name) {
        button = new JButton(name);
        button.setFont(bFont);
        button.setPreferredSize(new Dimension(200, 60));
        
        return button;
    }
    
    //displays the rule window
    public void rulesWindow(String text) {
        textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 240, 240));
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 600));
        
        JOptionPane.showMessageDialog(
            null,
            scrollPane,
            "Chess Rules & Help",
            JOptionPane.INFORMATION_MESSAGE
        );
        
    }
    
    //displays the loginPanel / had help from uncle ChatGPT
    public void login(int p_count) {
        loginFrame = new JFrame("Player " + p_count + " Login");
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding around

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Username Label ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        // --- Username Field ---
        userName = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(userName, gbc);

        // --- Password Label ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        // --- Password Field ---
        userPassword = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(userPassword, gbc);

        // --- Login Button ---
        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(mc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // --- Frame Setup ---
        loginFrame.add(loginPanel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
        
    }
    
    public void createNewUser() {
        loginFrame = new JFrame("Create User Login");
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding around

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Username Label ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        // --- Username Field ---
        userName = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(userName, gbc);

        // --- Password Label ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        // --- Password Field ---
        createPassword = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(createPassword, gbc);

        // --- Login Button ---
        createNewUserButton = new JButton("Create");
        createNewUserButton.setActionCommand("CREATE");
        createNewUserButton.addActionListener(mc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(createNewUserButton, gbc);

        // --- Frame Setup ---
        loginFrame.add(loginPanel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
    }
    
    public void disposeLoginFrame() {
        loginFrame.dispose();
    }
    
    //display wrong credentials
    public void wrongCredentials() {
        JOptionPane.showMessageDialog(loginDialog, "Invalid credentials!");
    }
    public void emptyPassword() {
        JOptionPane.showMessageDialog(loginDialog, """
                                                   Name or Password cannot be
                                                                    empty""");
    }
    
    public void passwordTooLong() {
        JOptionPane.showMessageDialog(loginDialog, "Name or Password too long");
    }
    
    //calls boardPanel to startGame
    public void startBoardPanel() {
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
    }
    
}