package gameworld;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Represents the game world consisting of rooms, items, and a target character.
 * The world can be loaded from a file, and a graphical map of the world can be
 * generated.
 */
public interface WorldInterface {
  /**
   * Loads the world from the specified file.
   * 
   * @param filePath the path of the file to load the world from
   * @throws IOException if an error occurs while reading the file
   */
  void loadFromFile(String filePath) throws IOException;

  /**
   * Returns the neighbors of the specified room.
   * 
   * @param room the room whose neighbors are to be returned
   * @return the list of neighboring rooms
   */
  List<RoomInterface> getNeighbors(RoomInterface room);

  /**
   * Provides information about a specified space in the world, including its
   * items and neighboring spaces.
   * 
   * @param room the room whose information is to be displayed
   * @return a string containing the room's name, its items, and visible
   *         neighboring spaces
   */
  String getSpaceInfo(RoomInterface room);

  /**
   * Moves the target character to the next room in the world.
   */
  void moveTargetCharacter();

  /**
   * Generates a graphical representation of the world map as a BufferedImage. The
   * image shows the layout of the rooms and their names.
   * 
   * @return a BufferedImage representing the world map
   * @throws IOException if an error occurs while saving the image file
   */
  BufferedImage generateWorldMap() throws IOException;

  /**
   * Returns the graphical object used for rendering the world.
   * 
   * @return the Graphics object for rendering the world map
   * @throws IOException if an error occurs while saving the world map image file
   */
  Graphics getGraphics() throws IOException;

  /**
   * Returns the name of the world.
   * 
   * @return the name of the world
   */
  String getName();

  /**
   * Returns the dimensions of the world in terms of rows and columns.
   * 
   * @return a 2D array with the number of rows and columns of the world
   */
  int[][] getRowAndCol();

  /**
   * Returns the target character in the world.
   * 
   * @return the target character
   */
  TargetInterface getTargetCharacter();

  /**
   * Returns the list of rooms in the world.
   * 
   * @return the list of rooms
   */
  List<RoomInterface> getRooms();

  /**
   * Returns the list of items in the world.
   * 
   * @return the list of items
   */
  List<ItemInterface> getItems();
}
