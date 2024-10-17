package gameworld;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Room class. These tests verify room behavior including
 * item handling and neighbor relationships.
 */

public class RoomInterfaceTest {

  private RoomInterface room;
  private RoomInterface room1;
  private RoomInterface room2;
  private RoomInterface room3;
  private RoomInterface roomWithDifferentName;
  private RoomInterface roomWithDifferentCoordinates;
  private ItemInterface item;

  /**
   * Sets up the test environment by creating rooms and items for testing.
   */
  @Before
  public void setUp() {
    int[] upperLeft = { 0, 0 };
    int[] lowerRight = { 2, 2 };
    room = new Room(upperLeft, lowerRight, "Armory", 0, new ArrayList<ItemInterface>(),
        new ArrayList<RoomInterface>());
    item = new Item(10, "Revolver");

    // Create two rooms with the same coordinates and name
    room1 = new Room(new int[] { 0, 0 }, new int[] { 2, 2 }, "Armory", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room2 = new Room(new int[] { 0, 0 }, new int[] { 2, 2 }, "Armory", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a room with a different name but same coordinates
    roomWithDifferentName = new Room(new int[] { 0, 0 }, new int[] { 2, 2 }, "Library", 2,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a room with different coordinates but same name
    roomWithDifferentCoordinates = new Room(new int[] { 3, 3 }, new int[] { 5, 5 }, "Armory", 3,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a completely different room
    room3 = new Room(new int[] { 3, 3 }, new int[] { 5, 5 }, "Library", 2,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
  }

  /**
   * Tests the room constructor to verify valid coordinates, name, and index.
   */
  @Test
  public void testConstructorValid() {
    assertEquals("Armory", room.getName());
    assertArrayEquals(new int[] { 0, 0 }, room.getCoordinateUpperLeft());
    assertArrayEquals(new int[] { 2, 2 }, room.getCoordinateLowerRight());
    assertEquals(0, room.getRoomInd());
  }

  /**
   * Tests adding an item to the room. Verifies that the item is correctly added
   * to the room's item list.
   */
  @Test
  public void testAddItem() {
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertNotNull(items);
    assertTrue(items.contains(item)); // Check if the item is in the room
  }

  /**
   * Tests getting items from the room. Ensures that the correct items are
   * retrieved.
   */
  @Test
  public void testGetItems() {
    room.addItem(item);
    List<ItemInterface> items = room.getItems();
    assertEquals(1, items.size()); // Only one item should be present
    assertEquals(item, items.get(0)); // The item should be the same we added
  }

  /**
   * Tests adding a neighboring room to the room.
   */
  @Test
  public void testAddNeighbor() {
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1, new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertTrue(neighbors.contains(neighborRoom)); // The neighbor should be added
  }

  /**
   * Tests getting neighbors for the room.
   */
  @Test
  public void testGetNeighbors() {
    RoomInterface neighborRoom = new Room(new int[] { 0, 3 }, new int[] { 2, 5 }, "Billiard Room",
        1, new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.myListofNeighbors();
    assertEquals(1, neighbors.size()); // One neighbor should be present
    assertEquals(neighborRoom, neighbors.get(0)); // The neighbor should be correctly returned
  }

  /**
   * Tests getting index of the room.
   */
  @Test
  public void testGetRoomInd() {
    assertEquals(0, room.getRoomInd()); // The room index should be correctly returned
  }

  /**
   * Tests the toString method when the item has a valid name and damage.
   */
  @Test
  public void testToStringValidItem() {
    String expectedOutput = "Item Revolver with 10 damage.";
    assertEquals(expectedOutput, item.toString());
  }

  /**
   * Tests that two rooms with the same coordinates and name are equal.
   */
  @Test
  public void testEqualsSameRooms() {
    assertTrue(room1.equals(room2));
    assertTrue(room2.equals(room1));
  }

  /**
   * Tests that two rooms are different.
   */
  @Test
  public void testEqualsDifferent() {
    assertFalse(room1.equals(roomWithDifferentCoordinates));
    assertFalse(room1.equals(roomWithDifferentName));
    assertFalse(room1.equals(room3));
    assertFalse(room1.equals(null));
    assertFalse(room1.equals("NotARoom"));
  }

  /**
   * Tests that two equal rooms have the same hash code.
   */
  @Test
  public void testHashCodeSameRooms() {
    assertEquals(room1.hashCode(), room2.hashCode());
  }

  /**
   * Tests that rooms with have different hash codes.
   */
  @Test
  public void testHashCodeDifferent() {
    assertNotEquals(room1.hashCode(), roomWithDifferentName.hashCode());
    assertNotEquals(room1.hashCode(), roomWithDifferentCoordinates.hashCode());
    assertNotEquals(room1.hashCode(), room3.hashCode());
  }

}
