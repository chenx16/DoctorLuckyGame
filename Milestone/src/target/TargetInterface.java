package target;

import room.RoomInterface;

/**
 * Represents a target character in the game world. The target can move between
 * rooms and has a health value.
 */
public interface TargetInterface {
  /**
   * Moves the target character to the specified room, also update lastRoomVisited
   * to the target’s previous room before setting the new current room.
   * 
   * @param room the room to move the target to
   */
  void move(RoomInterface room);

  /**
   * Returns the current room the target character is in.
   * 
   * @return the current room
   */
  RoomInterface getCurrentRoom();

  /**
   * Returns the room the target character last visited.
   * 
   * @return the room the target character last visited
   */
  RoomInterface getLastRoomVisited();

  /**
   * Returns the name of the target character.
   * 
   * @return the name of the target character
   */
  String getName();

  /**
   * Returns the health of the target character.
   * 
   * @return the health of the target character
   */
  int getHealth();

  /**
   * Reduces the health of the target character by the specified amount. If the
   * resulting health is less than 0, it is set to 0.
   * 
   * @param amount the amount of health to reduce
   */
  void reduceHealth(int amount);

  /**
   * Checks if the target character is still alive (health > 0).
   * 
   * @return true if the target character is alive, false otherwise
   */
  boolean isAlive();
}
