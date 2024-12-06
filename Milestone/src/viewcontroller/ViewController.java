package viewcontroller;

import gameworld.World;
import gameworld.WorldInterface;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import player.PlayerInterface;
import room.RoomInterface;
import view.GameView;

/**
 * ViewController bridges the GameView and the WorldInterface, handling user
 * inputs and automating computer players.
 */
public class ViewController implements ViewControllerInterface {
  private GameView view;
  private WorldInterface world;
  private int maxTurns;
  private int currentTurnCount;
  private boolean isMovementMode; // Tracks if movement mode is active

  /**
   * Constructs a ViewController.
   *
   * @param view     the graphical user interface.
   * @param world    the game world model.
   * @param maxTurns the maximum number of turns allowed.
   */
  public ViewController(GameView view, WorldInterface world, int maxTurns) {
    this.view = view;
    this.world = world;
    this.maxTurns = maxTurns;
    this.currentTurnCount = 0;
    this.isMovementMode = false;

    // Registering event listeners
    this.view.addMouseListener(new GameMouseListener());
    this.view.addKeyListener(new GameKeyListener());
    processTurn();

  }

  @Override
  public void processTurn() {
    boolean gameOver = world.isGameEnd();
    if (gameOver) {
      handleGameOver();
      return;
    }
    if (currentTurnCount >= maxTurns) {
      view.showMessage("Maximum number of turns reached. Game over!");
      handleGameOver();
      return;
    }

    PlayerInterface currentPlayer = world.getTurn();
    currentTurnCount++;
    if (currentPlayer.getIsComputerControlled()) {
      // Automate computer player's turn
      updateTurnInfo();
      String result = world.turnComputerPlayer();
      view.showMessage("Computer Player Turn: " + result);
      processTurn(); // Recursive call to handle the next turn
    } else {
      // Update the view for human player's turn
      view.requestFocusInWindow();
      updateTurnInfo();

    }
    world.moveTargetCharacter();
  }

  @Override
  public void updateTurnInfo() {
//    PlayerInterface currentPlayer = world.getTurn();
    view.updateTurnInfo(currentTurnCount);
  }

  @Override
  public void handleGameOver() {

    int choice = JOptionPane.showConfirmDialog(view, "Do you want to start a new game?",
        "Game Over", JOptionPane.YES_NO_OPTION);

    if (choice == JOptionPane.YES_OPTION) {
      startNewGame(); // Transition to a new game
    } else {
      System.exit(0); // Exit the application
    }
  }

  @Override
  public void startNewGame() {
    // Clear and reset the world
    WorldInterface newWorld = new World();
    String worldFilePath = "./res/mansion.txt"; // Replace with actual path
    Readable worldFile;
    try {
      worldFile = new FileReader(worldFilePath);
      world.loadFromFile(worldFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Create the GameView
    GameView newView = new GameView(world, worldFilePath);
    newView.setVisible(true);
    view.dispose();
  }

  private class GameMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      Point clickPoint = e.getPoint();
      // Movement mode: Move player to the clicked room
      if (isMovementMode) {
        RoomInterface clickedRoom = view.getRoomAtLocation(clickPoint);
        PlayerInterface currentPlayer = world.getTurn();
        RoomInterface currentRoom = currentPlayer.getCurrentRoom();

        if (clickedRoom != null && currentRoom.getListofNeighbors().contains(clickedRoom)) {
          ViewCommand command = new MoveCommand(world, clickedRoom.getRoomInd());
          command.execute();
          view.showMessage("Moved to: " + clickedRoom.getName());
          isMovementMode = false; // Exit movement mode after moving
          processTurn();
        } else {
          view.showMessage("Invalid move! Click on a neighboring room.");
        }
        return;
      }

      // Check if a player is clicked
      PlayerInterface clickedPlayer = view.getPlayerAtLocation(clickPoint);
      if (clickedPlayer != null) {
        // Show the player's description
        String description = clickedPlayer.getViewDescription();
        JOptionPane.showMessageDialog(view, description, "Player Description",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }

    }
  }

  // Handles keyboard commands
  private class GameKeyListener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
      PlayerInterface currentPlayer = world.getTurn();

      if (currentPlayer.getIsComputerControlled()) {
        // Ignore inputs during computer player's turn
        return;
      }
      ViewCommand command = null;

      char key = e.getKeyChar();
      String result = "";
      switch (key) {
        case 'm': // Move
          // Move command
          isMovementMode = true;
          view.showMessage("Click on a neighboring room to move.");
          break;
        case 'p':
          // Pick up item command
          String itemName = view.promptForItem();
          command = new PickUpCommand(world, itemName);
          break;
        case 'l':
          // Look command
          command = new LookCommand(world);
          break;
        case 'a':
          // Attempt attack command
          String item = view.promptForInventoryItem();
          command = new AttackCommand(world, item);
          break;
        default:
          result = "Invalid key pressed";
          return;
      }

      if (command != null) {
        result = command.execute();
        updateTurnInfo(); // Update turn information after the command
      }
      if (result != null && !result.isEmpty()) {
        view.showMessage(result); // Only show meaningful messages
      }

      processTurn();
    }

    // Other required KeyListener methods
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
  }
}
