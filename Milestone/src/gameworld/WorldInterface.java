package gameworld;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A single game of Doctor Lucky.
 */

public interface WorldInterface {
  void loadFromFile(String filePath);

  List<RoomInterface> getNeighbors(RoomInterface room);

  String getSpaceInfo(RoomInterface room);

  void moveTargetCharacter();

  BufferedImage generateWorldMap();

  Graphics getGraphics();

  String getName();

  int[][] getRowAndCol();

  TargetInterface getTargetCharacter();

  List<RoomInterface> getRooms();

  List<ItemInterface> getItems();
}
