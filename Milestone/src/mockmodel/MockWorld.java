package mockmodel;

import coordinate.Coordinate;
import gameworld.WorldInterface;
import item.Item;
import item.ItemInterface;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import player.PlayerInterface;
import room.Room;
import room.RoomInterface;
import target.TargetInterface;

public class MockWorld implements WorldInterface {

  private List<RoomInterface> rooms;
  private List<ItemInterface> items;
  private List<PlayerInterface> players;
  private StringBuilder log;
  private final String response;

  public MockWorld(StringBuilder log, String response) {
    this.log = log;
    this.response = response;
    this.players = new ArrayList<>();
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();

    // Adding mock data for testing
    RoomInterface newRoom = new Room(new Coordinate(3, 3), new Coordinate(5, 5), "New Room", 0,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    RoomInterface neighborRoom = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Neighbor", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    newRoom.addNeighbor(neighborRoom);
    rooms.add(newRoom);
    rooms.add(neighborRoom);
    items.add(new Item(10, "Revolver"));
  }

  @Override
  public String turnHumanPlayer(String action, int roomInd, String itemName) {
    log.append("Action: ").append(action).append(", Room: ").append(roomInd).append(", Item: ")
        .append(itemName).append("\n");
    return response;
  }

  @Override
  public void loadFromFile(Readable inputSource) throws IOException {
  }

  @Override
  public String getName() {
    return response;
  }

  @Override
  public List<RoomInterface> getRooms() {
    return rooms;
  }

  @Override
  public String turnComputerPlayer() {
    log.append("Mock Computer Player Turn").append("\n");
    return response;
  }

  @Override
  public List<RoomInterface> getNeighbors(RoomInterface room) {
    return new ArrayList<>();
  }

  @Override
  public String getSpaceInfo(RoomInterface room) {
    log.append("getSpaceInfo called").append("\n");
    return response;
  }

  @Override
  public BufferedImage generateWorldMap(String fileDir) throws IOException {
    return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public Graphics getGraphics(String fileDir) throws IOException {
    return null;
  }

  @Override
  public int[][] getRowAndCol() {
    return new int[][] { { 1, 1 } };
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }

  @Override
  public void addPlayer(PlayerInterface player, int roomInd) {
    log.append("addPlayer called\n");
    players.add(player);
  }

  @Override
  public List<PlayerInterface> getPlayers() {
    return players;
  }

  @Override
  public PlayerInterface getTurn() {
    log.append("getTurn called\n");
    return players.get(0);
  }

  @Override
  public void moveTargetCharacter() {
    log.append("moveTargetCharacter called\n");
  }

  @Override
  public TargetInterface getTargetCharacter() {
    log.append("getTargetCharacter called\n");
    return null;
  }
}
