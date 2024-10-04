package gameworld;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Room class. These tests verify room behavior including
 * item handling and neighbor relationships.
 */

public class RoomInterfaceTest {

  private RoomInterface room;
  private ItemInterface item;

  /**
   * Sets up the test environment by creating a room and an item. This method runs
   * before each test.
   */
  @Before
  public void setUp() {
    int[] upperLeft = { 0, 0 };
    int[] lowerRight = { 2, 2 };
    room = new Room(upperLeft, lowerRight, "Armory", 0);
    item = new Item(10, "Revolver");
  }

  @Test
  public void testConstructorValid() {
    assertEquals("Armory", room.getName());
    assertArrayEquals(new int[] { 0, 0 }, room.getCoordinateUpperLeft());
    assertArrayEquals(new int[] { 2, 2 }, room.getCoordinateLowerRight());
    assertEquals(0, room.getRoomInd());
  }

  @Test
  public void testAddItem() {
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertNotNull(items);
    assertTrue(items.contains(item)); // Check if the item is in the room
  }

  @Test
  public void testGetItems() {
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertEquals(1, items.size()); // Only one item should be present
    assertEquals(item, items.get(0)); // The item should be the same we added
  }

  @Test
  public void testAddNeighbor() {
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1);
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertTrue(neighbors.contains(neighborRoom)); // The neighbor should be added
  }

  @Test
  public void testGetNeighbors() {
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1);
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertEquals(1, neighbors.size()); // One neighbor should be present
    assertEquals(neighborRoom, neighbors.get(0)); // The neighbor should be correctly returned
  }

  @Test
  public void testGetRoomInd() {
    assertEquals(0, room.getRoomInd()); // The room index should be correctly returned
  }

  /**
   * Tests the toString method when the item has a valid name and damage.
   */
  @Test
  public void testToStringValidItem() {
    String expectedOutput = "The item Revolver has 10 damage.";
    assertEquals(expectedOutput, item.toString());
  }

}
