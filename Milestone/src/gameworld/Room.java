package gameworld;

import java.util.ArrayList;
import java.util.List;

public class Room implements RoomInterface {
  private int[] coordinateUpperLeft;
  private int[] coordinateLowerRight;
  private String name;
  private List<ItemInterface> items;
  private List<RoomInterface> neighbors;
  private int roomInd; // Room index

  // Constructor
  public Room(int[] coordinateUpperLeft, int[] coordinateLowerRight, String name, int roomInd) {
    this.coordinateUpperLeft = coordinateUpperLeft;
    this.coordinateLowerRight = coordinateLowerRight;
    this.name = name;
    this.roomInd = roomInd; // Initialize room index
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
    return roomInd; // Return the room index
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
