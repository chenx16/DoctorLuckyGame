package view;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * InfoPanel class is responsible for displaying game-related information, such
 * as whose turn it is, current game status, etc.
 */
class InfoPanel extends JPanel {
  private JLabel infoLabel;

  public InfoPanel() {
    setLayout(new FlowLayout(FlowLayout.LEFT));
    infoLabel = new JLabel("Welcome to Doctor Lucky Game");
    add(infoLabel);
  }

  public void updateInfo(String info) {
    infoLabel.setText(info);
  }
}