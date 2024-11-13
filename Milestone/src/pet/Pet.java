package pet;

import java.util.Objects;
import room.RoomInterface;
import target.Target;

/**
 * Represents a pet in the game world. The pet can move between rooms and affect
 * visibility.
 */
public class Pet implements PetInterface {

  private String name;
  private RoomInterface currentRoom;

  /**
   * Constructs a pet with the specified name and starting room.
   *
   * @param petName      the name of the pet
   * @param startingRoom the room where the pet starts
   */
  public Pet(String petName, RoomInterface startingRoom) {
    this.name = petName;
    this.currentRoom = startingRoom;
  }

  @Override
  public void moveTo(RoomInterface room) {
    this.currentRoom = room;
    // System.out.println(room.getName());
  }

  @Override
  public RoomInterface getCurrentRoom() {
    return this.currentRoom;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Checks if this pet is equal to another target.
   * 
   * @param obj the object to compare with
   * @return true if the pets are having same hashCode, false otherwise
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
    Pet other = (Pet) obj;
    return this.hashCode() == other.hashCode();
  }

  /**
   * Generates a hash code for this pet.
   *
   * @return the hash code for the pet
   */
  @Override
  public int hashCode() {
    return Objects.hash(currentRoom, name);
  }

  /**
   * Returns a string representation of the Target, including its name, health,
   * and current room.
   * 
   * @return a string representing the target's details
   */
  @Override
  public String toString() {
    return String.format("Pet name: %s, currentRoom: %s\n", name,
        (currentRoom != null ? currentRoom.getName() : "None"));
  }

}
