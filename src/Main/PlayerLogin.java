package Main;

import DataBase.PlayerDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Job Rotoava and Jayden Tosul 
 */
public class PlayerLogin extends JDialog {
    
    //instance objects
    private PlayerDB playerDB = new PlayerDB();
    
    //JTextFields
    private JTextField player1Field; //Text fields where players type their names
    private JTextField player2Field; //Text fields where players type their names
    
    // Variables to store the entered names
    private String player1Name;
    private String player2Name;
    private boolean namesSet = false;

    public PlayerLogin(JFrame parent) {
        super(parent, "Enter Player Names", true);
        initializeComponents();
        setupLayout();
        setProperties();
    }

    private void initializeComponents() {
        player1Field = new JTextField(15);
        player2Field = new JTextField(15);
        
        // Set default names
        player1Field.setText("Player1");
        player2Field.setText("Player2");
    }
    
    //deep-seek
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Player 1 (White):"));
        inputPanel.add(player1Field);
        inputPanel.add(new JLabel("Player 2 (Black):"));
        inputPanel.add(player2Field);

        // Button panel
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new StartButtonListener());

        mainPanel.add(new JLabel("Enter Player Names:"), BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(startButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setProperties() {
        setSize(300, 200);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            player1Name = player1Field.getText().trim();
            player2Name = player2Field.getText().trim();

            if (player1Name.isEmpty()) {
                player1Name = "Player 1";
            }
            if (player2Name.isEmpty()) {
                player2Name = "Player 2";
            }

            namesSet = true;
            dispose();
        }
    }

    public boolean areNamesSet() {
        return namesSet;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}