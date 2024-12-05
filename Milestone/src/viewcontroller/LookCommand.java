package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;

public class LookCommand implements ViewCommand {
  private final WorldInterface world;
  private final PlayerInterface player;

  public LookCommand(WorldInterface world, PlayerInterface player) {
    this.world = world;
    this.player = player;
  }

  @Override
  public String execute() {
    // Fetch and display information about the player's surroundings

    int currRoomInd = world.getTurn().getCurrentRoom().getRoomInd();
    String surroundingsInfo = world.turnHumanPlayer("look", currRoomInd, null);
    return surroundingsInfo;

  }
}
