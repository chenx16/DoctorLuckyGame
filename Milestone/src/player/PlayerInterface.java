package player;

import item.ItemInterface;
import java.util.List;
import room.RoomInterface;

/**
 * Represents a player in the game. Players can move between rooms, pick up
 * items, and perform actions.
 */
public interface PlayerInterface {

  /**
   * Gets the name of the player.
   * 
   * @return the name of the player
   */
  String getName();

  /**
   * Gets the current room where the player is located.
   * 
   * @return the current room of the player
   */
  RoomInterface getCurrentRoom();

  /**
   * Moves the player to the specified room.
   * 
   * @param newRoom the room to move the player to
   */
  void moveTo(RoomInterface newRoom);

  /**
   * Allows the player to pick up an item from the current room.
   * 
   * @param item the item to pick up
   * @return a string describing pick up the item
   */
  String pickUpItem(ItemInterface item);

  /**
   * Provides information about the space around the player.
   * 
   * @return a string describing the surroundings of the player
   */
  String lookAround();

  /**
   * Gets the description of the player, including their name, current room, and
   * items in the inventory.
   * 
   * @return a description of the player
   */
  String getDescription();

  /**
   * Gets the inventory of the player.
   * 
   * @return a list of items that the player is carrying
   */
  List<ItemInterface> getInventory();

  /**
   * Checks if the player is computer-controlled.
   * 
   * @return true if the player is computer-controlled, false otherwise
   */
  Boolean getIsComputerControlled();

  /**
   * Gets the maximum number of items the player can carry.
   * 
   * @return the maximum number of items the player can carry
   */
  int getMaxItems();

  /**
   * Checks if the current player can see another player.
   *
   * @param otherPlayer the player to check visibility for
   * @return true if the current player can see the other player, false otherwise
   */
  boolean canSeePlayer(PlayerInterface otherPlayer);

  /**
   * Remove an used item from a player's inventory.
   *
   * @param item item needs to be removed from inventory
   */
  void removeItem(ItemInterface item);

  /**
   * Provides a detailed description of the current state of the player. The
   * description includes the player's name, current room, neighboring rooms,
   * inventory, and whether the player is controlled by a computer or a human.
   *
   * @return A string that describes the player's current state including: -
   *         Player name and type (human or computer-controlled) - Current room
   *         name and index - The number of neighboring rooms - The items
   *         currently in the player's inventory
   */
  String getViewDescription();
}
