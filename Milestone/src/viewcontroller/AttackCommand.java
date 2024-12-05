package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;
import view.InfoPanel;

public class AttackCommand implements ViewCommand {
  private WorldInterface world;
  private PlayerInterface player;
  private InfoPanel infoPanel;
  private String itemName;

  public AttackCommand(WorldInterface world, PlayerInterface player, String itemName) {
    this.world = world;
    this.player = player;
    this.itemName = itemName;
  }

  @Override
  public String execute() {
    if (player.getCurrentRoom().equals(world.getTargetCharacter().getCurrentRoom())) {
      // Player attempts an attack on the target
      String result = world.turnHumanPlayer("attempt", -1, itemName);
//      infoPanel.updateInfo(result);
      return result;
    } else {
      return "The target is not in your current room. You cannot attack.";
//      infoPanel.updateInfo("The target is not in your current room. You cannot attack.");
    }
  }
}
