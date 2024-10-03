package gameworld;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * A single game of Doctor Lucky.
 */

public interface WorldInterface {
  void loadFromFile(String filePath) throws IOException;

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
