package controller;

import java.io.IOException;
import java.util.Scanner;

import gameworld.WorldInterface;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * Controller class to manage the game interaction and execute commands like
 * adding players, moving players, picking up items, etc.
 */
public class Controller implements ControllerInterface {

  private WorldInterface world;
  private Scanner scanner;
  private int maxTurns;

  public Controller(WorldInterface world, Scanner scanner) {
    this.world = world;
    this.scanner = scanner;
  }

  /**
   * Starts the game and handles user input for game interaction.
   */
  public void startGame() throws IOException {
    // Ask for maximum turns allowed in the game
    System.out.print("Enter the maximum number of turns allowed: ");
    maxTurns = Integer.parseInt(scanner.nextLine());

    // Save the world map as a PNG file
    System.out.println("Saving the world map to a PNG file...");
    world.generateWorldMap("");
    System.out.println("World map saved successfully!");

    // Add a human-controlled player
    addHumanPlayer();

    // Add a computer-controlled player
    addComputerPlayer();

    // Game loop
    int turnCount = 0;
    while (turnCount < maxTurns) {
      PlayerInterface currentPlayer = world.getTurn();
      System.out.println("It's " + currentPlayer.getName() + "'s turn.");

      if (!currentPlayer.getIsComputerControlled()) {
        handleHumanPlayerTurn(currentPlayer);
      } else {
        world.turnComputerPlayer(); // Automatically take computer player's turn
      }

      world.moveTargetCharacter(); // Automatically move the target character
      turnCount++;

      // Quit option
      System.out.print("Enter 'q' to quit or press Enter to continue: ");
      String quitInput = scanner.nextLine().trim();
      if ("q".equalsIgnoreCase(quitInput)) {
        System.out.println("Exiting the game.");
        break;
      }
    }

    System.out.println("Game over! The maximum number of turns has been reached.");
  }

  /**
   * Adds a human-controlled player to the game.
   */
  private void addHumanPlayer() {
    System.out.print("Enter the name for the human player: ");
    String playerName = scanner.nextLine();
    RoomInterface startingRoom = world.getRooms().get(0); // You can change to allow selection of
                                                          // the starting room
    int maxItems = world.getItems().size(); // Set max items to number of items in the world
    PlayerInterface humanPlayer = new HumanPlayer(playerName, startingRoom, maxItems);
    world.addPlayer(humanPlayer, 0);
    System.out.println("Human player " + playerName + " added to the game.");
  }

  /**
   * Adds a computer-controlled player to the game.
   */
  private void addComputerPlayer() {
    String computerPlayerName = "AI Player";
    RoomInterface startingRoom = world.getRooms().get(1); // You can change to allow selection of
                                                          // starting room
    int maxItems = world.getItems().size(); // Set max items to number of items in the world
    PlayerInterface computerPlayer = new ComputerPlayer(computerPlayerName, startingRoom, maxItems);
    world.addPlayer(computerPlayer, 1);
    System.out.println("Computer-controlled player " + computerPlayerName + " added to the game.");
  }

  /**
   * Handles the human player's turn by asking for input on what action to
   * perform.
   */
  private void handleHumanPlayerTurn(PlayerInterface player) {
    while (true) {
      System.out.println("Choose an action: [look, pickup, move, display]");
      String action = scanner.nextLine().trim().toLowerCase();

      switch (action) {
        case "look":
          System.out.println(player.lookAround());
          return;

        case "pickup":
          System.out.println("Enter the item name to pick up: ");
          String itemName = scanner.nextLine().trim();
          world.turnHumanPlayer("pickup", -1, itemName);
          return;

        case "move":
          System.out.println("Enter the room index to move to: ");
          int roomIndex = Integer.parseInt(scanner.nextLine().trim());
          world.turnHumanPlayer("move", roomIndex, null);
          return;

        case "display":
          System.out.println(player.getDescription());
          return;

        default:
          System.out
              .println("Invalid action. Please enter 'look', 'pickup', 'move', or 'display'.");
      }
    }
  }
}