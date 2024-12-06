package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;

/**
 * The {@code AttackCommand} class represents the command for a player to
 * attempt an attack on the target character using a specific item.
 */
public class AttackCommand implements ViewCommand {
  private WorldInterface world;
  private PlayerInterface player;
  private String itemName;

  /**
   * Constructs an {@code AttackCommand} object.
   *
   * @param world    the game world model.
   * @param itemName the name of the item to use for the attack.
   */
  public AttackCommand(WorldInterface world, String itemName) {
    this.world = world;
    this.player = world.getTurn();
    this.itemName = itemName;
  }

  @Override
  public String execute() {
    if (player.getCurrentRoom().equals(world.getTargetCharacter().getCurrentRoom())) {
      // Player attempts an attack on the target
      String result = world.turnHumanPlayer("attempt", -1, itemName);
      return result;
    } else {
      return "The target is not in your current room. You cannot attack.";
    }
  }
}
