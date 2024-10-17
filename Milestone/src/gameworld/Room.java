package gameworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room in the game world. A room has coordinates, a name, and can
 * contain items. Rooms can also have neighbors, which are other rooms directly
 * adjacent.
 */
public class Room implements RoomInterface {

  private CoordinateInterface upperLeft;
  private CoordinateInterface lowerRight;
  private String name;
  private List<ItemInterface> items;
  private List<RoomInterface> neighbors;
  private int roomInd;

  /**
   * Creates a new room with specified coordinates and name.
   *
   * @param upperLeft  the upper-left coordinate of the room
   * @param lowerRight the lower-right coordinate of the room
   * @param name       the name of the room
   * @param roomInd    the index of the room
   * @param items      the list of items in the room
   * @param neighbors  the list of neighbors of the room
   */
  public Room(CoordinateInterface upperLeft, CoordinateInterface lowerRight, String name,
      int roomInd, List<ItemInterface> items, List<RoomInterface> neighbors) {
    this.upperLeft = upperLeft;
    this.lowerRight = lowerRight;
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
  public CoordinateInterface getCoordinateUpperLeft() {
    return upperLeft.copy();
  }

  @Override
  public CoordinateInterface getCoordinateLowerRight() {
    return lowerRight.copy();
  }

  /**
   * Generates a hash code for this room. The hash code is based on the room's
   * coordinates and name.
   *
   * @return the hash code for the room
   */
  @Override
  public int hashCode() {
    return Objects.hash(upperLeft, lowerRight, name, roomInd);
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

  @Override
  public String toString() {
    return "Room{name=" + name + ", upperLeft=" + upperLeft + ", lowerRight=" + lowerRight + '}';
  }

}
