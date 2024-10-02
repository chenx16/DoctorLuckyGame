package gameworld;

public class Target implements TargetInterface {
  private RoomInterface currentRoom;
  private int health;
  private String name;

  public Target(RoomInterface currentRoom, int health, String name) {
    this.currentRoom = currentRoom;
    this.health = health;
    this.name = name;
  }

  @Override
  public void move(RoomInterface room) {
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
}
