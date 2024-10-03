
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
  public void loadFromFile(String filePath) throws IOException {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filePath));
      // Parse world dimensions and name
      String[] worldInfo = reader.readLine().split("\\s+", 3); // Split the first two values (rows,
                                                               // cols) and then capture the rest as
                                                               // the world name
      this.rows = Integer.parseInt(worldInfo[0]);
      this.cols = Integer.parseInt(worldInfo[1]);
      this.worldName = worldInfo[2];

      // Parse target character
      String[] targetInfo = reader.readLine().split("\\s+", 2);
      int health = Integer.parseInt(targetInfo[0]);
      String targetName = targetInfo[1]; // Target name could be multiple words
      this.targetCharacter = new Target(null, health, targetName);

      // Parse rooms
      int roomCount = Integer.parseInt(reader.readLine().trim());
      for (int roomInd = 0; roomInd < roomCount; roomInd++) {
        // Parse room line with flexible spaces handling, even if is a space at the
        // front of line
        String[] roomData = reader.readLine().trim().split("\\s+", 5);
        System.out.println(roomData[0]);
        int[] upperLeft = { Integer.parseInt(roomData[0]), Integer.parseInt(roomData[1]) };
        int[] lowerRight = { Integer.parseInt(roomData[2]), Integer.parseInt(roomData[3]) };
        String roomName = roomData[4];
        rooms.add(new Room(upperLeft, lowerRight, roomName, roomInd));
      }

      // After rooms are loaded, calculate neighbors
      calculateNeighbors();

      // Parse items
      int itemCount = Integer.parseInt(reader.readLine());
      for (int i = 0; i < itemCount; i++) {
        String[] itemData = reader.readLine().split("\\s+", 3);
        int roomInd = Integer.parseInt(itemData[0]);
        int damage = Integer.parseInt(itemData[1]);
        String itemName = itemData[2]; // Item name could be multiple words
        Item item = new Item(damage, itemName);
        rooms.get(roomInd).addItem(item);
        items.add(item);
      }

    } catch (IOException e) {
      throw new IOException("Failed to load file: " + filePath, e);
    } finally {
      if (reader != null) {
        reader.close(); // Ensure the BufferedReader is closed properly, even if an exception
                        // occurs.
      }
    }
  }

  private void calculateNeighbors() {
    for (RoomInterface room : rooms) {
      for (RoomInterface otherRoom : rooms) {
        if (!room.equals(otherRoom) && isNeighbor(room, otherRoom)) {
          room.addNeighbor(otherRoom); // Add the neighboring room
        }
      }
    }
  }

  private boolean isNeighbor(RoomInterface room1, RoomInterface room2) {
    // Logic to check if rooms share a wall (neighbors)
    int[] upperLeft1 = room1.getCoordinateUpperLeft();
    int[] lowerRight1 = room1.getCoordinateLowerRight();
    int[] upperLeft2 = room2.getCoordinateUpperLeft();
    int[] lowerRight2 = room2.getCoordinateLowerRight();

    int room1UpperLeftRow = upperLeft1[0];
    int room1UpperLeftCol = upperLeft1[1];
    int room1LowerRightRow = lowerRight1[0];
    int room1LowerRightCol = lowerRight1[1];

    int room2UpperLeftRow = upperLeft2[0];
    int room2UpperLeftCol = upperLeft2[1];
    int room2LowerRightRow = lowerRight2[0];
    int room2LowerRightCol = lowerRight2[1];

    // Check if the rooms share any walls (they must touch each other)
    boolean horizontallyShared = (room1LowerRightRow == room2UpperLeftRow - 1
        || room1UpperLeftRow == room2LowerRightRow + 1)
        && (room1UpperLeftCol <= room2LowerRightCol && room1LowerRightCol >= room2UpperLeftCol);

    boolean verticallyShared = (room1LowerRightCol == room2UpperLeftCol - 1
        || room1UpperLeftCol == room2LowerRightCol + 1)
        && (room1UpperLeftRow <= room2LowerRightRow && room1LowerRightRow >= room2UpperLeftRow);

    return horizontallyShared || verticallyShared;
  }

  @Override
  public List<RoomInterface> getNeighbors(RoomInterface room) {
    return room.myListofNeighbors();
  }

  @Override
  public String getSpaceInfo(RoomInterface room) {
    return room.getName() + " contains: " + room.getItems();
  }

  @Override
  public void moveTargetCharacter() {
    RoomInterface currentRoom = targetCharacter.getCurrentRoom();
    int currentIndex = rooms.indexOf(currentRoom);
    int nextIndex = (currentIndex + 1) % rooms.size(); // Ensures the index stays within bounds
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
