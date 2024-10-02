package gameworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World implements WorldInterface {
  private List<RoomInterface> rooms;
  private List<ItemInterface> items;
  private TargetInterface targetCharacter;
  private int rows, cols;
  private String worldName;

  public World() {
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
  }

  @Override
  public void loadFromFile(String filePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      // Parse world dimensions and name
      String[] worldInfo = reader.readLine().split(" ");
      this.rows = Integer.parseInt(worldInfo[0]);
      this.cols = Integer.parseInt(worldInfo[1]);
      this.worldName = worldInfo[2];

      // Parse target character
      String[] targetInfo = reader.readLine().split(" ");
      int health = Integer.parseInt(targetInfo[0]);
      String targetName = targetInfo[1];
      this.targetCharacter = new Target(null, health, targetName);

      // Parse rooms
      int roomCount = Integer.parseInt(reader.readLine());
      for (int i = 0; i < roomCount; i++) {
        String[] roomData = reader.readLine().split(" ");
        int[] upperLeft = { Integer.parseInt(roomData[0]), Integer.parseInt(roomData[1]) };
        int[] lowerRight = { Integer.parseInt(roomData[2]), Integer.parseInt(roomData[3]) };
        String roomName = roomData[4];
        rooms.add(new Room(upperLeft, lowerRight, roomName));
      }

      // Parse items
      int itemCount = Integer.parseInt(reader.readLine());
      for (int i = 0; i < itemCount; i++) {
        String[] itemData = reader.readLine().split(" ");
        int roomInd = Integer.parseInt(itemData[0]);
        int damage = Integer.parseInt(itemData[1]);
        String itemName = itemData[2];
        Item item = new Item(damage, itemName);
        rooms.get(roomInd).addItem(item);
        items.add(item);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<RoomInterface> getNeighbors(RoomInterface room) {
    List<RoomInterface> neighbors = new ArrayList<>();
    for (RoomInterface r : rooms) {
      if (isNeighbor(room, r)) {
        neighbors.add(r);
      }
    }
    return neighbors;
  }

  private boolean isNeighbor(RoomInterface room1, RoomInterface room2) {
    // Logic to check if rooms share a wall
    int[] upperLeft1 = room1.getCoordinateUpperLeft();
    int[] lowerRight1 = room1.getCoordinateLowerRight();
    int[] upperLeft2 = room2.getCoordinateUpperLeft();
    int[] lowerRight2 = room2.getCoordinateLowerRight();
    // Check if the rooms share any walls
    return ((lowerRight1[1] == upperLeft2[1] && upperLeft1[0] == upperLeft2[0])
        || (lowerRight2[1] == upperLeft1[1] && upperLeft1[0] == upperLeft2[0]));
  }

  @Override
  public String getSpaceInfo(RoomInterface room) {
    return room.getName() + " contains: " + room.getItems();
  }

  @Override
  public void moveTargetCharacter() {
    RoomInterface currentRoom = targetCharacter.getCurrentRoom();
    int currentIndex = rooms.indexOf(currentRoom);
    int nextIndex = (currentIndex + 1) % rooms.size();
    targetCharacter.move(rooms.get(nextIndex));
  }

  @Override
  public BufferedImage generateWorldMap() {
    BufferedImage image = new BufferedImage(cols * 50, rows * 50, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    g.setColor(Color.BLACK);
    for (RoomInterface room : rooms) {
      int[] upperLeft = room.getCoordinateUpperLeft();
      int[] lowerRight = room.getCoordinateLowerRight();
      g.drawRect(upperLeft[1] * 50, upperLeft[0] * 50, (lowerRight[1] - upperLeft[1]) * 50,
          (lowerRight[0] - upperLeft[0]) * 50);
      g.drawString(room.getName(), upperLeft[1] * 50 + 5, upperLeft[0] * 50 + 15);
    }
    return image;
  }

  @Override
  public Graphics getGraphics() {
    BufferedImage image = generateWorldMap();
    return image.getGraphics();
  }

  @Override
  public String getName() {
    return worldName;
  }

  @Override
  public int[][] getRowAndCol() {
    return new int[][] { { rows, cols } };
  }

  @Override
  public TargetInterface getTargetCharacter() {
    return targetCharacter;
  }

  @Override
  public List<RoomInterface> getRooms() {
    return rooms;
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }
}
