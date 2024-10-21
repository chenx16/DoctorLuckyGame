package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerInterface;

public class ControllerInterfaceTest {

  private WorldInterface world;
  private StringBuilder log;
  private StringReader input;
  private StringWriter output;
  private final String localDir = "./res/";
  private ControllerInterface controller;

  @Before
  public void setUp() throws IOException {
    log = new StringBuilder();
    world = new World();
    // Assuming the test world file is correctly structured for testing
    File worldFile = new File(localDir + "mansion.txt");
    FileReader fileReader1 = new FileReader(worldFile);
    world.loadFromFile(fileReader1);
    output = new StringWriter();
  }

  @Test
  public void testStartGame() throws IOException {
    // Simulate inputs: 3 turns, human player name, room index 0, action 'l' (look),
    // and quit 'q'.
    input = new StringReader("3\nHuman Player\n0\nl\nq\n");

    // Create the controller with mock world, input, and output.
    Controller controller = new Controller(world, input, output);

    // Start the game
    controller.startGame();

    // Verify the interaction log in the mock world
    assertEquals("", log.toString());

    // Verify the output from the controller
    assertTrue(output.toString().contains(
        "Enter the maximum number of turns allowed: Saving the world map to a PNG file...\n"
            + "World map saved successfully!\n" + "Adding players to the game...\n"
            + "Enter the name for the human player: Available rooms:\n" + "0: Armory\n"
            + "1: Billiard Room\n" + "2: Carriage House\n" + "3: Dining Hall\n"
            + "4: Drawing Room\n" + "5: Foyer\n" + "6: Green House\n" + "7: Hedge Maze\n"
            + "8: Kitchen\n" + "9: Lancaster Room\n" + "10: Library\n" + "11: Lilac Room\n"
            + "12: Master Suite\n" + "13: Nursery\n" + "14: Parlor\n" + "15: Piazza\n"
            + "16: Servants' Quarters\n" + "17: Tennessee Room\n" + "18: Trophy Room\n"
            + "19: Wine Cellar\n" + "20: Winter Garden\n"));
  }
}