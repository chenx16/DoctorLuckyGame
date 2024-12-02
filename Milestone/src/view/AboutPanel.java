package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * AboutPanel class that represents the About screen for the game. It contains a
 * welcome message, description, and a "Start Game" button.
 */
public class AboutPanel extends JPanel {

  private JButton startGameButton;

  public AboutPanel() {
    // Set the layout and border for the panel
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    // Create the welcome label
    JLabel welcomeLabel = new JLabel("Welcome to Doctor Lucky!");
    welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

    // Create the description label
    JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>"
        + "In Doctor Lucky, players attempt to track and kill Doctor Lucky while outsmarting their opponents. "
        + "Can you succeed before anyone else?</div></html>");
    descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);

    // Create the author label
    JLabel authorLabel = new JLabel("Created by Xinlai Chen");
    authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
    authorLabel.setAlignmentX(CENTER_ALIGNMENT);

    // Create the start game button
    startGameButton = new JButton("Start Game");
    startGameButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
    startGameButton.setAlignmentX(CENTER_ALIGNMENT);

    // Add the components to the panel
    add(Box.createVerticalGlue());
    add(welcomeLabel);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(descriptionLabel);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(authorLabel);
    add(Box.createRigidArea(new Dimension(0, 40)));
    add(startGameButton);
    add(Box.createVerticalGlue());
  }

  // Method to register an ActionListener for the start game button
  public void registerActionListener(ActionListener listener) {
    startGameButton.addActionListener(listener);
  }
}
