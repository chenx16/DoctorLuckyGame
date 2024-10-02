package gameworld;

import java.util.List;

public interface RoomInterface {
  void addItem(Item item);

  List<ItemInterface> getItems();

  int getroomInd();

  String getName();

  List<RoomInterface> myListofNeighbors();

  int[] getCoordinateUpperLeft();

  int[] getCoordinateLowerRight();
}
