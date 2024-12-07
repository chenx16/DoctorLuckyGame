
package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * InfoPanel class is responsible for displaying game-related information, such
 * as whose turn it is, current game status, and controls.
 */
public class InfoPanel extends JPanel {
  private static final Color VERY_LIGHT_PINK = new Color(233, 205, 205);
  private static final Color VERY_LIGHT_GRAY = new Color(224, 224, 224);
  private JLabel keyInfoLabel;
  private JLabel turnInfoLabel;
  private JLabel targetInfoLabel;

  /**
   * Constructs an InfoPanel to display game information, turn details, and key
   * controls.
   */
  public InfoPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    // Turn Information Label
    turnInfoLabel = new JLabel("Turn Information: ", SwingConstants.CENTER);
    turnInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
    turnInfoLabel.setOpaque(true);
    turnInfoLabel.setBackground(VERY_LIGHT_GRAY);
    turnInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    add(turnInfoLabel);

    // Target Info Label
    targetInfoLabel = new JLabel("", SwingConstants.CENTER);
    targetInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
    targetInfoLabel.setOpaque(true);
    targetInfoLabel.setBackground(VERY_LIGHT_PINK);
    targetInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    add(targetInfoLabel);

    // Key Information Label
    keyInfoLabel = new JLabel(getKeyInfoText(), SwingConstants.CENTER);
    keyInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
    keyInfoLabel.setOpaque(true);
    keyInfoLabel.setBackground(Color.LIGHT_GRAY);
    keyInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    add(keyInfoLabel);

  }

  /**
   * Returns the key information text to display for the game.
   *
   * @return a formatted string containing key controls and player color
   *         information.
   */
  private String getKeyInfoText() {
    return "<html>" + "<b>Controls:</b> " + "M - Move | P - Pick Up | L - Look | A - Attack<br>"
        + "<b>Legend:</b> " + "<span style='color:green;'>● Human Player</span>  "
        + "<span style='color:blue;'>● Computer Player</span>  "
        + "<span style='color:red;'>● Target</span>" + "</html>";
  }

  /**
   * Updates the target information displayed on the panel.
   *
   * @param targetName the name of the target.
   * @param health     the health of the target.
   */
  public void updateTargetInfo(String targetName, int health) {
    String targetInfo = String.format(
        "<html><b>Target:</b> %s&nbsp;&nbsp;&nbsp;&nbsp;" + "<b>Health:</b> %d</html>", targetName,
        health);
    targetInfoLabel.setText(targetInfo);
  }

  /**
   * Updates the turn information displayed on the panel.
   *
   * @param num        the current turn number.
   * @param maxTurns   the maximum number of turns.
   * @param playerName the name of the current player.
   * @param roomName   the name of the current room.
   */
  public void updateTurnInfo(int num, int maxTurns, String playerName, String roomName) {
    String turnInfo = String.format(
        "<html><b>Turn:</b> %d/%d&nbsp;&nbsp;&nbsp;&nbsp;"
            + "<b>Player:</b> %s&nbsp;&nbsp;&nbsp;&nbsp;" + "<b>Location:</b> %s</html>",
        num, maxTurns, playerName, roomName);
    turnInfoLabel.setText(turnInfo);
  }

}
