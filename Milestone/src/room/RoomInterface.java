package room;

import coordinate.CoordinateInterface;
import item.ItemInterface;
import java.util.List;
import player.PlayerInterface;

/**
 * Represents a room in the game world. A room has coordinates, a name, items,
 * and can have neighboring rooms.
 */
public interface RoomInterface {
  /**
   * Adds an item to the room.
   *
   * @param item the item to be added to the room
   */
  void addItem(ItemInterface item);

  /**
   * Removes an item to the room.
   *
   * @param item the item to be removed to the room
   */
  void removeItem(ItemInterface item);

  /**
   * Returns the list of items in the room.
   *
   * @return the list of items
   */
  List<ItemInterface> getItems();

  /**
   * Gets the index of the room.
   * 
   * @return the index of the room
   */
  int getRoomInd();

  /**
   * Gets the name of the room.
   * 
   * @return the name of the room
   */
  String getName();

  /**
   * Adds a neighboring room to this room.
   *
   * @param room the room to be added as a neighbor
   */
  void addNeighbor(RoomInterface room);

  /**
   * Returns the list of neighboring rooms.
   *
   * @return the list of neighbors
   */
  List<RoomInterface> getListofNeighbors();

  /**
   * /** Gets the upper-left coordinate of the room.
   * 
   * @return the upper-left coordinate
   */
  CoordinateInterface getCoordinateUpperLeft();

  /**
   * Gets the lower-right coordinate of the room.
   * 
   * @return the lower-right coordinate
   */
  CoordinateInterface getCoordinateLowerRight();

  /**
   * Adds a player to the room.
   *
   * @param player the player to be added to the room
   */
  void addPlayer(PlayerInterface player);

  /**
   * Removes a player to the room.
   *
   * @param player the player to be removed to the room
   */
  void removePlayer(PlayerInterface player);

  /**
   * Returns the list of players in room.
   *
   * @return the list of players in room
   */
  List<PlayerInterface> getPlayers();

  /**
   * Returns the description of the whole room.
   *
   * @return the description of the whole room
   */
  String getRoomDescription();

  /**
   * Returns the description of the whole room.
   *
   * @return the description of the whole room and information about it's visible
   *         neighbors
   */
  String getRoomDescriptionVisible();

  /**
   * Returns a list of visible neighboring rooms. If this room is sealed, it will
   * not include itself as visible. Neighbors that are sealed are also excluded.
   *
   * @return a list of visible neighboring rooms
   */
  List<RoomInterface> getVisibleNeighbors();

  /**
   * Seal the room.
   * 
   */
  void setSealed();

  /**
   * Unseals the room, allowing it to be seen by other rooms' neighboring checks.
   */
  void unseal();

  /**
   * Checks if the room is sealed.
   * 
   * @return true if the room is sealed, false otherwise.
   */
  boolean isSealed();
}
