package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WorldInterfaceTest {

  private World world;

  @Before
  public void setUp() throws Exception {
    world = new World();
    String localDir = System.getProperty("user.dir");
    // Assuming the test world file is correctly structured for testing
    world.loadFromFile(localDir + "/res/mansion.txt");
  }

  @Test
  public void testLoadFromFileValid() {
    assertNotNull(world.getRooms());
    assertNotNull(world.getItems());
    assertNotNull(world.getTargetCharacter());
    assertEquals(36, world.getRowAndCol()[0][0]);
    assertEquals(30, world.getRowAndCol()[0][1]);
    assertEquals("Doctor Lucky's Mansion", world.getName());
  }

  @Test(expected = IOException.class)
  public void testLoadFromFileInvalidPath() throws Exception {
    world.loadFromFile("invalidpath.txt");
  }

  @Test
  public void testGetNeighbors() {
    RoomInterface kitchen = world.getRooms().get(0); // Assume kitchen is room 0
    List<RoomInterface> neighbors = world.getNeighbors(kitchen);
    assertNotNull(neighbors);
    assertTrue(neighbors.size() > 0); // Should have some neighbors
  }

  @Test
  public void testGetSpaceInfo() {
    RoomInterface armory = world.getRooms().get(0); // Assuming room 0 is Armory
    String info = world.getSpaceInfo(armory);
    assertTrue(info.contains("Armory"));
    assertTrue(info.contains("contains"));
  }

  @Test
  public void testMoveTargetCharacter() {
    RoomInterface initialRoom = world.getTargetCharacter().getCurrentRoom();
    world.moveTargetCharacter();
    RoomInterface newRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(initialRoom, newRoom);
  }

  @Test
  public void testGenerateWorldMap() {
    world.generateWorldMap();
    assertNotNull(world.generateWorldMap());
  }

  @Test
  public void testGetGraphics() {
    // Ensure that getGraphics() returns a valid Graphics object
    Graphics graphics = world.getGraphics();
    assertNotNull(graphics); // Ensure that the returned Graphics object is not null
  }

  @Test
  public void testGraphicsDrawsWorld() {
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
  public void testBufferedImageDimensions() {
    // Check if the generated BufferedImage has the expected dimensions
    BufferedImage image = world.generateWorldMap();
    assertNotNull(image);

    int expectedWidth = world.getRowAndCol()[0][1] * 50; // Assuming each cell is 50px wide
    int expectedHeight = world.getRowAndCol()[0][0] * 50; // Assuming each cell is 50px tall
    assertEquals(expectedWidth, image.getWidth());
    assertEquals(expectedHeight, image.getHeight());
  }
}
