
package gameworld;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import player.PlayerInterface;

/**
 * Represents the game world, consisting of multiple rooms, items, and a target
 * character. The world can be loaded from a file, and a graphical map of the
 * world can be generated.
 */
public class World implements WorldInterface {

  private List<RoomInterface> rooms;
  private List<ItemInterface> items;
  private List<PlayerInterface> players;
  private TargetInterface targetCharacter;
  private int rows;
  private int cols;
  private String worldName;
  private final int pixel;

  /**
   * Constructs an empty world.
   */
  public World() {
    this.players = new ArrayList<PlayerInterface>();
    this.rooms = new ArrayList<RoomInterface>();
    this.items = new ArrayList<ItemInterface>();
    this.pixel = 50;
  }

  @Override
  public void loadFromFile(Readable source) throws IOException {
    if (source == null) {
      throw new IOException("parameter cannot be null");
    }
    BufferedReader reader = new BufferedReader((Reader) source);
    try {
      // Parse world dimensions and name
      String firstLine = reader.readLine().trim(); // Trim the first line
      if (firstLine == null) {
        throw new IOException("No content to read, input seems empty.");
      }
      // System.out.println("First line: " + firstLine);

      String[] worldInfo = firstLine.split("\\s+", 3);
      this.rows = Integer.parseInt(worldInfo[0]);
      this.cols = Integer.parseInt(worldInfo[1]);
      this.worldName = worldInfo[2];

      // System.out.println("World Dimensions: " + rows + "x" + cols + ", Name: " +
      // worldName);

      // Parse target character
      String secondLine = reader.readLine().trim(); // Trim the second line
      // System.out.println("Second line (Target Info): " + secondLine);
      if (secondLine == null) {
        throw new IOException("Expected target info but found null.");
      }

      String[] targetInfo = secondLine.split("\\s+", 2);
      int health;
      String targetName;
      health = Integer.parseInt(targetInfo[0].trim()); // Trim the number
      targetName = targetInfo[1].trim(); // Trim the target name

      // System.out.println("Target: " + targetName + ", Health: " + health);

      // Parse the number of rooms
      String thirdLine = reader.readLine().trim(); // Trim the third line
      // System.out.println("Third line (Room count): " + thirdLine);
      if (thirdLine == null) {
        throw new IOException("Expected room count but found null.");
      }

      int roomCount = Integer.parseInt(thirdLine.trim());
      // System.out.println("Room Count: " + roomCount);

      for (int roomInd = 0; roomInd < roomCount; roomInd++) {
        String roomData = reader.readLine().trim();
        // System.out.println("Room Data: " + roomData);
        String[] roomParts = roomData.split("\\s+", 5);
        Coordinate upperLeft = new Coordinate(Integer.parseInt(roomParts[0].trim()),
            Integer.parseInt(roomParts[1].trim()));
        Coordinate lowerRight = new Coordinate(Integer.parseInt(roomParts[2].trim()),
            Integer.parseInt(roomParts[3].trim()));
        String roomName = roomParts[4].trim();
        rooms.add(new Room(upperLeft, lowerRight, roomName, roomInd, new ArrayList<ItemInterface>(),
            new ArrayList<RoomInterface>()));
      }
      // After rooms are loaded, calculate neighbors
      calculateNeighbors();

      // Parse the items
      String itemCountLine = reader.readLine().trim(); // Trim item count line
      // System.out.println("Item count line: " + itemCountLine);
      int itemCount = Integer.parseInt(itemCountLine.trim());

      for (int i = 0; i < itemCount; i++) {
        String itemData = reader.readLine().trim();
        // System.out.println("Item Data: " + itemData);
        String[] itemParts = itemData.split("\\s+", 3);
        int roomInd = Integer.parseInt(itemParts[0].trim());
        int damage = Integer.parseInt(itemParts[1].trim());
        String itemName = itemParts[2].trim();

        Item item = new Item(damage, itemName);
        rooms.get(roomInd).addItem(item);
        items.add(item);
      }

      // Set the target character in the first room
      this.targetCharacter = new Target(this.getRooms().get(0), health, targetName);

    } catch (NumberFormatException e) {
      throw new IOException("Failed to load the world: Invalid number format", e);
    } finally {
      reader.close();
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
    CoordinateInterface upperLeft1 = room1.getCoordinateUpperLeft();
    CoordinateInterface lowerRight1 = room1.getCoordinateLowerRight();
    CoordinateInterface upperLeft2 = room2.getCoordinateUpperLeft();
    CoordinateInterface lowerRight2 = room2.getCoordinateLowerRight();

    // Room 1 coordinates
    int room1UpperLeftRow = upperLeft1.getX();
    int room1UpperLeftCol = upperLeft1.getY();
    int room1LowerRightRow = lowerRight1.getX();
    int room1LowerRightCol = lowerRight1.getY();

    // Room 2 coordinates
    int room2UpperLeftRow = upperLeft2.getX();
    int room2UpperLeftCol = upperLeft2.getY();
    int room2LowerRightRow = lowerRight2.getX();
    int room2LowerRightCol = lowerRight2.getY();

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
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }
    // Return a copy to avoid modification
    return new ArrayList<RoomInterface>(room.getListofNeighbors());
  }

