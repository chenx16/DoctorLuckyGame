//package gameworld;
//
//import static org.junit.Assert.assertTrue;
//
//import controller.Controller;
//import controller.ControllerInterface;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.StringWriter;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Unit tests for the {@link ControllerInterface} class. This class tests the
// * interaction between the {@link Controller} and the {@link WorldInterface}
// * using mock data and input/output handling via {@link StringReader} and
// * {@link StringWriter}.
// */
//public class ControllerInterfaceTest {
//
//  private WorldInterface world;
//  private StringReader input;
//  private StringWriter output;
//  private final String localDir = "./res/";
//  private ControllerInterface controller;
//
//  /**
//   * Sets up the test environment by initializing the mock world, input, output,
//   * and controller. This method is executed before each test case.
//   *
//   * @throws IOException if an I/O error occurs during setup.
//   */
//  @Before
//  public void setUp() throws IOException {
//    world = new World();
//    // Assuming the test world file is correctly structured for testing
//    File worldFile = new File(localDir + "mansion.txt");
//    FileReader fileReader1 = new FileReader(worldFile);
//    world.loadFromFile(fileReader1);
//    output = new StringWriter();
//  }
//
//  @Test
//  public void testStartGame() throws IOException {
//    // Simulate inputs: 3 turns, human player name, room index 0, action 'l' (look),
//    // and quit 'q'.
//    input = new StringReader("3\nHuman Player\n0\nl\nq\n");
//
//    // Create the controller with mock world, input, and output.
//    controller = new Controller(world, input, output);
//
//    // Start the game
//    controller.startGame();
//
//    // Verify the output from the controller
//    assertTrue(output.toString().contains(
//        "Enter the maximum number of turns allowed: Saving the world map to a PNG file...\n"
//            + "World map saved successfully!\n" + "Adding players to the game...\n"
//            + "Enter the name for the human player: Available rooms:\n" + "0: Armory\n"
//            + "1: Billiard Room\n" + "2: Carriage House\n" + "3: Dining Hall\n"
//            + "4: Drawing Room\n" + "5: Foyer\n" + "6: Green House\n" + "7: Hedge Maze\n"
//            + "8: Kitchen\n" + "9: Lancaster Room\n" + "10: Library\n" + "11: Lilac Room\n"
//            + "12: Master Suite\n" + "13: Nursery\n" + "14: Parlor\n" + "15: Piazza\n"
//            + "16: Servants' Quarters\n" + "17: Tennessee Room\n" + "18: Trophy Room\n"
//            + "19: Wine Cellar\n" + "20: Winter Garden\n"));
//  }
//
//  // Test to add a human player and ensure output is updated
//  @Test
//  public void testAddHumanPlayer() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nl\nq\n"); // Mock user input
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Human player Human Player added to the game."));
//  }
//
//  // Test to add a computer player
//  @Test
//  public void testAddComputerPlayer() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nl\nq\n"); // Mock user input
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString()
//        .contains("Computer-controlled player AI Player added to the game, starting in"));
//  }
//
//  // Test turn execution (look action)
//  @Test
//  public void testTurnLookAction() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nl\nq\n"); // Mock user input
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Room:"));
//    assertTrue(output.toString().contains("Items in this room:"));
//    assertTrue(output.toString().contains("Neighboring rooms:"));
//    assertTrue(output.toString().contains("Target character is here: Doctor Lucky"));
//  }
//
//  // Test handling of invalid input for adding a player
//  @Test
//  public void testInvalidRoomInput() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nm\n5\nm\n4\nq\n"); // Mock invalid input
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Invalid move. Please select a neighboring room."));
//  }
//
//  // Test handling of human player actions (move)
//  @Test
//  public void testHandleMoveAction() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nm\n4\nq\n"); // Mock user input to move and quit
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Human Player moved to Drawing Room"));
//  }
//
//  // Test handling of quitting the game
//  @Test
//  public void testQuitGame() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\nm\n4\nq\n");
//    // Mock user input to quit immediately
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Exiting the game."));
//  }
//
//  // Test game over when maximum turns are reached
//  @Test
//  public void testGameOverAtMaxTurns() throws IOException {
//    input = new StringReader("1\nHuman Player\n0\nl\n\n"); // Mock user input for several turns
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(
//        output.toString().contains("Game over! The maximum number of turns has been reached."));
//  }
//
//  // Test handling of invalid action input
//  @Test
//  public void testInvalidActionInput() throws IOException {
//    input = new StringReader("3\nHuman Player\n0\no\nl\nq\n"); // Mock invalid action input
//    controller = new Controller(world, input, output);
//
//    controller.startGame();
//    assertTrue(output.toString().contains("Invalid action. Please enter 'l', 'p', or 'm'."));
//  }
//
//}