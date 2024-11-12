package command;

import gameworld.WorldInterface;
import java.io.IOException;
import java.util.Scanner;
import player.PlayerInterface;

/**
 * Command class for a player attempting to attack the target character.
 */
public class AttemptOnTargetCommand implements Command {
  private WorldInterface world;
  private Appendable out;
  private Scanner scanner;

  /**
   * Constructs an AttemptOnTargetCommand.
   *
   * @param world   the game world in which the player is interacting.
   * @param out     the output stream to which move command results will be
   *                appended.
   * @param scanner the input source from which the player's room selection is
   *                read.
   */
  public AttemptOnTargetCommand(WorldInterface world, Appendable out, Scanner scanner) {
    this.world = world;
    this.out = out;
    this.scanner = scanner;
  }

  @Override
  public void execute() throws IOException {
    PlayerInterface player = world.getTurn();
    boolean validItem = false;

    while (!validItem) {
      out.append("Enter the valid item name to use for the attack"
          + " or press Enter to use the best item available: ");
      String itemName = scanner.nextLine().trim();

      // Check if item name is valid or empty
      if (itemName.isEmpty() || player.getInventory().stream()
          .anyMatch(item -> item.getName().equalsIgnoreCase(itemName))) {
        String result = world.attemptOnTarget(player, itemName);
        out.append(result).append("\n");
        validItem = true; // End loop after a valid attempt
      } else {
        out.append("Invalid item name. Please enter a valid item from your inventory"
            + " or press Enter to use the best item.\n");
      }
    }
  }
}