  @Override
  public String getSpaceInfo(RoomInterface room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null.");
    }

    StringBuilder spaceInfo = new StringBuilder(room.getRoomDescription());

    // Add target character info if present in the room
    if (targetCharacter.getCurrentRoom().equals(room)) {
      spaceInfo.append("Target character is here: ").append(targetCharacter.getName()).append("\n");
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

//  @Override
//  public void addPlayer(PlayerInterface player) {
//    players.add(player);
//  }
//  
//  @Override
//  public void addPlayerInRoom(PlayerInterface player, RoomInterface room) {
//    room.addPlayer(player);
//  }
//
//  @Override
//  public String takeTurn(PlayerInterface player) {
//    if (player.getIsComputerControlled()) {
//      return ((ComputerPlayer) player).takeTurn();
//    } else {
//      return player.getName() + "'s turn is pending user input.";
//    }
//  }

  @Override
  public BufferedImage generateWorldMap(String fileDir) throws IOException {
    BufferedImage image = new BufferedImage(cols * (pixel + 1), rows * (pixel + 1),
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = null;

    try {
      g = image.getGraphics();
      g.setColor(Color.BLACK);

      Font font = new Font("Arial", Font.PLAIN, 12);

      g.setColor(Color.BLACK);
      g.setFont(font);

      // Draw each room
      for (RoomInterface room : rooms) {
        CoordinateInterface upperLeft = room.getCoordinateUpperLeft();
        CoordinateInterface lowerRight = room.getCoordinateLowerRight();
        int x = upperLeft.getY() * pixel;
        int y = upperLeft.getX() * pixel;
        int width = (lowerRight.getY() - upperLeft.getY() + 1) * pixel;
        int height = (lowerRight.getX() - upperLeft.getX() + 1) * pixel;

        // Draw room's outline and name
        g.drawRect(x, y, width, height);
        g.drawString(room.getName(), x + 5, y + 15);
      }

      // Save the image to the file
      File outputfile = new File(fileDir + "worldmap.png");
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
  public Graphics getGraphics(String fileDir) throws IOException {
    BufferedImage image;
    try {
      image = generateWorldMap(fileDir);
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
    // Create a defensive copy of the targetCharacter to prevent external
    // modifications
    return new Target(targetCharacter.getCurrentRoom(), // Defensive copy of current room
        targetCharacter.getHealth(), // Health is a primitive type, so it's safe to return directly
        targetCharacter.getName() // String is immutable, so it's safe to return directly
    );
  }

  @Override
  public List<RoomInterface> getRooms() {
    return new ArrayList<>(rooms);
  }

  @Override
  public List<ItemInterface> getItems() {
    return new ArrayList<>(items);
  }

  /**
   * Checks if this world is equal to another world.
   * 
   * @param obj the object to compare with
   * @return true if the worlds are having same hashCode, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof World)) {
      return false;
    }
    World other = (World) obj;
    return this.hashCode() == other.hashCode();
  }

  /**
   * Generates a hash code for this world.
   *
   * @return the hash code for the world
   */
  @Override
  public int hashCode() {
    return Objects.hash(rooms, items, targetCharacter, rows, cols, worldName);
  }

}