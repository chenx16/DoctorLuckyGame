package gameworld;

/**
 * Represents a target character in the game world. The target character has
 * health and can move between rooms in the world.
 */
public class Target implements TargetInterface {
  private RoomInterface currentRoom;
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
    this.health = health;
    this.name = name;
  }

  @Override
  public void move(RoomInterface room) {
    RoomInterface roomCopy = new Room(room.getCoordinateUpperLeft(), room.getCoordinateLowerRight(),
        room.getName(), room.getRoomInd());
    this.currentRoom = roomCopy;
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

  public void takeDamage(int amount) {
    this.health = Math.max(0, this.health - amount); // Health should not go below 0
  }

  public boolean isAlive() {
    return this.health > 0;
  }

}
