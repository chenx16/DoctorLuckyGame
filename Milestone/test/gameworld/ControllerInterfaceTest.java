package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.Controller;
import controller.ControllerInterface;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import mockmodel.MockWorld;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ControllerInterface} class. This class tests the
 * interaction between the {@link Controller} and the {@link WorldInterface}
 * using mock data and input/output handling via {@link StringReader} and
 * {@link StringWriter}.
 */
public class ControllerInterfaceTest {

  private WorldInterface mockWorld;
  private ControllerInterface controller;
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
    mockWorld = new MockWorld(log, "Mocked model response\n");
  }

  @Test
  public void testStartGame() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 10, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertEquals(log.toString(), "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n"
        + "getTurn called\n" + "getTargetLocationHint called\n" + "getPet called\n");
    assertTrue(out.toString().contains("Human player Human Player added to the game."));
    assertTrue(out.toString().contains("Turn number: 1/10"));
  }

  @Test
  public void testControllerWithMockModel() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertEquals(log.toString(),
        "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n" + "getTurn called\n"
            + "getTargetLocationHint called\n" + "getPet called\n" + "getTurn called\n"
            + "Action: look, Room: 0, Item: null\n" + "moveTargetCharacter called\n"
            + "wanderPet called\n" + "getTurn called\n" + "getTargetLocationHint called\n"
            + "getPet called\n");
    assertTrue(out.toString().contains("Mocked model response\n"));

  }

  /** Test to add a human player and ensure output is updated. */
  @Test
  public void testAddHumanPlayer() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertEquals(log.toString(), "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n"
        + "getTurn called\n" + "getTargetLocationHint called\n" + "getPet called\n");
    assertTrue(out.toString().contains("Human player Human Player added to the game."));
  }

  /** Test to add a computer player. */
  @Test
  public void testAddComputerPlayer() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 1, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Mocked model response\n"));
    assertTrue(out.toString()
        .contains("Computer-controlled player AI Player added to the game, starting in "));
  }

  @Test
  public void testAddPlayersRandomOrder() throws IOException {

    StringReader in = new StringReader("Human\n0\nl\nq\n");
    controller = new Controller(in, out);
    // Start game with mock world
    controller.start(mockWorld, 3, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    // Check if players were added in the expected order
    String output = out.toString();
    assertTrue(output.contains("Human player Human added to the game."));
    assertTrue(output.contains("Computer-controlled player AI Player added to the game"));

    // Verify log or output based on the expected order
    String expectedLog = "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n";
    assertEquals(expectedLog, log.toString().substring(0, expectedLog.length()));
  }

  /** Test turn execution (look action). */
  @Test
  public void testTurnLookAction() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertEquals(log.toString(),
        "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n" + "getTurn called\n"
            + "getTargetLocationHint called\n" + "getPet called\n" + "getTurn called\n"
            + "Action: look, Room: 0, Item: null\n" + "moveTargetCharacter called\n"
            + "wanderPet called\n" + "getTurn called\n" + "getTargetLocationHint called\n"
            + "getPet called\n");
    assertTrue(out.toString().contains("Mocked model response"));
  }

  /** Test handling of human player actions (move). */
  @Test
  public void testHandleMoveAction() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nm\n1\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertEquals(log.toString(),
        "addPlayer called\n" + "addPlayer called\n" + "wanderPet called\n" + "getTurn called\n"
            + "getTargetLocationHint called\n" + "getPet called\n" + "getTurn called\n"
            + "Action: move, Room: 1, Item: null\n" + "moveTargetCharacter called\n"
            + "wanderPet called\n" + "getTurn called\n" + "getTargetLocationHint called\n"
            + "getPet called\n");
    assertTrue(out.toString().contains("Enter the room index to move to: Mocked model response"));
  }

  /** Test handling of quitting the game. */
  @Test
  public void testQuitGame() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 10, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Exiting the game."));
  }

  @Test
  public void testExecutePickup() throws IOException {
    // Setting up a scenario where the human player is in the same room as the
    // target
    StringReader in = new StringReader("Human Player\n0\np\nRevolver\nl\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 3, new Random(1));

    // System.out.println(log);
    // System.out.println(out);
    // Checking the output to confirm that the target was killed
    assertTrue(out.toString().contains(
        "Choose an action: [l: look, p: pickup, m: move, mp: move pet, a: attack, q: quit]"));
    assertTrue(out.toString().contains("Items in the room:\n" + "Revolver (Damage: 50)"));
    assertTrue(out.toString().contains("Enter the item name to pick up: "));

  }

  /** Test game over when maximum turns are reached. */
  @Test
  public void testGameOverAtMaxTurns() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 1, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString()
        .contains("Game over! Unfortunately, the maximum number of turns is reached."));
    assertTrue(
        out.toString().contains("The target character escapes and runs away to live another day"));
  }

  /** Test handling of invalid action input. */
  @Test
  public void testInvalidActionInput() throws IOException {
    StringReader in = new StringReader("Human Player\n0\ni\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Exiting the game."));
    assertTrue(
        out.toString().contains("Invalid action. Please enter 'l', 'p', 'm', 'mp', or 'a'."));
  }

  @Test
  public void testShowPlayerDescription() throws IOException {
    StringReader in = new StringReader("Human Player\n0\ni\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 10, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Exiting the game."));
    assertTrue(out.toString()
        .contains("It's Human Player's turn.\n" + "You are in: New Room\n"
            + "There are 1 neighboring rooms.\n" + "Inventory: No items\n"
            + "The target might be near New Room.\n" + "Pet Fortune the Cat is in: Neighbor\n"));
  }

  /** Test moving the pet to a new room using the MovePetCommand. */
  @Test
  public void testMovePetCommand() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nmp\n1\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 5, new Random(1));
    // System.out.println(log);
    // System.out.println(out);
    assertTrue(log.toString().contains("wanderPet called\n"));
    assertTrue(out.toString().contains("Select a room to move the pet to:\n" + "0: New Room\n"
        + "1: Neighbor\n" + "Enter the room number: Mocked model response"));
  }

  /**
   * Test handling of the attack action when the target is in the same room.
   */
  @Test
  public void testAttackTargetSuccess() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\na\n\nl\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 5, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Attempting to attack the target..."));
    assertTrue(out.toString().contains("Poked the target in the eye for 1 damage."));
  }

  /**
   * Test attack command when the target is not in the same room as the player.
   */
  @Test
  public void testAttackTargetFailureWhenNotInRoom() throws IOException {
    StringReader in = new StringReader("Human Player\n1\na\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 2, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(
        out.toString().contains("The target is not in your current room. You cannot attack."));
  }

  /**
   * Test the scenario where the game ends because the maximum number of turns is
   * reached.
   */
  @Test
  public void testGameEndsAfterMaxTurnsReached() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\nm\n1\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 1, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString()
        .contains("Game over! Unfortunately, the maximum number of turns is reached."));
    assertTrue(
        out.toString().contains("The target character escapes and runs away to live another day"));
  }

  @Test
  public void testExecuteAttemptOnTargetCommandLethalAttack() throws IOException {
    // Setting up a scenario where the human player is in the same room as the
    // target
    StringReader in = new StringReader("Human Player\n0\nl\na\nRevolver\nl\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 5, new Random(1));

    // System.out.println(log);
//    System.out.println(out);
    // Checking the output to confirm that the target was killed
    assertTrue(out.toString().contains("Attempting to attack the target..."));
    assertTrue(out.toString().contains("Target has been killed!"));
  }

}