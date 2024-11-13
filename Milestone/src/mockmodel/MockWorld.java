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
import java.util.Set;
import pet.Pet;
import pet.PetInterface;
import player.PlayerInterface;
import room.Room;
import room.RoomInterface;
import target.TargetInterface;

/**
 * MockWorld is a mock implementation of the WorldInterface designed for testing
 * purposes. It logs interactions and simulates game world behaviors such as
 * player actions, room information, item management, and other world
 * interactions.
 */
public class MockWorld implements WorldInterface {

  private List<RoomInterface> rooms;
  private List<ItemInterface> items;
  private List<PlayerInterface> players;
  private PetInterface pet;
  private StringBuilder log;
  private String response;

  /**
   * Constructs a MockWorld with a specified log and response. Populates the mock
   * world with sample rooms, items, and neighbors for testing purposes.
   *
   * @param log      a StringBuilder to capture logs of method calls for
   *                 verification in tests
   * @param response a fixed response string to return for certain method calls
   */
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
    ItemInterface item = new Item(10, "Revolver");
    newRoom.addItem(item);
    newRoom.addNeighbor(neighborRoom);
    rooms.add(newRoom);
    rooms.add(neighborRoom);
    items.add(item);
    pet = new Pet("Fortune the Cat", newRoom);
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

  @Override
  public PetInterface getPet() {
    log.append("getPet called\n");
    return this.pet;
  }

  @Override
  public void wanderPet() {
    log.append("wanderPet called\n");
  }

  @Override
  public Set<RoomInterface> getPetVisitedRooms() {
    log.append("getPetVisitedRooms called\n");
    return null;
  }

  @Override
  public void movePetTo(int roomInd) {
    log.append("movePetTo called\n");
  }

  @Override
  public String getTargetLocationHint() {
    log.append("getTargetLocationHint called\n");
    return null;
  }

  @Override
  public String getPlayerSpaceInfo(PlayerInterface player) {
    log.append("getPlayerSpaceInfo called\n");
    return null;
  }

  @Override
  public void setGameEnd(boolean isGameEnd) {
    log.append("setGameEnd called\n");
  }

  @Override
  public boolean isGameEnd() {
    log.append("isGameEnd called\n");
    return false;
  }

  @Override
  public String attemptOnTarget(PlayerInterface player, String itemName) {
    log.append("attemptOnTarget called\n");
    return null;
  }

}
