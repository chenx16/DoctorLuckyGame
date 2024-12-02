package viewcontroller;

import player.HumanPlayer;

//Concrete command for moving a player
public class MoveCommand implements ViewCommand {
  private HumanPlayer player;
  private String direction;

  public MoveCommand(HumanPlayer player, String direction) {
    this.player = player;
    this.direction = direction;
  }

  @Override
  public void execute() {
//    player.move(direction);
  }
}