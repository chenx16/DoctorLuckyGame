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
   * Determines if this player can see anotherPlayer. This player can see
   * anotherPlayer if they are in the same room, or if anotherPlayer is in one of
   * the neighboring spaces of the room this player is in, provided
   * anotherPlayer's room is not sealed.
   *
   * @param anotherPlayer the player who is checking for visibility.
   * @return true if this player can see anotherPlayer, false otherwise.
   */
  boolean canSeePlayer(PlayerInterface anotherPlayer);
}
