package command;

import gameworld.WorldInterface;
import item.ItemInterface;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import player.PlayerInterface;

/**
 * A command that allows the player to pick up an item from their current room.
 * This command lists available items in the room, prompts the player to choose
 * one, and then attempts to pick up the selected item.
 */
public class PickUpCommand implements Command {
  private final WorldInterface world;
  private final PlayerInterface player;
  private final Appendable out;
  private final Scanner scanner;

  /**
   * Constructs a PickUpCommand with the specified world, player, output stream,
   * and scanner.
   *
   * @param world   the game world in which the player is interacting.
   * @param player  the player who is executing the pick-up command.
   * @param out     the output stream to which pick-up command results will be
   *                appended.
   * @param scanner the input source from which the player's item choice is read.
   */
  public PickUpCommand(WorldInterface world, PlayerInterface player, Appendable out,
      Scanner scanner) {
    this.world = world;
    this.player = player;
    this.out = out;
    this.scanner = scanner;
  }

  @Override
  public void execute() throws IOException {
    List<ItemInterface> itemsInRoom = player.getCurrentRoom().getItems();
    if (itemsInRoom.isEmpty()) {
      out.append("No items to pick up in this room.\n");
      return;
    }

    // Display items in the room
    displayItemsInRoom(player);

    boolean validItemPicked = false;

    // Continuously prompt until a valid item name is entered
    while (!validItemPicked) {
      out.append("Enter the item name to pick up: ");
      String itemName = scanner.nextLine().trim();

      // Search for the item in the room
      ItemInterface itemToPick = itemsInRoom.stream()
          .filter(item -> item.getName().equalsIgnoreCase(itemName)).findFirst().orElse(null);

      if (itemToPick != null) {
        // Execute the pick-up and break out of the loop if item is valid
        String result = world.turnHumanPlayer("pickup", -1, itemToPick.getName());
        out.append(result).append("\n");
        validItemPicked = true;
      } else {
        // Prompt the player again if the item name is invalid
        out.append("Invalid item name. Please enter full name of a valid item from the list.\n");
      }
    }

  }

  /**
   * Displays the items in the room for the human player.
   */
  private void displayItemsInRoom(PlayerInterface player) throws IOException {
    List<ItemInterface> items = player.getCurrentRoom().getItems();
    out.append("Items in the room:\n");
    for (ItemInterface item : items) {
      out.append(item.getName() + " (Damage: " + item.getDamage() + ")\n");
    }
  }
}
