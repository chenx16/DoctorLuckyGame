package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import command.LookCommand;
import command.MoveCommand;
import command.PickUpCommand;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import mockmodel.MockWorld;
import org.junit.Before;
import org.junit.Test;
import player.HumanPlayer;
import player.PlayerInterface;

/**
 * CommandTest class contains unit tests for command classes in the game,
 * including LookCommand, MoveCommand, and PickUpCommand. These tests verify
 * that each command interacts correctly with the WorldInterface, handles
 * outputs, and processes player actions.
 */
public class CommandTest {
  private WorldInterface mockWorld;
  private StringWriter out;
  private StringBuilder log;

  /**
   * Sets up the test environment by initializing the mock world, input, output,
   * and controller. This method is executed before each test case.
   *
   * @throws IOException if an I/O error occurs during setup.
   */
  @Before
  public void setUp() throws IOException {
    out = new StringWriter();
    log = new StringBuilder();
  }

  /**
   * Test LookCommand to ensure it correctly interacts with the world and outputs
   * the room description.
   */
  @Test
  public void testExecuteLookCommand() throws IOException {
    mockWorld = new MockWorld(log, "You are in a peaceful room with no items.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);
    LookCommand lookCommand = new LookCommand(mockWorld, mockPlayer, out);
    lookCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the interaction with the world
    assertEquals("addPlayer called\n" + "Action: look, Room: -1, Item: null\n", log.toString());

    // Verify the output
    assertEquals("You are in a peaceful room with no items.\n", out.toString());
  }

  /**
   * Test MoveCommand to ensure it prompts for a room and moves the player
   * correctly.
   */
  @Test
  public void testExecuteMoveCommand() throws IOException {
    StringReader in = new StringReader("1\n");
    mockWorld = new MockWorld(log, "Moved to room 0.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);
    MoveCommand moveCommand = new MoveCommand(mockWorld, mockPlayer, out, new Scanner(in));
    moveCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Check that the correct move action was called in the world
    assertTrue(log.toString().contains("Action: move, Room: 1, Item: null\n"));

    // Verify the expected output
    String expectedOutput = "Neighboring rooms:\n" + "1: Neighbor\n"
        + "Enter the room index to move to: Moved to room 0.\n";
    assertEquals(expectedOutput, out.toString());
  }

  /**
   * Test PickUpCommand to ensure it handles picking up an available item
   * correctly.
   */
  @Test
  public void testExecutePickUpCommandWithAvailableItem() throws IOException {
    StringReader in = new StringReader("revolver\n");
    mockWorld = new MockWorld(log, "Revolver picked up successfully.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);
    PickUpCommand pickUpCommand = new PickUpCommand(mockWorld, mockPlayer, out, new Scanner(in));
    pickUpCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the interaction with the world and output
    assertTrue(log.toString().contains("Action: pickup, Room: -1, Item: Revolver"));
    assertTrue(out.toString().contains("Items in the room:\n" + "Revolver (Damage: 10)\n"
        + "Enter the item name to pick up: Revolver picked up successfully.\n"));
  }

  /**
   * Test PickUpCommand to ensure it handles attempting to pick up an unavailable
   * item.
   */
  @Test
  public void testExecutePickUpCommandWithUnavailableItem() throws IOException {
    StringReader in = new StringReader("nonexistent\n revolver\n");
    mockWorld = new MockWorld(log, "Item not found in the room.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);
    PickUpCommand pickUpCommand = new PickUpCommand(mockWorld, mockPlayer, out, new Scanner(in));
    pickUpCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the output and log for unavailable item
    assertEquals("addPlayer called\n" + "Action: pickup, Room: -1, Item: Revolver\n",
        log.toString());
    assertTrue(out.toString()
        .contains("Invalid item name. Please enter full name of a valid item from the list."));
    assertTrue(out.toString().contains("Item not found in the room.\n"));
  }
}
