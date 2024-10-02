package gameworld;

public interface TargetInterface {
  void move(RoomInterface room);

  RoomInterface getCurrentRoom();

  String getName();

  int getHealth();
}
