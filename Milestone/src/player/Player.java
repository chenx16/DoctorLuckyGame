package player;

import java.util.ArrayList;
import java.util.List;

import gameworld.ItemInterface;
import gameworld.RoomInterface;

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
   * Constructs a player with the given name, starting room, and maximum number of
   * items they can carry.
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
    return currentRoom.getName() + " contains: " + currentRoom.getItems();
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
    description.append("Player Name: ").append(name).append("\nCurrent Room: ")
        .append(currentRoom.getName()).append("\nInventory: ");

    if (inventory.isEmpty()) {
      description.append("No items");
    } else {
      for (ItemInterface item : inventory) {
        description.append("\n- ").append(item.getName());
      }
    }

    return description.toString();
  }

  @Override
  public String toString() {
    return "Player " + name + " in " + currentRoom.getName() + " with items: " + getInventory();
  }
}
