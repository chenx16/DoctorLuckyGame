
package gameworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Represents the game world, consisting of multiple rooms, items, and a target
 * character. The world can be loaded from a file, and a graphical map of the
 * world can be generated.
 */
public class World implements WorldInterface {
  private List<RoomInterface> rooms;
  private List<ItemInterface> items;
  private TargetInterface targetCharacter;
  private int rows;
  private int cols;
  private String worldName;
  private final int pixel;
  private String path;

  /**
   * Constructs an empty world.
   */
  public World() {
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
    this.pixel = 50;
    this.path = null;
  }

  @Override
  public void loadFromFile(String filePath) throws IOException {
    BufferedReader reader = null;
    this.path = filePath;
    try {
      reader = new BufferedReader(new FileReader(this.path + "mansion.txt"));
      // Parse world dimensions and name
      // Split the first two values (rows, cols) and then
      // capture the rest as the world name
      String[] worldInfo = reader.readLine().split("\\s+", 3);
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
        reader.close();
        // Ensure the BufferedReader is closed properly, even if an exception occurs.
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
    // Collect room information
    StringBuilder spaceInfo = new StringBuilder();
    spaceInfo.append("Room: ").append(room.getName()).append("\n");

    // Add information about the items in the room
    List<ItemInterface> roomItems = room.getItems();
    if (roomItems.isEmpty()) {
      spaceInfo.append("No items in this room.\n");
    } else {
      spaceInfo.append("Items in this room:\n");
      for (ItemInterface item : roomItems) {
        spaceInfo.append("- ").append(item.getName()).append(" (Damage: ").append(item.getDamage())
            .append(")\n");
      }
    }

    // Add information about neighboring rooms
    List<RoomInterface> neighbors = room.myListofNeighbors();
    if (neighbors.isEmpty()) {
      spaceInfo.append("This room has no neighboring rooms.\n");
    } else {
      spaceInfo.append("Neighboring rooms:\n");
      for (RoomInterface neighbor : neighbors) {
        spaceInfo.append("- ").append(neighbor.getName()).append("\n");
      }
    }

    return spaceInfo.toString();
  }

  @Override
  public void moveTargetCharacter() {
    RoomInterface currentRoom = targetCharacter.getCurrentRoom();
    int currentIndex = rooms.indexOf(currentRoom);
    int nextIndex = (currentIndex + 1) % rooms.size(); // Ensures the index stays within bounds
    targetCharacter.move(rooms.get(nextIndex));
  }

  @Override
  public BufferedImage generateWorldMap() throws IOException {
    BufferedImage image = new BufferedImage(cols * pixel, rows * pixel,
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = null;

    try {
      g = image.getGraphics();
      g.setColor(Color.BLACK);

      // Draw each room
      for (RoomInterface room : rooms) {
        int[] upperLeft = room.getCoordinateUpperLeft();
        int[] lowerRight = room.getCoordinateLowerRight();
        int x = upperLeft[1] * pixel;
        int y = upperLeft[0] * pixel;
        int width = (lowerRight[1] - upperLeft[1] + 1) * pixel;
        int height = (lowerRight[0] - upperLeft[0] + 1) * pixel;

        // Draw room's outline and name
        g.drawRect(x, y, width, height);
        g.drawString(room.getName(), x + 5, y + 15);
      }

      // Save the image to the file
      File outputfile = new File(this.path + "worldmap.png");
      ImageIO.write(image, "png", outputfile);
      // System.out.println("World map saved as 'worldmap.png' !");

    } catch (IOException e) {
      // Log the error and throw new error
      System.err.println("Failed to save world map: " + e.getMessage());
      throw new IOException("Error saving the world map to file", e);
    } finally {
      // Clean up the Graphics object
      if (g != null) {
        g.dispose();
      }
    }

    // Return the generated BufferedImage
    return image;
  }

  @Override
  public Graphics getGraphics() throws IOException {
    BufferedImage image;
    try {
      image = generateWorldMap();
    } catch (IOException e) {
      System.err.println("Error generating world map for Graphics: " + e.getMessage());
      return null;
    }
    return image.getGraphics(); // Return the Graphics object from the BufferedImage
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
