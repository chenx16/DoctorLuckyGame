package pet;

import room.RoomInterface;

/**
 * An interface representing the behavior of a pet in the game world.
 */
public interface PetInterface {
  /**
   * Moves the pet to a specified room.
   *
   * @param room the room to move the pet to
   */
  void moveTo(RoomInterface room);

  /**
   * Get the current room where the pet is located.
   *
   * @return the current room of the pet
   */
  RoomInterface getCurrentRoom();

  /**
   * Checks if the pet is in a specific room.
   *
   * @param room the room to check
   * @return true if the pet is in the specified room, false otherwise
   */
  boolean isInRoom(RoomInterface room);

  /**
   * Gets the name of the pet.
   *
   * @return the name of the pet
   */
  String getName();
}
