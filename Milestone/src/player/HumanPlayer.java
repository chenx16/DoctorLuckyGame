package player;

import gameworld.RoomInterface;

/**
 * Represents a human-controlled player in the game.
 */
public class HumanPlayer extends Player {

  public HumanPlayer(String name, RoomInterface startingRoom, int maxItems) {
    super(name, startingRoom, maxItems, false);
  }

  // Specific human-controlled behaviors can be added here.
}
