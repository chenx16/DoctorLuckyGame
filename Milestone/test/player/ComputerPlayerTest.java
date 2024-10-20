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
 * Unit tests for the ComputerPlayer class.
 */
public class ComputerPlayerTest {

  private ComputerPlayer computerPlayer;
  private RoomInterface startingRoom;
  private RoomInterface neighborRoom;
  private RoomInterface finalRoom;
  private ItemInterface sword;

  /**
   * Sets up the test environment by creating players, rooms and items for
   * testing.
   */
  @Before
  public void setUp() {
    startingRoom = new Room(new Coordinate(0, 0), new Coordinate(1, 1), "Starting Room", 0,
        new ArrayList<>(), new ArrayList<>());
    neighborRoom = new Room(new Coordinate(2, 2), new Coordinate(3, 3), "New Room", 1,
        new ArrayList<>(), new ArrayList<>());
    finalRoom = new Room(new Coordinate(4, 4), new Coordinate(5, 5), "Final Room", 2,
        new ArrayList<>(), new ArrayList<>());

    // Add neighbor to starting room
    startingRoom.addNeighbor(neighborRoom);
    // neighborRoom.addNeighbor(startingRoom);
    neighborRoom.addNeighbor(finalRoom);
    // finalRoom.addNeighbor(neighborRoom);

    sword = new Item(5, "Sword");
    startingRoom.addItem(sword);

    computerPlayer = new ComputerPlayer("Computer", startingRoom, 2);
  }

  @Test
  public void testLookAround() {
    String action = computerPlayer.takeTurn();
    assertTrue(action.contains("looks around"));
    assertFalse(computerPlayer.getInventory().contains(sword)); // No item picked up in look around
    String expectedOutput = "Computer looks around: Starting Room "
        + "contains: [Item Sword with 5 damage.]\n";
    assertEquals(expectedOutput, action);
  }

  @Test
  public void testPickUpItem() {
    computerPlayer.takeTurn(); // Look around
    String action = computerPlayer.takeTurn(); // Pick up item
    assertTrue(action.contains("picked up Sword"));
    String expectedOutput = "Computer picked up Sword\n";
    assertEquals(expectedOutput, action);
  }

  @Test
  public void testMoveToNeighbor() {
    computerPlayer.takeTurn(); // Look around
    computerPlayer.takeTurn(); // Pick up item
    String action = computerPlayer.takeTurn(); // Move to neighbor
    assertTrue(action.contains("moved to"));
    String expectedOutput = "Computer moved to New Room\n";
    assertEquals(expectedOutput, action);
  }

  @Test
  public void testMoveToNextRoomNoItemPickup() {
    computerPlayer.takeTurn(); // Look around
    computerPlayer.takeTurn(); // Pick up item
    computerPlayer.takeTurn(); // Move to neighbor

    computerPlayer.takeTurn(); // Look around again
    // No item in this room so the player should just move to next move
    String action = computerPlayer.takeTurn(); // Move to neighbor
    assertTrue(action.contains("moved to"));
    String expectedOutput = "Computer moved to Final Room\n";
    assertEquals(expectedOutput, action);
  }

  @Test
  public void testResetTurn() {
    computerPlayer.takeTurn(); // Look around
    computerPlayer.takeTurn(); // Pick up item
    computerPlayer.takeTurn(); // Move to neighbor

    // After moving, the next action should be looking around again (turn reset)
    String action = computerPlayer.takeTurn(); // Look around again\
    assertTrue(action.contains("looks around"));
    String expectedOutput = "Computer looks around: New Room contains: []\n";
    assertEquals(expectedOutput, action);
  }
}
