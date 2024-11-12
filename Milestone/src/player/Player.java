package player;

import item.ItemInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import room.Room;
import room.RoomInterface;

/**
 * Represents a player in the game. Players can move between rooms, pick up
 * items, and perform actions.
 */
public abstract class Player implements PlayerInterface {
  protected String name;
  protected RoomInterface currentRoom;
  protected List<ItemInterface> inventory;
  protected int maxItems;
  protected boolean isComputerControlled;

  /**
   * Constructs a player with the given name, starting room, maximum number of
   * items they can carry, and if the player is controlled by the computer.
   *
   * @param name                 the name of the player
   * @param startingRoom         the room where the player starts
   * @param maxItems             the maximum number of items the player can carry
   * @param isComputerControlled if the player is controlled by the computer
   */
  public Player(String name, RoomInterface startingRoom, int maxItems,
      boolean isComputerControlled) {
    this.name = name;
    this.currentRoom = startingRoom;
    this.inventory = new ArrayList<>();
    this.maxItems = maxItems;
    this.isComputerControlled = isComputerControlled;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public RoomInterface getCurrentRoom() {
    return currentRoom;
  }

  @Override
  public void moveTo(RoomInterface newRoom) {
    this.currentRoom = newRoom;
  }

  @Override
  public String pickUpItem(ItemInterface item) {
    if (inventory.size() < maxItems) {
      inventory.add(item);
      currentRoom.removeItem(item);
      String result = String.format("Picked up %s. %d item(s) left to full inventory.\n",
          item.getName(), this.maxItems - this.inventory.size());
      return result.toString();
    } else {
      return "Cannot pick up item. Inventory is full.\n";
    }
  }

  @Override
  public String lookAround() {
    return "\n" + currentRoom.getName() + " contains: " + currentRoom.getItems();
  }

  @Override
  public List<ItemInterface> getInventory() {
    return new ArrayList<>(inventory); // Return a copy to avoid modification
  }

  @Override
  public Boolean getIsComputerControlled() {
    return isComputerControlled;
  }

  @Override
  public int getMaxItems() {
    return maxItems;
  }

  @Override
  public String getDescription() {
    StringBuilder description = new StringBuilder();
    int neighborCount = currentRoom.getListofNeighbors().size();
    description.append("It's " + name + "'s turn.").append("\nYou are in: ")
        .append(currentRoom.getName()).append("\nThere are ").append(neighborCount)
        .append(" neighboring rooms.").append("\nInventory: ");

    if (inventory.isEmpty()) {
      description.append("No items");
    } else {
      for (ItemInterface item : inventory) {
        description.append("\n- ").append(item.toString());
      }
    }

    return description.toString();
  }

  @Override
  public boolean canSeePlayer(PlayerInterface anotherPlayer) {
    RoomInterface roomA = this.getCurrentRoom();
    RoomInterface roomB = anotherPlayer.getCurrentRoom();

    // Check if players are in the same room
    if (roomA.getRoomInd() == (roomB.getRoomInd())) {
      return true;
    }

    // Check if Player B is in a neighboring space of Player A's room and the room
    // is not sealed
    if (roomA.getListofNeighbors().contains(roomB) && !roomB.isSealed()) {
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return "Player " + name + " in " + currentRoom.getName() + " with items: " + getInventory();
  }

  /**
   * Generates a hash code for this player.
   *
   * @return the hash code for the player
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, maxItems, isComputerControlled);
  }

  /**
   * Checks if this player is equal to another player. Two players are considered
   * equal if they have the same name, maximum number of items, and if the player
   * is controlled by the computer
   *
   * @param obj the object to compare with
   * @return true if the rooms are having same hashCode, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Room)) {
      return false;
    }
    Room other = (Room) obj;
    return this.hashCode() == other.hashCode();
  }
}
