package view;

/**
 * Interface for the InfoPanel, providing methods to update game and turn
 * information.
 */
public interface InfoPanelInterface {

  /**
   * Updates general information displayed on the panel.
   *
   * @param info the information to display.
   */
  void updateInfo(String info);

  /**
   * Updates the turn information displayed on the panel.
   *
   * @param num        the current turn number.
   * @param playerName the name of the current player.
   * @param roomName   the name of the current room.
   */
  void updateTurnInfo(int num, String playerName, String roomName);
}
