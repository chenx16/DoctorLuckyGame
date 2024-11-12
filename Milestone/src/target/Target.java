package target;

import java.util.Objects;
import room.RoomInterface;

/**
 * Represents a target character in the game world. The target character has
 * health and can move between rooms in the world.
 */
public class Target implements TargetInterface {

  private RoomInterface currentRoom;
  private RoomInterface lastRoomVisited;
  private int health;
  private String name;

  /**
   * Constructs a target character with the specified starting room, health, and
   * name.
   * 
   * @param currentRoom the room where the target starts
   * @param health      the health of the target character
   * @param name        the name of the target character
   */
  public Target(RoomInterface currentRoom, int health, String name)
      throws IllegalArgumentException {
    if (health < 0) {
      throw new IllegalArgumentException();
    }
    this.currentRoom = currentRoom;
    this.lastRoomVisited = null; // Initially, no last room visited
    this.health = health;
    this.name = name;
  }

  @Override
  public void move(RoomInterface room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    this.lastRoomVisited = this.currentRoom; // Update last room before moving
    this.currentRoom = room;
  }

  @Override
  public RoomInterface getCurrentRoom() {
    return currentRoom;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void reduceHealth(int amount) {
    this.health = Math.max(0, this.health - amount); // Health should not go below 0
  }

  @Override
  public boolean isAlive() {
    return this.health > 0;
  }

  @Override
  public RoomInterface getLastRoomVisited() {
    return lastRoomVisited;
  }

  /**
   * Checks if this target is equal to another target.
   * 
   * @param obj the object to compare with
   * @return true if the targets are having same hashCode, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Target)) {
      return false;
    }
    Target other = (Target) obj;
    return this.hashCode() == other.hashCode();
  }

  /**
   * Generates a hash code for this target.
   *
   * @return the hash code for the target
   */
  @Override
  public int hashCode() {
    return Objects.hash(currentRoom, health, name);
  }

  /**
   * Returns a string representation of the Target, including its name, health,
   * and current room.
   * 
   * @return a string representing the target's details
   */
  @Override
  public String toString() {
    return String.format("Target character name: %s, health: %d, currentRoom: %s\n", name, health,
        (currentRoom != null ? currentRoom.getName() : "None"));
  }
}
