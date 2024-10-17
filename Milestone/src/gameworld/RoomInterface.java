package gameworld;

import java.util.List;

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
  List<RoomInterface> myListofNeighbors();

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
}
