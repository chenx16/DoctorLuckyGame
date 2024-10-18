package player;

import gameworld.RoomInterface;

/**
 * Represents a human-controlled player in the game.
 */
public class HumanPlayer extends Player {
  /**
   * Constructs a HumanPlayer with the given name, starting room, and maximum
   * number of items they can carry.
   *
   * @param name         the name of the player
   * @param startingRoom the room where the player starts
   * @param maxItems     the maximum number of items the player can carry
   */
  public HumanPlayer(String name, RoomInterface startingRoom, int maxItems) {
    super(name, startingRoom, maxItems, false);
  }

  // Specific human-controlled behaviors can be added here.
}
