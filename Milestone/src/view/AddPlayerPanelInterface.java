package view;

import room.RoomInterface;

/**
 * Interface for the {@code AddPlayerPanel}, defining methods for managing the
 * addition of human and computer players to the game.
 */
public interface AddPlayerPanelInterface {

  /**
   * Adds a human player to the game.
   */
  void addHumanPlayer();

  /**
   * Adds a computer-controlled player to the game.
   */
  void addComputerPlayer();

  /**
   * Opens a dialog to select a starting room for a player.
   *
   * @return the selected {@code RoomInterface}, or {@code null} if no selection
   *         was made.
   */
  RoomInterface selectRoomDialog();

  /**
   * Selects a random starting room for a player.
   *
   * @return the randomly selected {@code RoomInterface}.
   */
  RoomInterface selectRandomRoom();

  /**
   * Updates the status label to reflect the number of players added.
   */
  void updateStatus();
}
