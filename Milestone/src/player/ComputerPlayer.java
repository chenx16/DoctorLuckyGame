package player;

import item.ItemInterface;
import java.util.List;
import java.util.Random;
import room.RoomInterface;

/**
 * Represents a computer-controlled player in the game. The computer-controlled
 * player takes turns to explore the world.
 */
public class ComputerPlayer extends Player {

  private Random random;
  private boolean hasLookedAround;
  private boolean hasPickedUpItem;
  private boolean hasMoved;

  /**
   * Constructs a HumanPlayer with the given name, starting room, and maximum
   * number of items they can carry.
   *
   * @param name         the name of the player
   * @param startingRoom the room where the player starts
   * @param maxItems     the maximum number of items the player can carry
   */
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
   * @return A string describing the actions taken by the computer player.
   */
  public String takeTurn() {
    StringBuilder actionDescription = new StringBuilder();

    if (!hasLookedAround) {
      // 1. Look around
      actionDescription.append("look");
      hasLookedAround = true;

    } else if (!hasPickedUpItem) {
      // 2. Try to pick up an item if available
      String performPickup = tryPickUpItem();
      actionDescription.append(performPickup);
      if ("".equals(performPickup)) {
        // Move to next room if no items or inventory is full
        String performMove = moveToNextRoom();
        actionDescription.append(performMove);
      }

    } else if (!hasMoved) {
      // 3. Move to a neighboring room
      String performMove = moveToNextRoom();
      actionDescription.append(performMove);
    }

    return actionDescription.toString();
  }

  /**
   * Tries to pick up an item in the current room. If there is no item or
   * inventory is full, return false.
   *
   * @return The string for appending the action.
   */
  private String tryPickUpItem() {
    StringBuilder actionDescription = new StringBuilder();
    List<ItemInterface> itemsInRoom = getCurrentRoom().getItems();
    if (!itemsInRoom.isEmpty() && this.getInventory().size() < getMaxItems()) {
      ItemInterface item = itemsInRoom.get(0); // Pick the first item
      pickUpItem(item);
      actionDescription.append(getName()).append(" picked up ").append(item.getName()).append("\n");
      hasPickedUpItem = true;
      return actionDescription.toString();
    }
    return "";
  }

  /**
   * Moves to a random neighboring room and appends the action to the description.
   *
   * @return The string for appending the action.
   */
  private String moveToNextRoom() {
    StringBuilder actionDescription = new StringBuilder();
    List<RoomInterface> neighbors = this.getCurrentRoom().getListofNeighbors();
    if (!neighbors.isEmpty()) {
      RoomInterface nextRoom = neighbors.get(random.nextInt(neighbors.size()));
      moveTo(nextRoom);
      actionDescription.append(getName()).append(" moved to ").append(nextRoom.getName())
          .append("\n");
      hasMoved = true;
      resetTurn();
      return actionDescription.toString();
    } else {
      resetTurn();
      return "no neighboring room";
    }
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
