package gameworld;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the game world. A room has coordinates, a name, and can
 * contain items. Rooms can also have neighbors, which are other rooms directly
 * adjacent.
 */
public class Room implements RoomInterface {
  private int[] coordinateUpperLeft;
  private int[] coordinateLowerRight;
  private String name;
  private List<ItemInterface> items;
  private List<RoomInterface> neighbors;
  private int roomInd;

  /**
   * Creates a new room with specified coordinates and name.
   *
   * @param coordinateUpperLeft  the coordinates of the upper-left corner of the
   *                             room
   * @param coordinateLowerRight the coordinates of the lower-right corner of the
   *                             room
   * @param name                 the name of the room
   * @param roomInd              the index of the room
   */
  public Room(int[] coordinateUpperLeft, int[] coordinateLowerRight, String name, int roomInd) {
    this.coordinateUpperLeft = coordinateUpperLeft;
    this.coordinateLowerRight = coordinateLowerRight;
    this.name = name;
    this.roomInd = roomInd;
    this.items = new ArrayList<>();
    this.neighbors = new ArrayList<>();
  }

  @Override
  public void addItem(ItemInterface item) {
    items.add(item);
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }

  @Override
  public int getRoomInd() {
    return roomInd;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void addNeighbor(RoomInterface room) {
    neighbors.add(room);
  }

  @Override
  public List<RoomInterface> myListofNeighbors() {
    return neighbors;
  }

  @Override
  public int[] getCoordinateUpperLeft() {
    return coordinateUpperLeft;
  }

  @Override
  public int[] getCoordinateLowerRight() {
    return coordinateLowerRight;
  }
}
