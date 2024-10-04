package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the World class. These tests verify that the world loads
 * correctly, generates neighbors, and handles items.
 */
public class WorldInterfaceTest {

  private WorldInterface world;
  private final String localDir = "./res/";

  /**
   * Sets up the test environment by creating a new instance of the world. Load
   * correct file into the world.
   */
  @Before
  public void setUp() throws IOException {
    world = new World();
    // Assuming the test world file is correctly structured for testing
    world.loadFromFile(localDir);

  }

  /**
   * Tests that the loadFromFile method throws an IOException when the file path
   * is invalid.
   */
  @Test(expected = IOException.class)
  public void testLoadFromFileInvalidPath() throws IOException {
    world.loadFromFile("invalid/path/to/file/");
  }

  @Test
  public void testLoadFromFileValid() throws IOException {
    world.loadFromFile(localDir);
    assertNotNull(world.getRooms());
    assertNotNull(world.getItems());
    assertNotNull(world.getTargetCharacter());
    assertEquals(36, world.getRowAndCol()[0][0]);
    assertEquals(30, world.getRowAndCol()[0][1]);
    assertEquals("Doctor Lucky's Mansion", world.getName());
  }

  @Test
  public void testGetNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory
    List<RoomInterface> neighbors = world.getNeighbors(armory);
    assertNotNull(neighbors);
    assertTrue(neighbors.size() > 0); // Should have some neighbors
    assertTrue(neighbors.contains(world.getRooms().get(4))); // Drawing Room
    assertTrue(neighbors.contains(world.getRooms().get(4))); // Dining Hall
  }

  /**
   * Tests getSpaceInfo when the room has items and neighbors.
   */
  @Test
  public void testGetSpaceInfoWithItemsAndNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory

    String expectedOutput = "Room: Armory\n" + "Items in this room:\n"
        + "- Item Revolver with 3 damage.\n" + "Neighboring rooms:\n" + "- Billiard Room\n"
        + "- Dining Hall\n" + "- Drawing Room\n";

    String actualOutput = world.getSpaceInfo(armory);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no items but has neighbors.
   */
  @Test
  public void testGetSpaceInfoNoItems() {
    RoomInterface dining = world.getRooms().get(5); // Room 5 is Foyer without items

    String expectedOutput = "Room: Foyer\n" + "No items in this room.\n" + "Neighboring rooms:\n"
        + "- Drawing Room\n" + "- Piazza\n";

    String actualOutput = world.getSpaceInfo(dining);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no neighbors.
   */
  @Test
  public void testGetSpaceInfoNoNeighbors() {
    RoomInterface newRoom = new Room(new int[] { 3, 0 }, new int[] { 5, 2 }, "New Room", 1);

    String expectedOutput = "Room: New Room\n" + "No items in this room.\n"
        + "This room has no neighboring rooms.\n";

    String actualOutput = world.getSpaceInfo(newRoom);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testMoveTargetCharacter() {
    RoomInterface initialRoom = world.getTargetCharacter().getCurrentRoom();
    world.moveTargetCharacter();
    RoomInterface newRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(initialRoom, newRoom);
  }

  /**
   * Tests that getGraphics returns not null when a graphics returns correctly.
   */
  @Test
  public void testGetGraphics() throws IOException {
    Graphics graphics = world.getGraphics();

    // Ensure graphics is not null (meaning the world map was generated correctly)
    assertNotNull(graphics);
  }

  /**
   * Tests that generateWorldMap correctly generates and saves the image file.
   */
  @Test
  public void testGenerateWorldMapValidPath() throws IOException {
    // Generate the world map with a valid path
    BufferedImage image = world.generateWorldMap();

    // Assert the image is not null and the file is created
    assertNotNull(image);

    // Check if the file is created and exists
    String filePath = localDir + "/worldmap.png";

    File file = new File(filePath);
    assertTrue(file.exists());

    // Check if the file is non-empty (i.e., it contains data)
    assertTrue(file.length() > 0);
  }

  @Test
  public void testGraphicsDrawsWorld() throws IOException {
    // Ensure that the BufferedImage is created properly before getting the Graphics
    // object
    BufferedImage image = world.generateWorldMap();
    assertNotNull(image); // BufferedImage should not be null
    assertEquals(BufferedImage.TYPE_INT_ARGB, image.getType()); // Check the image type

    // Get the Graphics object and perform a sample drawing operation
    Graphics graphics = image.getGraphics();
    assertNotNull(graphics); // Graphics object should not be null

    // Optionally, draw something and ensure the operation doesn't throw an error
    graphics.setColor(Color.BLACK);
    graphics.drawRect(0, 0, 100, 100); // Draw a test rectangle
  }

  @Test
  public void testBufferedImageDimensions() throws IOException {
    BufferedImage image = world.generateWorldMap();
    assertNotNull(image);

    int expectedWidth = world.getRowAndCol()[0][1] * 50; // Assuming each cell is 50px wide
    int expectedHeight = world.getRowAndCol()[0][0] * 50; // Assuming each cell is 50px tall
    assertEquals(expectedWidth, image.getWidth());
    assertEquals(expectedHeight, image.getHeight());
  }
}
