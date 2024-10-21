package controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;
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

    // Add players randomly as human or computer
    addPlayersRandomly();

    // Game loop
    int turnCount = 0;
    while (turnCount < maxTurns) {
      PlayerInterface currentPlayer = world.getTurn();

      System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");

      // Display the player's description at the beginning of their turn
      System.out.println(currentPlayer.getDescription());

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
   * Adds players to the game randomly as either human or computer-controlled.
   */
  private void addPlayersRandomly() {
    Random random = new Random();

    // Add 2 players (you can adjust the number as needed)
    for (int i = 0; i < 2; i++) {
      if (random.nextBoolean()) {
        addHumanPlayer();
      } else {
        addComputerPlayer();
      }
    }
  }

  /**
   * Adds a human-controlled player to the game. The player can choose which room
   * to enter at the start.
   */
  private void addHumanPlayer() {
    System.out.print("Enter the name for the human player: ");
    String playerName = scanner.nextLine();

    RoomInterface startingRoom = null;
    while (startingRoom == null) {
      // Display available rooms
      System.out.println("Available rooms:");
      for (RoomInterface r : world.getRooms()) {
        System.out.println(r.getRoomInd() + ": " + r.getName());
      }

      System.out.print("Enter the room index for the human player to start in: ");
      String input = scanner.nextLine();

      try {
        int roomIndex = Integer.parseInt(input);
        if (roomIndex >= 0 && roomIndex < world.getRooms().size()) {
          startingRoom = world.getRooms().get(roomIndex);
        } else {
          System.out.println("Invalid room index. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid room index.");
      }
    }

    int maxItems = world.getItems().size(); // Set max items to number of items in the world
    PlayerInterface humanPlayer = new HumanPlayer(playerName, startingRoom, maxItems);
    world.addPlayer(humanPlayer, world.getRooms().indexOf(startingRoom));
    System.out.println("Human player " + playerName + " added to the game.");
  }

  /**
   * Adds a computer-controlled player to the game. The computer player starts in
   * a random room.
   */
  private void addComputerPlayer() {
    String computerPlayerName = "AI Player";
    Random random = new Random();
    int randomRoomIndex = random.nextInt(world.getRooms().size()); // Random room selection

    RoomInterface startingRoom = world.getRooms().get(randomRoomIndex);
    int maxItems = world.getItems().size(); // Set max items to number of items in the world

    PlayerInterface computerPlayer = new ComputerPlayer(computerPlayerName, startingRoom, maxItems);
    world.addPlayer(computerPlayer, randomRoomIndex);
    System.out.println("Computer-controlled player " + computerPlayerName
        + " added to the game, starting in " + startingRoom.getName() + ".");
  }

  /**
   * Handles the human player's turn by asking for input on what action to
   * perform.
   */
  private void handleHumanPlayerTurn(PlayerInterface player) {
    boolean validAction = false;
    while (!validAction) {
      System.out.println("Choose an action: [l: look, p: pickup, m: move]");
      String action = scanner.nextLine().trim().toLowerCase();

      switch (action) {
        case "l":
          world.turnHumanPlayer("look", -1, null);
          validAction = true;
          break;

        case "p":
          System.out.println("Enter the item name to pick up: ");
          String itemName = scanner.nextLine().trim();
          world.turnHumanPlayer("pickup", -1, itemName);
          validAction = true;
          break;

        case "m":
          displayNeighborRooms(player);
          System.out.println("Enter the room index to move to: ");
          int roomIndex = Integer.parseInt(scanner.nextLine().trim());
          if (isNeighborRoom(player, roomIndex)) {
            world.turnHumanPlayer("move", roomIndex, null);
            validAction = true;
          } else {
            System.out.println("Invalid move. Please select a neighboring room.");
          }
          break;

        default:
          System.out.println("Invalid action. Please enter 'l', 'p', or 'm'.");
      }
    }
  }

  /**
   * Displays the neighboring rooms for the human player.
   */
  private void displayNeighborRooms(PlayerInterface player) {
    List<RoomInterface> neighbors = player.getCurrentRoom().getListofNeighbors();
    System.out.println("Neighboring rooms:");
    for (int i = 0; i < neighbors.size(); i++) {
      RoomInterface neighbor = neighbors.get(i);
      System.out.println(neighbor.getRoomInd() + ": " + neighbor.getName());
    }
  }

  /**
   * Checks if the selected room index is a neighboring room for the player.
   * 
   * @param player    The player trying to move.
   * @param roomIndex The index of the room the player wants to move to.
   * @return true if the room is a neighbor, false otherwise.
   */
  private boolean isNeighborRoom(PlayerInterface player, int roomIndex) {
    RoomInterface targetRoom = world.getRooms().get(roomIndex);
    return player.getCurrentRoom().getListofNeighbors().contains(targetRoom);
  }
}
