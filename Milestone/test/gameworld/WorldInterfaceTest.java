package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the World class. These tests verify that the world loads
 * correctly, generates neighbors, and handles items.
 */
public class WorldInterfaceTest {

  private WorldInterface world;
  private WorldInterface worldSame;
  private WorldInterface worldDiff;
  private final String localDir = "./res/";

  /**
   * Sets up the test environment by creating a new instance of the world. Load
   * correct file into the world.
   * 
   */
  @Before
  public void setUp() throws IOException {

    world = new World();
    worldSame = new World();
    worldDiff = new World();
    // Assuming the test world file is correctly structured for testing
    File worldFile = new File(localDir + "mansion.txt");
    FileReader fileReader1 = new FileReader(worldFile);
    world.loadFromFile(fileReader1);

    FileReader fileReader2 = new FileReader(worldFile);
    worldSame.loadFromFile(fileReader2);

  }

  /**
   * Tests loading a valid world from a StringReader (simulated file input).
   * Verifies that rooms, items, and the target character are loaded correctly.
   */
  @Test
  public void testLoadFromString() throws IOException {
    String worldData = "36 30 Doctor Lucky's Mansion\n" + "50 Doctor Lucky\n" + "2\n"
        + "0 0 2 2 Armory\n" + "3 0 5 2 Billiard Room\n" + "2\n"// 2 rooms
        + "0 5 Revolver\n" + "1 3 Billiard Cue\n"; // 2 items

    StringReader reader = new StringReader(worldData);
    worldDiff.loadFromFile(reader);

    // Assertions to verify the world is loaded correctly
    assertEquals("Doctor Lucky's Mansion", world.getName());
    assertEquals(2, worldDiff.getRooms().size());

    RoomInterface armory = worldDiff.getRooms().get(0);
    assertEquals("Armory", armory.getName());
    assertEquals(1, armory.getItems().size());

    ItemInterface revolver = armory.getItems().get(0);
    assertEquals("Revolver", revolver.getName());
    assertEquals(5, revolver.getDamage());

    // Test target character
    TargetInterface target = worldDiff.getTargetCharacter();
    assertEquals("Doctor Lucky", target.getName());
    assertEquals(50, target.getHealth());
  }

  /**
   * Tests loading invalid world data to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadInvalidWorldData() throws IOException {
    String invalidWorldData = "Invalid data\n50 Doctor Lucky\n";
    StringReader reader = new StringReader(invalidWorldData);
    world.loadFromFile(reader);
  }

  /**
   * Tests loading null data to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadNull() throws IOException {
    world.loadFromFile(null);
  }

  /**
   * Tests loading invalid file to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadInvalidFile() throws IOException {
    File worldFile = new File(localDir + "output.txt");
    FileReader fileReader = new FileReader(worldFile);
    world.loadFromFile(fileReader);
  }

  /**
   * Tests loading a valid world file and checks that rooms, items, and the target
   * are loaded correctly.
   */
  @Test
  public void testLoadFromFileValid() throws IOException {
    assertNotNull(world.getRooms());
    assertNotNull(world.getItems());
    assertNotNull(world.getTargetCharacter());
    assertEquals(36, world.getRowAndCol()[0][0]);
    assertEquals(30, world.getRowAndCol()[0][1]);
    assertEquals("Doctor Lucky's Mansion", world.getName());
  }

  /**
   * Tests retrieving neighbors for a room.
   */
  @Test
  public void testGetNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory
    List<RoomInterface> neighbors = world.getNeighbors(armory);
    assertNotNull(neighbors);
    assertTrue(neighbors.size() > 0); // Should have some neighbors
    assertTrue(neighbors.contains(world.getRooms().get(3))); // Dining Hall
    assertTrue(neighbors.contains(world.getRooms().get(4))); // Drawing Room
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a null room is passed
   * to getNeighbors().
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighborsInValid() {
    world.getNeighbors(null);
  }

  /**
   * Tests getSpaceInfo when the room has items and neighbors.
   */
  @Test
  public void testGetSpaceInfoWithItemsAndNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory

    String expectedOutput = "Room: Armory\n" + "Items in this room:\n"
        + "- Item Revolver with 3 damage.\n" + "Players in room: No players in this room.\n"
        + "Neighboring rooms:\n" + "- Billiard Room\n" + "- Dining Hall\n" + "- Drawing Room\n\n"
        + "Target character is here: Doctor Lucky\n";

    String actualOutput = world.getSpaceInfo(armory);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no items but has neighbors.
   */
  @Test
  public void testGetSpaceInfoNoItems() {
    RoomInterface dining = world.getRooms().get(5); // Room 5 is Foyer without items

    String expectedOutput = "Room: Foyer\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "Neighboring rooms:\n"
        + "- Drawing Room\n" + "- Piazza\n\n";

    String actualOutput = world.getSpaceInfo(dining);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no items or neighbors.
   */
  @Test
  public void testGetSpaceInfoNoNeighbors() {
    RoomInterface newRoom = new Room(new Coordinate(3, 0), new Coordinate(5, 2), "New Room", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    String expectedOutput = "Room: New Room\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "This room has no neighboring rooms.\n";

    String actualOutput = world.getSpaceInfo(newRoom);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests if the getSpaceInfo check a null input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetSpaceInfoInValid() {
    world.getSpaceInfo(null);
  }

  /**
   * Tests moving the target character.
   */
  @Test
  public void testMoveTargetCharacter() {
    RoomInterface initialRoom = world.getTargetCharacter().getCurrentRoom();
    world.moveTargetCharacter();
    RoomInterface secondRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(initialRoom, secondRoom);
    world.moveTargetCharacter();
    RoomInterface thirdRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(thirdRoom, secondRoom);
  }

  /**
   * Tests that getGraphics returns not null when a graphics returns correctly.
   */
  @Test
  public void testGetGraphics() throws IOException {
    Graphics graphics = world.getGraphics(localDir);

    // Ensure graphics is not null (meaning the world map was generated correctly)
    assertNotNull(graphics);
  }

  /**
   * Tests that generateWorldMap correctly generates and saves the image file.
   */
  @Test
  public void testGenerateWorldMapValidPath() throws IOException {
    // Generate the world map with a valid path
    BufferedImage image = world.generateWorldMap(localDir);

    // Assert the image is not null and the file is created
    assertNotNull(image);

    // Check if the file is created and exists
    String filePath = localDir + "worldmap.png";
    File file = new File(filePath);
    assertTrue(file.exists());

    // Check if the file is non-empty (i.e., it contains data)
    assertTrue(file.length() > 0);
  }

  /**
   * Tests if two targets are equal using hash code.
   */
  @Test
  public void testEqualsAndHashCode() {

    // Test equals
    assertTrue(world.equals(world));
    assertTrue(world.equals(worldSame));
    assertFalse(world.equals(worldDiff));
    assertFalse(world.equals(null));
    assertFalse(world.equals("Invalid"));

    // Test hashCode
    assertEquals(world.hashCode(), worldSame.hashCode());
  }

}
