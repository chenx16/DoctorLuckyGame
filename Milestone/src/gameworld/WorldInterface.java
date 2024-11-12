package gameworld;

import item.ItemInterface;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import pet.PetInterface;
import player.PlayerInterface;
import room.RoomInterface;
import target.TargetInterface;

/**
 * Represents the game world consisting of rooms, items, and a target character.
 * The world can be loaded from a file, and a graphical map of the world can be
 * generated.
 */
public interface WorldInterface {

  /**
   * Loads the world from the specified file or string.
   * 
   * @param source of the file or string to load the world from
   * @throws IOException if an error occurs while reading the file
   */
  void loadFromFile(Readable source) throws IOException;

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
   * Provides information about a specified space in the world, including its
   * items and neighboring spaces.
   * 
   * @param player the room whose information is to be displayed based on player
   * @return a string containing the room's name, its items, and visible
   *         neighboring spaces
   */
  String getPlayerSpaceInfo(PlayerInterface player);

  /**
   * Moves the target character to the next room in the world.
   */
  void moveTargetCharacter();

  /**
   * Generates a graphical representation of the world map as a BufferedImage. The
   * image shows the layout of the rooms and their names.
   * 
   * @param fileDir file directory path
   * @return a BufferedImage representing the world map
   * @throws IOException if an error occurs while saving the image file
   */
  BufferedImage generateWorldMap(String fileDir) throws IOException;

  /**
   * Returns the graphical object used for rendering the world.
   * 
   * @param fileDir file directory path
   * @return the Graphics object for rendering the world map
   * @throws IOException if an error occurs while saving the world map image file
   */
  Graphics getGraphics(String fileDir) throws IOException;

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
   * Returns the pet in the world.
   * 
   * @return the pet
   */
  PetInterface getPet();

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

  /**
   * Adds a player to the world and places them in the specified room.
   *
   * @param player  The player to be added to the world.
   * @param roomInd The index of the room where the player will start.
   * @throws IllegalArgumentException if the player is null or if the room index
   *                                  is invalid.
   */
  void addPlayer(PlayerInterface player, int roomInd);

  /**
   * Returns a list of all players currently in the world.
   *
   * @return A list of players.
   */
  List<PlayerInterface> getPlayers();

  /**
   * Gets the player whose turn it is.
   * 
   * @return The player who will take the next turn.
   */
  PlayerInterface getTurn();

  /**
   * Handles the turn for a human player, allowing them to take an action based on
   * input.
   *
   * @param action   The action the player wants to perform (e.g., "look",
   *                 "pickup", "move").
   * @param roomInd  The index of the room to move to (if the action is "move").
   * @param itemName The name of the item to pick up (if the action is "pickup").
   * @return human player turn result
   */
  String turnHumanPlayer(String action, int roomInd, String itemName);

  /**
   * Handles the turn for a computer-controlled player, automatically performing
   * actions.
   * 
   * @return computer player turn result
   */
  String turnComputerPlayer();

  /**
   * Makes the pet wander through the rooms of the world using a depth-first
   * traversal. The pet moves with each turn, following a DFS path through the
   * rooms.
   */
  void wanderPet();

  /**
   * Returns the set of rooms that have been visited by the pet.
   *
   * @return a set of visited rooms
   */
  Set<RoomInterface> getPetVisitedRooms();

  /**
   * Moves the pet to a specified room by room index, resetting the DFS traversal
   * to start from the new room.
   *
   * @param roomInd the room index to move the pet to
   */
  void movePetTo(int roomInd);

  /**
   * Provide players with a hint about the target’s last known room when they use
   * a command like “hint” or periodically during game play.
   *
   * @return The room that target character last visited.
   */
  String getTargetLocationHint();

  /**
   * Determines if Player A can see Player B. Player A can see Player B if they
   * are in the same room, or if Player B is in one of the neighboring spaces of
   * the room Player A is in, provided Player B's room is not sealed.
   *
   * @param playerA the player being observed.
   * @param playerB the player who is checking for visibility.
   * @return true if Player A can see Player B, false otherwise.
   */
  boolean isSeenBy(PlayerInterface playerA, PlayerInterface playerB);
}
