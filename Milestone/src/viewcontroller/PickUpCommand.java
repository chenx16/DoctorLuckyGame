package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;

public class PickUpCommand implements ViewCommand {
  private WorldInterface world;
  private PlayerInterface player;
  private String itemName;

  public PickUpCommand(WorldInterface world, PlayerInterface player, String itemName) {
    this.world = world;
    this.player = player;
    this.itemName = itemName;
  }

  @Override
  public String execute() {
    String result = world.turnHumanPlayer("pickup", player.getCurrentRoom().getRoomInd(), itemName);
    System.out.println(result);
    return result;
  }
}