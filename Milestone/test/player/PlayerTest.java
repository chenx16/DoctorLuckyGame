package player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import coordinate.Coordinate;
import item.Item;
import item.ItemInterface;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import room.Room;
import room.RoomInterface;

/**
 * Unit tests for the Player abstract class. Tested indirectly via HumanPlayer
 * and ComputerPlayer.
 */
public class PlayerTest {

  private PlayerInterface humanPlayer;
  private PlayerInterface computerPlayer;
  private RoomInterface startingRoom;
  private RoomInterface neighboringRoom;

  /**
   * Sets up the test environment by creating players, rooms and items for
   * testing.
   */
  @Before
  public void setUp() {
    startingRoom = new Room(new Coordinate(0, 0), new Coordinate(1, 1), "Starting Room", 0,
        new ArrayList<>(), new ArrayList<>());
    neighboringRoom = new Room(new Coordinate(2, 2), new Coordinate(3, 3), "Neighboring Room", 1,
        new ArrayList<>(), new ArrayList<>());

    startingRoom.addNeighbor(neighboringRoom);
    neighboringRoom.addNeighbor(startingRoom);

    humanPlayer = new HumanPlayer("Human", startingRoom, 2);
    computerPlayer = new ComputerPlayer("Computer", startingRoom, 2);
  }

  @Test
  public void testGetName() {
    assertEquals("Human", humanPlayer.getName());
    assertEquals("Computer", computerPlayer.getName());
  }

  @Test
  public void testGetCurrentRoom() {
    assertEquals("Starting Room", humanPlayer.getCurrentRoom().getName());
  }

  @Test
  public void testMoveTo() {
    humanPlayer.moveTo(neighboringRoom);
    assertEquals("Neighboring Room", humanPlayer.getCurrentRoom().getName());
  }

  @Test
  public void testPickUpItem() {
    ItemInterface item = new Item(5, "Sword");
    startingRoom.addItem(item);

    humanPlayer.pickUpItem(item);
    assertEquals(1, humanPlayer.getInventory().size());
    assertTrue(humanPlayer.getInventory().contains(item));
    assertFalse(startingRoom.getItems().contains(item));
  }

  @Test
  public void testInventoryLimit() {
    ItemInterface item1 = new Item(5, "Sword");
    ItemInterface item2 = new Item(10, "Shield");
    ItemInterface item3 = new Item(15, "Bow");

    startingRoom.addItem(item1);
    startingRoom.addItem(item2);
    startingRoom.addItem(item3);

    String description1 = humanPlayer.pickUpItem(item1);
    String expectedOutput1 = "Picked up Sword. 1 item(s) left to full inventory.\n";
    assertEquals(expectedOutput1, description1);

    String description2 = humanPlayer.pickUpItem(item2);
    String expectedOutput2 = "Picked up Shield. 0 item(s) left to full inventory.\n";
    assertEquals(expectedOutput2, description2);

    String description3 = humanPlayer.pickUpItem(item3);
    // Should not be picked up since max items = 2
    String expectedOutput3 = "Cannot pick up item. Inventory is full.\n";
    assertEquals(expectedOutput3, description3);

    assertEquals(2, humanPlayer.getInventory().size());
    assertFalse(humanPlayer.getInventory().contains(item3));

  }

  @Test
  public void testLookAround() {
    ItemInterface item = new Item(5, "Sword");
    startingRoom.addItem(item);
    String description = humanPlayer.lookAround();
    String expectedOutput = "\n" + "Starting Room contains: [Sword with 5 damage.]";
    assertEquals(expectedOutput, description);
  }

  @Test
  public void testGetDescription() {
    String description = humanPlayer.getDescription();
    String expectedOutput = "It's Human's turn.\n" + "You are in: Starting Room\n"
        + "There are 1 neighboring rooms.\n" + "Inventory: No items";
    assertEquals(expectedOutput, description);
  }

  @Test
  public void testToString() {
    assertEquals("Player Human in Starting Room with items: []", humanPlayer.toString());
  }

  @Test
  public void testRemoveItem() {
    ItemInterface item = new Item(5, "Sword");
    startingRoom.addItem(item);
    humanPlayer.pickUpItem(item);
    assertEquals(1, humanPlayer.getInventory().size());
    humanPlayer.removeItem(item);
    assertEquals(0, humanPlayer.getInventory().size());
  }

  @Test
  public void testCanSeePlayerInSameRoom() {
    humanPlayer.moveTo(startingRoom);
    computerPlayer.moveTo(startingRoom);

    assertTrue(humanPlayer.canSeePlayer(computerPlayer));
    assertTrue(computerPlayer.canSeePlayer(humanPlayer));
  }

  @Test
  public void testCanSeePlayerInNeighboringRoom() {
    humanPlayer.moveTo(startingRoom);
    computerPlayer.moveTo(neighboringRoom);

    assertTrue(humanPlayer.canSeePlayer(computerPlayer));
    assertTrue(computerPlayer.canSeePlayer(humanPlayer));
  }

  @Test
  public void testCanNotSeePlayer() {
    RoomInterface otherRoom = new Room(new Coordinate(4, 4), new Coordinate(5, 5), "Other Room", 2,
        new ArrayList<>(), new ArrayList<>());

    humanPlayer.moveTo(startingRoom);
    computerPlayer.moveTo(otherRoom);

    assertFalse(humanPlayer.canSeePlayer(computerPlayer));
    assertFalse(computerPlayer.canSeePlayer(humanPlayer));
  }

  @Test
  public void testGetMaxItems() {
    assertEquals(2, humanPlayer.getMaxItems());
    assertEquals(2, computerPlayer.getMaxItems());
  }

  @Test
  public void testGetIsComputerControlled() {
    assertFalse(humanPlayer.getIsComputerControlled());
    assertTrue(computerPlayer.getIsComputerControlled());
  }

  @Test
  public void testHashCodeAndEquals() {
    PlayerInterface anotherHumanPlayer = new HumanPlayer("Human", startingRoom, 2);

    // Test if two HumanPlayers with the same properties are considered equal
    assertEquals(humanPlayer, anotherHumanPlayer);
    assertEquals(humanPlayer, humanPlayer);
    assertEquals(humanPlayer.hashCode(), anotherHumanPlayer.hashCode());

    // Test if a HumanPlayer and a ComputerPlayer with the same properties are not
    // equal
    PlayerInterface anotherComputerPlayer = new ComputerPlayer("Human", startingRoom, 2);
    assertNotEquals(humanPlayer, anotherComputerPlayer);
    assertNotEquals(humanPlayer, computerPlayer);

    // Test if two ComputerPlayers with the same properties are equal
    PlayerInterface anotherComputerPlayerSame = new ComputerPlayer("Computer", startingRoom, 2);
    assertEquals(computerPlayer, anotherComputerPlayerSame);
    assertEquals(computerPlayer, computerPlayer);
    assertEquals(computerPlayer.hashCode(), anotherComputerPlayerSame.hashCode());

    // Test null and different types
    assertFalse(humanPlayer.equals(null));
    assertFalse(humanPlayer.equals("Not a Player"));
  }

}
