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
  private String worldFilePath;
  private boolean isTesting;
  private boolean isMovementMode; // Tracks if movement mode is active

  /**
   * Constructs a {@code ViewController} to manage the interaction between the
   * graphical user interface and the game world model. This controller is
   * responsible for handling user inputs, updating the view, and orchestrating
   * game logic such as turn processing.
   *
   * @param view          the graphical user interface component that displays the
   *                      game world and receives user interactions.
   * @param world         the game world model that encapsulates the game's state,
   *                      including rooms, players, and target character
   *                      information.
   * @param worldFilePath the file path to the world specification file, used for
   *                      loading the game's world data.
   * @param maxTurns      the maximum number of turns allowed in the game,
   *                      determining the game's duration.
   * @throws IllegalArgumentException if any of the provided arguments are
   *                                  {@code null}.
   */
  public ViewController(GameView view, WorldInterface world, String worldFilePath, int maxTurns) {
    this.view = view;
    this.world = world;
    this.maxTurns = maxTurns;
    this.worldFilePath = worldFilePath;
    this.isMovementMode = false;
    this.isTesting = false;
    // Registering event listeners
    this.view.registerListeners(new GameKeyListener(), new GameMouseListener());
    processTurn();

  }

  @Override
  public void processTurn() {
    if (isTesting && world.isGameEnd()) {
      return; // Prevent infinite loop in testing
    }
    boolean gameOver = world.isGameEnd();
    if (gameOver) {
      handleGameOver();
      return;
    }
    if (world.getTotalTurn() > maxTurns) {
      StringBuilder message = new StringBuilder();
      message.append("Game over! Unfortunately, the maximum number of turns is reached.\n")
          .append("The target character escapes and runs away to live another day.\n")
          .append("Nobody wins...\n");
      view.showMessage(message.toString());
      handleGameOver();
      return;
    }

    PlayerInterface currentPlayer = world.getTurn();
    if (currentPlayer.getIsComputerControlled()) {
      // Automate computer player's turn
      updateTurnInfo();
      String result = world.turnComputerPlayer();
      view.showMessage("Computer Player Turn: " + result);
      processTurn(); // Recursive call to handle the next turn
      world.moveTargetCharacter();
    } else {
      // Update the view for human player's turn
      view.requestFocusInWindow();
      updateTurnInfo();

    }

  }

  @Override
  public void updateTurnInfo() {
    view.updateTurnInfo(world.getTotalTurn());
  }

  @Override
  public void handleGameOver() {
    if (isTesting) {
      return; // Prevent further calls during testing
    }
    int choice = getGameOverChoice();
    if (choice == JOptionPane.YES_OPTION) {
      startNewGame(); // Transition to a new game
    } else {
      System.exit(0); // Exit the application
    }
  }

  public int getGameOverChoice() {
    return JOptionPane.showConfirmDialog(view, "Do you want to start a new game?", "Game Over",
        JOptionPane.YES_NO_OPTION);
  }

  @Override
  public void startNewGame() {
    maxTurns = 0;
    while (maxTurns <= 0) {
      String input = JOptionPane.showInputDialog(view, "Enter the maximum number of turns:",
          "Max Turns", JOptionPane.PLAIN_MESSAGE);

      try {
        maxTurns = Integer.parseInt(input);
        if (maxTurns <= 0) {
          JOptionPane.showMessageDialog(view, "Maximum turns must be a positive number.",
              "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Invalid input. Please enter a positive integer.",
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    // Clear and reset the world
    WorldInterface newWorld = new World(); // Replace with actual path
    Readable worldFile;
    try {
      worldFile = new FileReader(worldFilePath);
      newWorld.loadFromFile(worldFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Create the GameView
    GameView newView = new GameView(newWorld, worldFilePath, maxTurns);
    newView.setVisible(true);
    view.dispose();
  }

  /**
   * {@code GameMouseListener} is a custom mouse event handler that processes
   * mouse interactions within the game. It responds to user clicks, enabling
   * functionality such as player movement to a clicked room or displaying
   * information about a clicked player.
   */
  public class GameMouseListener extends MouseAdapter {
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
          world.moveTargetCharacter();
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

  /**
   * {@code GameKeyListener} is a custom keyboard event handler that processes key
   * inputs during the game. It maps specific key presses to corresponding game
   * actions such as moving, picking up items, attacking, and looking around.
   */
  public class GameKeyListener implements KeyListener {
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
        world.moveTargetCharacter();
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

  @Override
  public GameMouseListener getGameMouseListener() {
    return new GameMouseListener();
  }

  @Override
  public GameKeyListener getGameKeyListener() {
    return new GameKeyListener();
  }

  @Override
  public void setIsTesting() {
    this.isTesting = true;
  }
}
