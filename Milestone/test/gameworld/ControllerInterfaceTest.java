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

  // Test to add a human player and ensure output is updated
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

  // Test to add a computer player
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

  // Test turn execution (look action)
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

  // Test handling of human player actions (move)
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

  // Test handling of quitting the game
  @Test
  public void testQuitGame() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nq\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 10, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString().contains("Exiting the game."));
  }

  // Test game over when maximum turns are reached
  @Test
  public void testGameOverAtMaxTurns() throws IOException {
    StringReader in = new StringReader("Human Player\n0\nl\n");
    controller = new Controller(in, out);
    controller.start(mockWorld, 1, new Random(1));
    // System.out.println(log);
    // System.out.println(out);

    assertTrue(out.toString()
        .contains("Game over! Unfortunately, the maximum number of turns is reached."));
  }

  // Test handling of invalid action input
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
    assertTrue(out.toString().contains("It's Human Player's turn.\n" + "You are in: New Room\n"
        + "There are 1 neighboring rooms.\n" + "Inventory: No itemsnull"));
  }

  // Test moving the pet to a new room using the MovePetCommand
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

}