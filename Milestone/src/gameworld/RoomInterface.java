package gameworld;

import java.util.List;

public interface RoomInterface {
  void addItem(ItemInterface item);

  List<ItemInterface> getItems();

  int getRoomInd();

  String getName();

  void addNeighbor(RoomInterface room);

  List<RoomInterface> myListofNeighbors();

  int[] getCoordinateUpperLeft();

  int[] getCoordinateLowerRight();
}
