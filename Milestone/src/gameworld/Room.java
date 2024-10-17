package gameworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
   * @param items                the list of items in the room
   * @param neighbors            the ilist of neighbors of the room
   */
  public Room(int[] coordinateUpperLeft, int[] coordinateLowerRight, String name, int roomInd,
      List<ItemInterface> items, List<RoomInterface> neighbors) {
    this.coordinateUpperLeft = coordinateUpperLeft;
    this.coordinateLowerRight = coordinateLowerRight;
    this.name = name;
    this.roomInd = roomInd;
    this.items = new ArrayList<ItemInterface>();
    this.neighbors = new ArrayList<RoomInterface>();
  }

  @Override
  public void addItem(ItemInterface item) {
    items.add(item);
  }

  @Override
  public List<ItemInterface> getItems() {
    return new ArrayList<>(items);
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
    return new ArrayList<>(neighbors);
  }

  @Override
  public int[] getCoordinateUpperLeft() {
    return Arrays.copyOf(coordinateUpperLeft, coordinateUpperLeft.length);
  }

  @Override
  public int[] getCoordinateLowerRight() {
    return Arrays.copyOf(coordinateLowerRight, coordinateLowerRight.length);
  }

  /**
   * Generates a hash code for this room. The hash code is based on the room's
   * coordinates and name.
   *
   * @return the hash code for the room
   */
  @Override
  public int hashCode() {
    int prime = 31;
    int result = Objects.hash(name);
    result = prime * result + Arrays.hashCode(coordinateUpperLeft);
    result = prime * result + Arrays.hashCode(coordinateLowerRight);
    return result;
  }

  /**
   * Checks if this room is equal to another room. Two rooms are considered equal
   * if they have the same coordinates and the same name.
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
