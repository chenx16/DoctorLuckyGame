package gameworld;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RoomInterfaceTest {

  private RoomInterface room;
  private ItemInterface item;

  @Before
  public void setUp() {
    int[] upperLeft = { 0, 0 };
    int[] lowerRight = { 2, 2 };
    room = new Room(upperLeft, lowerRight, "Armory", 0); // Use RoomInterface
    item = new Item(10, "Revolver"); // Use ItemInterface
  }

  @Test
  public void testConstructorValid() {
    // Test if the constructor correctly initializes the room
    assertEquals("Armory", room.getName());
    assertArrayEquals(new int[] { 0, 0 }, room.getCoordinateUpperLeft());
    assertArrayEquals(new int[] { 2, 2 }, room.getCoordinateLowerRight());
    assertEquals(0, room.getRoomInd());
  }

  @Test
  public void testAddItem() {
    // Test adding an item to the room
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertNotNull(items);
    assertTrue(items.contains(item)); // Check if the item is in the room
  }

  @Test
  public void testGetItems() {
    // Test retrieving items from the room
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertEquals(1, items.size()); // Only one item should be present
    assertEquals(item, items.get(0)); // The item should be the same we added
  }

  @Test
  public void testAddNeighbor() {
    // Test adding a neighbor to the room
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1);
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertTrue(neighbors.contains(neighborRoom)); // The neighbor should be added
  }

  @Test
  public void testGetNeighbors() {
    // Test retrieving neighbors from the room
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1);
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertEquals(1, neighbors.size()); // One neighbor should be present
    assertEquals(neighborRoom, neighbors.get(0)); // The neighbor should be correctly returned
  }

  @Test
  public void testGetRoomInd() {
    // Test retrieving the room index
    assertEquals(0, room.getRoomInd()); // The room index should be correctly returned
  }
}
