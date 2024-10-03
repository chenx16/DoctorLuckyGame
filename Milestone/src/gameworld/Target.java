package gameworld;

public class Target implements TargetInterface {
  private RoomInterface currentRoom;
  private int health;
  private String name;

  // Constructor
  public Target(RoomInterface currentRoom, int health, String name)
      throws IllegalArgumentException {
    if (health < 0) {
      throw new IllegalArgumentException();
    }
    this.currentRoom = currentRoom;
    this.health = health;
    this.name = name;
  }

  // Move the target to a new room
  @Override
  public void move(RoomInterface room) {
    this.currentRoom = room;
  }

  // Get the current room
  @Override
  public RoomInterface getCurrentRoom() {
    return currentRoom;
  }

  // Get the name of the target
  @Override
  public String getName() {
    return name;
  }

  // Get the target's health
  @Override
  public int getHealth() {
    return health;
  }

  // Reduce health by the specified amount
  public void takeDamage(int amount) {
    this.health = Math.max(0, this.health - amount); // Health should not go below 0
  }

  // Check if the target is alive (health > 0)
  public boolean isAlive() {
    return this.health > 0;
  }
}
