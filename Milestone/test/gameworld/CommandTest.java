package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import command.AttemptOnTargetCommand;
import command.Command;
import command.LookCommand;
import command.MoveCommand;
import command.MovePetCommand;
import command.PickUpCommand;
import item.Item;
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
    Command lookCommand = new LookCommand(mockWorld, out);
    lookCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the interaction with the world
    assertEquals("addPlayer called\n" + "getTurn called\n" + "Action: look, Room: 0, Item: null\n",
        log.toString());

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
    Command moveCommand = new MoveCommand(mockWorld, out, new Scanner(in));
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
    Command pickUpCommand = new PickUpCommand(mockWorld, out, new Scanner(in));
    pickUpCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the interaction with the world and output
    assertTrue(log.toString().contains("Action: pickup, Room: -1, Item: Revolver"));
    assertTrue(out.toString().contains("Items in the room:\n" + "Revolver (Damage: 50)\n"
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
    Command pickUpCommand = new PickUpCommand(mockWorld, out, new Scanner(in));
    pickUpCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Verify the output and log for unavailable item
    assertEquals("addPlayer called\n" + "getTurn called\n" + "getTurn called\n"
        + "Action: pickup, Room: -1, Item: Revolver\n", log.toString());
    assertTrue(out.toString()
        .contains("Invalid item name. Please enter full name of a valid item from the list."));
    assertTrue(out.toString().contains("Item not found in the room.\n"));
  }

  /**
   * Test MovePetCommand to ensure it prompts for a room and moves the pet
   * correctly.
   */
  @Test
  public void testExecuteMovePetCommand() throws IOException {
    StringReader in = new StringReader("1\n");
    mockWorld = new MockWorld(log, "Pet moved to room 1.");
    Command movePetCommand = new MovePetCommand(mockWorld, out, new Scanner(in));
    movePetCommand.execute();
    // System.out.println(log);
    // System.out.println(out);
    // Check that the correct move action was called in the world

    assertTrue(log.toString().contains("Action: movepet, Room: 1, Item: null"));

    // Verify the expected output
    String expectedOutput = "Select a room to move the pet to:\n" + "0: New Room\n"
        + "1: Neighbor\n" + "Enter the room number: Pet moved to room 1.\n";
    assertEquals(expectedOutput, out.toString());
  }

  /**
   * Test AttemptOnTargetCommand to ensure the player can successfully attack the
   * target with a valid item from inventory.
   */
  @Test
  public void testExecuteAttemptOnTargetWithValidItem() throws IOException {
    mockWorld = new MockWorld(log, "You attacked the target with Sword.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);

    // Add an item to player's inventory
    mockPlayer.pickUpItem(new Item(10, "Sword"));

    StringReader in = new StringReader("Sword\n");
    Command attemptCommand = new AttemptOnTargetCommand(mockWorld, out, new Scanner(in));
    attemptCommand.execute();

    // System.out.println(log);
    // System.out.println(out);

    // Verify the interaction with the world
    assertEquals("addPlayer called\n" + "getTurn called\n" + "attemptOnTarget called\n",
        log.toString());

    // Verify the expected output
    String expectedOutput = "Enter the valid item name to use for the attack or "
        + "press Enter to use the best item available: Attacked the target with Sword.\n";
    assertEquals(expectedOutput, out.toString());
  }

  /**
   * Test AttemptOnTargetCommand to ensure player is asked to input until they
   * provide a valid item name.
   */
  @Test
  public void testExecuteAttemptOnTargetWithMultipleInvalidInputs() throws IOException {
    mockWorld = new MockWorld(log, "You attacked the target with Dagger.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);

    // Add items to player's inventory
    mockPlayer.pickUpItem(new Item(20, "Dagger"));

    StringReader in = new StringReader("WrongItem\nAnotherWrong\nDagger\n");
    Command attemptCommand = new AttemptOnTargetCommand(mockWorld, out, new Scanner(in));
    attemptCommand.execute();

    // System.out.println(log);
    // System.out.println(out);

    // Verify the output prompts the user for multiple invalid entries
    assertTrue(out.toString().contains("Enter the valid item name to use for the attack or "
        + "press Enter to use the best item available: Invalid item name. "
        + "Please enter a valid item from your inventory or press Enter to use the best item.\n"));
    assertTrue(out.toString().contains("Enter the valid item name to use for the attack or "
        + "press Enter to use the best item available: Attacked the target with Dagger."));
  }

  /**
   * Test AttemptOnTargetCommand to ensure it handles attacking without any item
   * available.
   */
  @Test
  public void testExecuteAttemptOnTargetWithoutAnyItem() throws IOException {
    StringReader in = new StringReader("\n"); // Press Enter without specifying an item
    mockWorld = new MockWorld(log, "Poked the target in the eye for 1 damage.");
    PlayerInterface mockPlayer = new HumanPlayer("Human", mockWorld.getRooms().get(0), 2);
    mockWorld.addPlayer(mockPlayer, 0);

    // No items in the player's inventory

    Command attemptCommand = new AttemptOnTargetCommand(mockWorld, out, new Scanner(in));
    attemptCommand.execute();
    // System.out.println(log);
    // System.out.println(out);

    // Verify the output
    assertTrue(out.toString().contains("Poked the target in the eye for 1 damage."));
  }
}
