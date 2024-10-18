package player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coordinate.Coordinate;
import item.Item;
import item.ItemInterface;
import room.Room;
import room.RoomInterface;

/**
 * Unit tests for the Player abstract class. Tested indirectly via HumanPlayer
 * and ComputerPlayer.
 */
public class PlayerTest {

  private HumanPlayer humanPlayer;
  private ComputerPlayer computerPlayer;
  private RoomInterface startingRoom;

  /**
   * Sets up the test environment by creating players, rooms and items for
   * testing.
   */
  @Before
  public void setUp() {
    startingRoom = new Room(new Coordinate(0, 0), new Coordinate(1, 1), "Starting Room", 0,
        new ArrayList<>(), new ArrayList<>());
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
    RoomInterface newRoom = new Room(new Coordinate(2, 2), new Coordinate(3, 3), "New Room", 1,
        new ArrayList<>(), new ArrayList<>());
    humanPlayer.moveTo(newRoom);
    assertEquals("New Room", humanPlayer.getCurrentRoom().getName());
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
    String expectedOutput = "Starting Room contains: [Item Sword with 5 damage.]";
    assertEquals(expectedOutput, description);
  }

  @Test
  public void testGetDescription() {
    String description = humanPlayer.getDescription();
    assertTrue(description.contains("Player Name: Human"));
    assertTrue(description.contains("Current Room: Starting Room"));
    assertTrue(description.contains("Inventory: No items"));
  }

  @Test
  public void testToString() {
    assertEquals("Player Human in Starting Room with items: []", humanPlayer.toString());
  }
}
