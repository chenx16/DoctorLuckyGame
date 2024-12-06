package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * InfoPanel class is responsible for displaying game-related information, such
 * as whose turn it is, current game status, etc.
 */

public class InfoPanel extends JPanel {
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

  /**
   * Updates general information displayed on the panel.
   *
   * @param info the information to display.
   */
  public void updateInfo(String info) {
    infoLabel.setText(info);
  }

  /**
   * Updates the turn information displayed on the panel.
   *
   * @param num        the current turn number.
   * @param playerName the name of the current player.
   * @param roomName   the name of the current room.
   */
  public void updateTurnInfo(int num, String playerName, String roomName) {
    turnLabel.setText("Current Turn: " + num + " " + playerName + " - Location: " + roomName);
  }

}
