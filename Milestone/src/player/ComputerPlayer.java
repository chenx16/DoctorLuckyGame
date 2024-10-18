package player;

import java.util.List;
import java.util.Random;

import gameworld.ItemInterface;
import gameworld.RoomInterface;

/**
 * Represents a computer-controlled player in the game. The computer-controlled
 * player takes turns to explore the world.
 */
public class ComputerPlayer extends Player {

  private Random random;
  private boolean hasLookedAround;
  private boolean hasPickedUpItem;
  private boolean hasMoved;

  public ComputerPlayer(String name, RoomInterface startingRoom, int maxItems) {
    super(name, startingRoom, maxItems, true);
    this.random = new Random();
    resetTurn();
  }

  /**
   * The computer-controlled player's turn. It performs the following actions in
   * order: 1. Look around the room. 2. Pick up an item if there are items in the
   * room. 3. Move to a random neighboring room.
   *
   * @param world the game world the player is interacting with.
   * @return A string describing the actions taken by the computer player.
   */
  public String takeTurn() {
    StringBuilder actionDescription = new StringBuilder();

    if (!hasLookedAround) {
      // 1. Look around
      actionDescription.append(getName()).append(" looks around: ").append(lookAround())
          .append("\n");
      hasLookedAround = true;

    } else if (!hasPickedUpItem) {
      // 2. Pick up an item if there are items and space in inventory
      List<ItemInterface> itemsInRoom = getCurrentRoom().getItems();
      if (!itemsInRoom.isEmpty() && this.getInventory().size() < getMaxItems()) {
        ItemInterface item = itemsInRoom.get(0); // Pick the first item
        pickUpItem(item);
        actionDescription.append(getName()).append(" picked up ").append(item.getName())
            .append("\n");
        hasPickedUpItem = true;
      }

    } else if (!hasMoved) {
      // 3. Move to a neighboring room
      List<RoomInterface> neighbors = this.currentRoom.getListofNeighbors();
      if (!neighbors.isEmpty()) {
        RoomInterface nextRoom = neighbors.get(random.nextInt(neighbors.size()));
        moveTo(nextRoom);
        actionDescription.append(getName()).append(" moved to ").append(nextRoom.getName())
            .append("\n");
        hasMoved = true;
      }

      // Reset the actions for the next turn
      resetTurn();
    }

    return actionDescription.toString();
  }

  /**
   * Resets the action flags at the beginning of each new turn, allowing the
   * player to look around, pick up items, and move again.
   */
  private void resetTurn() {
    hasLookedAround = false;
    hasPickedUpItem = false;
    hasMoved = false;
  }
}
