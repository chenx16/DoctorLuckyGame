package view;

import java.awt.Point;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * The {@code GameViewInterface} defines the required methods for a view
 * implementation in the MVC architecture of the game. It provides functionality
 * for interacting with the user and updating the graphical interface.
 */
public interface GameViewInterface {

  /**
   * Displays a message to the user.
   *
   * @param message the message to display.
   */
  void showMessage(String message);

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage the error message to display.
   */
  void showErrorMessage(String errorMessage);

  /**
   * Prompts the user to select a room and returns the index of the chosen room.
   *
   * @return the index of the selected room, or -1 if no selection was made.
   */
  int promptForRoom();

  /**
   * Prompts the user to select an item to pick up from the current room.
   *
   * @return the name of the selected item, or null if no selection was made.
   */
  String promptForItem();

  /**
   * Prompts the user to select an item from their inventory.
   *
   * @return the name of the selected item, or null if no selection was made.
   */
  String promptForInventoryItem();

  /**
   * Updates the turn information on the view.
   *
   * @param turnNum the current turn number.
   */
  void updateTurnInfo(int turnNum);

  /**
   * Retrieves the player at the specified graphical location.
   *
   * @param point the location to check.
   * @return the player at the specified location, or null if no player is found.
   */
  PlayerInterface getPlayerAtLocation(Point point);

  /**
   * Retrieves the room at the specified graphical location.
   *
   * @param point the location to check.
   * @return the room at the specified location, or null if no room is found.
   */
  RoomInterface getRoomAtLocation(Point point);

  /**
   * Switches the view to display the main game interface.
   */
  void switchToGameView();

  /**
   * Switches the view to display the panel for adding players.
   */
  void switchToAddPlayerPanel();

  /**
   * Returns the GamePanel.
   *
   * @return the GamePanel
   */
  GamePanel getGamePanel();

  /**
   * Returns the InfoPanel.
   *
   * @return the InfoPanel
   */
  InfoPanel getInfoPanel();
}
