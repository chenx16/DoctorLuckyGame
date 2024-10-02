package gameworld;

import java.util.ArrayList;
import java.util.List;

public class Room implements RoomInterface {
  private int[] coordinateUpperLeft;
  private int[] coordinateLowerRight;
  private String name;
  private List<ItemInterface> items;
  private List<RoomInterface> neighbors;
  private int roomInd;

  public Room(int[] coordinateUpperLeft, int[] coordinateLowerRight, String name) {
    this.coordinateUpperLeft = coordinateUpperLeft;
    this.coordinateLowerRight = coordinateLowerRight;
    this.name = name;
    this.items = new ArrayList<>();
    this.neighbors = new ArrayList<>();
  }

  @Override
  public void addItem(Item item) {
    items.add(item);
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }

  @Override
  public int getroomInd() {
    return roomInd;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<RoomInterface> myListofNeighbors() {
    return neighbors;
  }

  public int[] getCoordinateUpperLeft() {
    return coordinateUpperLeft;
  }

  public int[] getCoordinateLowerRight() {
    return coordinateLowerRight;
  }
}
