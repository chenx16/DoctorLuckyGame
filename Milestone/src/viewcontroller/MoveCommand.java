package viewcontroller;

import gameworld.WorldInterface;

/**
 * The {@code MoveCommand} class represents the command to move a player to a
 * different room in the game world.
 */
public class MoveCommand implements ViewCommand {
  private WorldInterface world;
  private int roomIndex;

  /**
   * Constructs a {@code MoveCommand} object.
   *
   * @param world     the game world model.
   * @param roomIndex the index of the room to move to.
   */
  public MoveCommand(WorldInterface world, int roomIndex) {
    this.world = world;
    this.roomIndex = roomIndex;
  }

  @Override
  public String execute() {
    String result = world.turnHumanPlayer("move", roomIndex, null);
    return result;
  }
}