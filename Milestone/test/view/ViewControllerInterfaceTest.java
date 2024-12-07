package view;

import static org.junit.Assert.assertTrue;

import gameworld.WorldInterface;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mockmodel.MockGameView;
import mockmodel.MockWorld;
import org.junit.Before;
import org.junit.Test;
import player.HumanPlayer;
import viewcontroller.ViewController;
import viewcontroller.ViewControllerInterface;

/**
 * Unit tests for the {@link viewcontroller.ViewController} class to ensure
 * proper interaction between the ViewController, View, and Model.
 * 
 * 
 */
public class ViewControllerInterfaceTest {
  private StringBuilder log;
  private WorldInterface mockWorld;
  private GameView mockView;
  private ViewControllerInterface controller;

  /**
   * Sets up the mock components and initializes the controller before each test.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    mockWorld = new MockWorld(log, "Mock Response");
    mockView = new MockGameView(log);
    controller = new ViewController(mockView, mockWorld, "test_world.txt", 10);
  }

  /**
   * Tests that the controller processes a human player's turn correctly and
   * updates the turn info.
   */
  @Test
  public void testProcessTurn_HumanPlayer() {
    mockWorld.addPlayer(new HumanPlayer("PlayerH", mockWorld.getRooms().get(0), 5), 0);
    controller.processTurn();
    // System.out.println(log);
    assertTrue(log.toString().contains("getTurn called"));
    assertTrue(log.toString().contains("updateTurnInfo called"));
    assertTrue(log.toString().contains("updateTurnInfo called with turnNum: 0"));
  }

  /**
   * Tests that the game-over logic properly terminates or restarts the game as
   * expected.
   */
  @Test
  public void testHandleGameOver() {
    class TestViewController extends ViewController {
      public TestViewController(GameView view, WorldInterface world, String worldFilePath,
          int maxTurns) {
        super(view, world, worldFilePath, maxTurns);
        this.setIsTesting();
        // Enable test mode to prevent recursion
      }

      @Override
      public int getGameOverChoice() {
        return JOptionPane.NO_OPTION; // Simulate user choosing "No"
      }
    }

    // Set up the controller with the mock world and view
    controller = new TestViewController(mockView, mockWorld, "mock/path", 10);

    // Simulate game end
    mockWorld.setGameEnd(true);
    controller.processTurn();

    // Verify log
    // System.out.println(log);
    assertTrue(log.toString().contains("setGameEnd called"));
    assertTrue(log.toString().contains("isGameEnd called"));
  }

  /**
   * Tests that the 'M' key activates movement mode and prompts the user to click
   * a room.
   */
  @Test
  public void testKeyListener_MoveMode() {
    KeyEvent moveKeyEvent = new KeyEvent(mockView, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
        0, KeyEvent.VK_UNDEFINED, 'm');
    controller.getGameKeyListener().keyPressed(moveKeyEvent);
    assertTrue(
        log.toString().contains("showMessage called with: Click on a neighboring room to move."));
  }

  /**
   * Tests that the 'P' key triggers the pick-up item action and prompts the user
   * for input.
   */
  @Test
  public void testKeyListener_PickUpItem() {
    controller.processTurn();
    KeyEvent pickUpKeyEvent = new KeyEvent(mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'p');
    controller.getGameKeyListener().keyPressed(pickUpKeyEvent);
    // System.out.println(log);
    assertTrue(log.toString().contains("promptForItem called"));
  }

  /**
   * Tests that clicking on a room during movement mode validates the move and
   * shows the result.
   */
  @Test
  public void testMouseListener_ClickRoom() {
    // Mock source component (e.g., the game panel)
    JPanel mockPanel = new JPanel(); // Create a JPanel to act as the source
    mockPanel.setSize(800, 600); // Set size to ensure it's valid for coordinates
    KeyEvent moveKeyEvent = new KeyEvent(mockView, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
        0, KeyEvent.VK_UNDEFINED, 'm');
    controller.getGameKeyListener().keyPressed(moveKeyEvent);
    Point clickPoint = new Point(50, 50); // Example point to simulate clicking
    MouseEvent event = new MouseEvent(mockPanel, MouseEvent.MOUSE_CLICKED,
        System.currentTimeMillis(), 0, clickPoint.x, clickPoint.y, 1, false);

    // Trigger the mouseClicked event
    controller.getGameMouseListener().mouseClicked(event);

    // Validate the expected behavior
    // System.out.println(log);
    assertTrue(log.toString()
        .contains("showMessage called with: Invalid move! Click on a neighboring room."));
    assertTrue(log.toString()
        .contains("getRoomAtLocation called with clickPoint: java.awt.Point[x=50,y=50]"));
  }

  /**
   * Tests that clicking on a player shows their description.
   */
  @Test
  public void testMouseListener_ClickPlayer() {
    // Assuming gamePanel is the JPanel where the mouse listener is registered
    JPanel mockPanel = new JPanel(); // Mock panel as the event source
    Point clickPoint = new Point(50, 50); // Adjust the point as needed

    // Create a valid MouseEvent
    MouseEvent event = new MouseEvent(mockPanel, MouseEvent.MOUSE_CLICKED,
        System.currentTimeMillis(), 0, clickPoint.x, clickPoint.y, 1, false);

    // Call the mouse listener
    controller.getGameMouseListener().mouseClicked(event);

    // Verify that the correct behavior occurred (e.g., log, method calls, etc.)
    // System.out.println(log);
    assertTrue(log.toString().contains("registerListeners called"));
    assertTrue(log.toString()
        .contains("getPlayerAtLocation called with point: java.awt.Point[x=50,y=50]"));
  }

}
