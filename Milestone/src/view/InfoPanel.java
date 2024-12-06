package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * InfoPanel class is responsible for displaying game-related information, such
 * as whose turn it is, current game status, etc.
 */

public class InfoPanel extends JPanel implements InfoPanelInterface {
  private JLabel infoLabel;
  private JLabel turnLabel;

  /**
   * Constructs an InfoPanel to display game information and turn details.
   */
  public InfoPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    infoLabel = new JLabel("Welcome to Doctor Lucky Game");
    turnLabel = new JLabel("Turn Information: ");

    add(infoLabel);
    add(turnLabel);
  }

  @Override
  public void updateInfo(String info) {
    infoLabel.setText(info);
  }

  @Override
  public void updateTurnInfo(int num, String playerName, String roomName) {
    turnLabel.setText("Current Turn: " + num + " " + playerName + " - Location: " + roomName);
  }

}
