package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;

//Concrete command for moving a player
public class MoveCommand implements ViewCommand {
  private WorldInterface world;
  private PlayerInterface player;
  private int roomIndex;

  public MoveCommand(WorldInterface world, PlayerInterface player, int roomIndex) {
    this.world = world;
    this.player = player;
    this.roomIndex = roomIndex;
  }

  @Override
  public String execute() {
    String result = world.turnHumanPlayer("move", roomIndex, null);
    System.out.println(result);
    return result;
  }
}