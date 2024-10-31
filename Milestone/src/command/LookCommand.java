package command;

import gameworld.WorldInterface;
import java.io.IOException;
import player.PlayerInterface;

/**
 * A command that allows the player to look around their current room. This
 * command provides details about the player's current location, including
 * visible items, neighboring rooms, and any characters present.
 */
public class LookCommand implements Command {
  private final WorldInterface world;
  private final PlayerInterface player;
  private final Appendable out;

  /**
   * Constructs a LookCommand with the specified world, player, and output stream.
   *
   * @param world  the game world in which the player is interacting.
   * @param player the player who is executing the look command.
   * @param out    the output stream to which the look command's result will be
   *               appended.
   */
  public LookCommand(WorldInterface world, PlayerInterface player, Appendable out) {
    this.world = world;
    this.player = player;
    this.out = out;
  }

  @Override
  public void execute() throws IOException {
    String result = world.turnHumanPlayer("look", -1, null);
    out.append(result).append("\n");
  }
}
