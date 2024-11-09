package pet;

import room.RoomInterface;

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
//    System.out.println(room.getName());
  }

  @Override
  public RoomInterface getCurrentRoom() {
    return this.currentRoom;
  }

  @Override
  public boolean isInRoom(RoomInterface room) {
    return this.currentRoom.equals(room);
  }

  @Override
  public String getName() {
    return name;
  }

}
