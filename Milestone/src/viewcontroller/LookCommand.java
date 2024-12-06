package viewcontroller;

import gameworld.WorldInterface;

/**
 * The {@code LookCommand} class represents the command for a player to look
 * around their current room and observe their surroundings.
 */
public class LookCommand implements ViewCommand {
  private WorldInterface world;

  /**
   * Constructs a {@code LookCommand} object.
   *
   * @param world the game world model.
   */
  public LookCommand(WorldInterface world) {
    this.world = world;
  }

  @Override
  public String execute() {
    // Fetch and display information about the player's surroundings

    int currRoomInd = world.getTurn().getCurrentRoom().getRoomInd();
    String surroundingsInfo = world.turnHumanPlayer("look", currRoomInd, null);
    return surroundingsInfo;

  }
}
