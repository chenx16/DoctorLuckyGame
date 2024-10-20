package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import coordinate.Coordinate;
import item.Item;
import item.ItemInterface;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.Room;
import room.RoomInterface;

/**
 * Unit tests for the Room class. These tests verify room behavior including
 * item handling and neighbor relationships.
 */

public class RoomInterfaceTest {

  private RoomInterface room;
  private RoomInterface room1;
  private RoomInterface room2;
  private RoomInterface room3;
  private RoomInterface roomPlayer;
  private RoomInterface roomWithDifferentName;
  private RoomInterface roomWithDifferentCoordinates;
  private ItemInterface item;
  private ItemInterface item1;
  private ItemInterface item2;
  private PlayerInterface player1;
  private PlayerInterface player2;

  /**
   * Sets up the test environment by creating rooms and items for testing.
   */
  @Before
  public void setUp() {
    item = new Item(10, "Revolver");
    item1 = new Item(5, "Sword");
    item2 = new Item(3, "Shield");

    room = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Armory", 0,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create two rooms with the same coordinates and name
    room1 = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Armory", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room2 = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Armory", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a room with a different name but same coordinates
    roomWithDifferentName = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Library", 2,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a room with different coordinates but same name
    roomWithDifferentCoordinates = new Room(new Coordinate(3, 3), new Coordinate(5, 5), "Armory", 3,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Create a completely different room
    room3 = new Room(new Coordinate(3, 3), new Coordinate(5, 5), "Library", 2,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    roomPlayer = new Room(new Coordinate(5, 5), new Coordinate(7, 7), "Test Room", 0,
        new ArrayList<>(), new ArrayList<>());
    player1 = new HumanPlayer("Player1", room, 5);
    player2 = new ComputerPlayer("Player2", room, 5);
  }

  /**
   * Tests the room constructor to verify valid coordinates, name, and index.
   */
  @Test
  public void testConstructorValid() {
    assertEquals("Armory", room.getName());
    assertEquals(new Coordinate(0, 0), room.getCoordinateUpperLeft());
    assertEquals(new Coordinate(2, 2), room.getCoordinateLowerRight());
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
    RoomInterface neighborRoom = new Room(new Coordinate(0, 3), new Coordinate(2, 5),
        "Billiard Room", 1, new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.getListofNeighbors();
    assertTrue(neighbors.contains(neighborRoom)); // The neighbor should be added
  }

  /**
   * Tests getting neighbors for the room.
   */
  @Test
  public void testGetNeighbors() {
    RoomInterface neighborRoom = new Room(new Coordinate(0, 3), new Coordinate(2, 5),
        "Billiard Room", 1, new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room.addNeighbor(neighborRoom);
    List<RoomInterface> neighbors = room.getListofNeighbors();
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
   * Tests getting players of the room.
   */
  @Test
  public void testGetPlayers() {
    // Initially, the room has no players
    assertEquals(0, roomPlayer.getPlayers().size());

    // Add players to the room
    roomPlayer.addPlayer(player1);
    roomPlayer.addPlayer(player2);

    List<PlayerInterface> playersInRoom = roomPlayer.getPlayers();
    assertEquals(2, playersInRoom.size());
    assertEquals("Player1", playersInRoom.get(0).getName());
    assertEquals("Player2", playersInRoom.get(1).getName());

    // Remove a player and check again
    roomPlayer.removePlayer(player1);
    playersInRoom = roomPlayer.getPlayers();
    assertEquals(1, playersInRoom.size());
    assertEquals("Player2", playersInRoom.get(0).getName());
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

  /**
   * Test the description of an empty room with no items, players, or neighbors.
   */
  @Test
  public void testEmptyRoomDescription() {
    String expectedDescription = "Room: Armory\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "This room has no neighboring rooms.\n";

    assertEquals(expectedDescription, room.getRoomDescription());
  }

  /**
   * Test the description of a room with items, players, and neighbors.
   */
  @Test
  public void testRoomWithItemsAndPlayers() {
    // Add items and players to the room
    room.addItem(item1);
    room.addItem(item2);
    room.addPlayer(player1);
    room.addPlayer(player2);
    room.addNeighbor(room1);

    // Test the description of a room with items, players, and neighbors
    String expectedDescription = "Room: Armory\n" + "Items in this room:\n"
        + "- Item Sword with 5 damage.\n" + "- Item Shield with 3 damage.\n"
        + "Players in room: Player1 Player2 \n" + "Neighboring rooms:\n" + "- Armory\n\n";

    assertEquals(expectedDescription, room.getRoomDescription());
  }

  /**
   * Test the description of a room with only items.
   */
  @Test
  public void testRoomWithOnlyItems() {
    // Add only items to the room
    room.addItem(item1);

    String expectedDescription = "Room: Armory\n" + "Items in this room:\n"
        + "- Item Sword with 5 damage.\n" + "Players in room: No players in this room.\n"
        + "This room has no neighboring rooms.\n";

    assertEquals(expectedDescription, room.getRoomDescription());
  }

  /**
   * Test the description of a room with only players.
   */
  @Test
  public void testRoomWithOnlyPlayers() {
    // Add only players to the room
    room.addPlayer(player1);

    String expectedDescription = "Room: Armory\n" + "No items in this room.\n"
        + "Players in room: Player1 \n" + "This room has no neighboring rooms.\n";

    assertEquals(expectedDescription, room.getRoomDescription());
  }

  /**
   * Test the description of a room with no neighbors.
   */
  @Test
  public void testRoomWithNoNeighbors() {
    // Create a room with no neighbors
    RoomInterface isolatedRoom = new Room(new Coordinate(0, 0), new Coordinate(2, 2),
        "Isolated Room", 2, new ArrayList<>(), new ArrayList<>());

    String expectedDescription = "Room: Isolated Room\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "This room has no neighboring rooms.\n";

    assertEquals(expectedDescription, isolatedRoom.getRoomDescription());
  }
}
