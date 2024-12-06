package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;

/**
 * The {@code PickUpCommand} class represents the command to pick up an item
 * from the player's current room.
 */

public class PickUpCommand implements ViewCommand {
  private WorldInterface world;
  private PlayerInterface player;
  private String itemName;

  /**
   * Constructs a {@code PickUpCommand} object.
   *
   * @param world    the game world model.
   * @param itemName the name of the item to pick up.
   */
  public PickUpCommand(WorldInterface world, String itemName) {
    this.world = world;
    this.player = world.getTurn();
    this.itemName = itemName;
  }

  @Override
  public String execute() {
    String result = world.turnHumanPlayer("pickup", player.getCurrentRoom().getRoomInd(), itemName);
    System.out.println(result);
    return result;
  }
}