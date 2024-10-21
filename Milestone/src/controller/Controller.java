package controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import gameworld.WorldInterface;
import item.ItemInterface;
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
  private Appendable out;
  private Scanner scanner;
  private int maxTurns;

  /**
   * Constructs a Controller for managing interactions with the game world. This
   * constructor initializes the input source, output target, and the world model.
   *
   * @param world the WorldInterface instance representing the game world. This is
   *              used to interact with and manipulate the game state.
   * @param in    the Readable instance from which the controller reads user
   *              input. Typically, this will be System.in or a StringReader in
   *              testing.
   * @param out   the Appendable instance to which the controller writes output.
   *              Typically, this will be System.out or a StringBuilder in
   *              testing.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public Controller(WorldInterface world, Readable in, Appendable out) {
    this.world = world;
    this.scanner = new Scanner(in);
    this.out = out;
  }

  /**
   * Starts the game and handles user input for game interaction.
   */
  public void startGame() throws IOException {
    // Ask for maximum turns allowed in the game
    out.append("Enter the maximum number of turns allowed: ");
    maxTurns = Integer.parseInt(scanner.nextLine());

    // Save the world map as a PNG file
    out.append("Saving the world map to a PNG file...\n");
    world.generateWorldMap("");
    out.append("World map saved successfully!\n");

    // Add one human player and one computer-controlled player in a random order
    out.append("Adding players to the game...\n");
    // addPlayersRandomly();
    addHumanPlayer();
    addComputerPlayer();
    // Game loop
    int turnCount = 0;
    while (turnCount < maxTurns) {
      PlayerInterface currentPlayer = world.getTurn();
      out.append("\n" + "Turn number: " + (turnCount + 1) + "\n");
      out.append("It's " + currentPlayer.getName() + "'s turn.\n");
      out.append(currentPlayer.getDescription() + "\n");

      if (!currentPlayer.getIsComputerControlled()) {
        handleHumanPlayerTurn(currentPlayer);
      } else {
        String actionResult = world.turnComputerPlayer();
        out.append(actionResult + "\n");
      }

      world.moveTargetCharacter(); // Automatically move the target character
      turnCount++;

      // Quit option
      out.append("Enter 'q' to quit or press Enter to continue: ");
      String quitInput = scanner.nextLine().trim();
      if ("q".equalsIgnoreCase(quitInput)) {
        out.append("Exiting the game.\n");
        break;
      }
    }

    out.append("Game over! The maximum number of turns has been reached.\n");
  }

  /**
   * Randomly adds one human-controlled player and one computer-controlled player.
   */
  private void addPlayersRandomly() throws IOException {
    Random random = new Random();
    boolean humanFirst = random.nextBoolean(); // Randomize who is added first

    if (humanFirst) {
      addHumanPlayer();
      addComputerPlayer();
    } else {
      addComputerPlayer();
      addHumanPlayer();
    }
  }

  /**
   * Adds a human-controlled player to the game.
   */
  private void addHumanPlayer() throws IOException {
    out.append("Enter the name for the human player: ");
    String playerName = scanner.nextLine();

    RoomInterface startingRoom = null;
    while (startingRoom == null) {
      // Display available rooms
      out.append("Available rooms:\n");
      for (RoomInterface r : world.getRooms()) {
        out.append(r.getRoomInd() + ": " + r.getName() + "\n");
      }

      out.append("Enter the room index for the human player to start in: ");
      String input = scanner.nextLine();

      try {
        int roomIndex = Integer.parseInt(input);
        if (roomIndex >= 0 && roomIndex < world.getRooms().size()) {
          startingRoom = world.getRooms().get(roomIndex);
        } else {
          out.append("Invalid room index. Please try again.\n");
        }
      } catch (NumberFormatException e) {
        out.append("Invalid input. Please enter a valid room index.\n");
      }
    }

    int maxItems = world.getItems().size(); // Set max items to number of items in the world
    PlayerInterface humanPlayer = new HumanPlayer(playerName, startingRoom, maxItems);
    world.addPlayer(humanPlayer, world.getRooms().indexOf(startingRoom));
    out.append("Human player " + playerName + " added to the game.\n");
  }

  /**
   * Adds a computer-controlled player to the game.
   */
  private void addComputerPlayer() throws IOException {
    String computerPlayerName = "AI Player";
    if (world.getRooms().isEmpty()) {
      out.append("No available rooms to place the computer player.\n");
      return;
    }
    Random random = new Random();
    int randomRoomIndex = random.nextInt(world.getRooms().size()); // Random room selection

    RoomInterface startingRoom = world.getRooms().get(randomRoomIndex);
    int maxItems = world.getItems().size(); // Set max items to number of items in the world

    PlayerInterface computerPlayer = new ComputerPlayer(computerPlayerName, startingRoom, maxItems);
    world.addPlayer(computerPlayer, randomRoomIndex);
    out.append("Computer-controlled player " + computerPlayerName
        + " added to the game, starting in " + startingRoom.getName() + ".\n");
  }

  /**
   * Handles the human player's turn by asking for input on what action to
   * perform.
   */
  private void handleHumanPlayerTurn(PlayerInterface player) throws IOException {
    boolean validAction = false;
    while (!validAction) {
      out.append("Choose an action: [l: look, p: pickup, m: move]\n");
      String action = scanner.nextLine().trim().toLowerCase();

      switch (action) {
        case "l":
          String lookResult = world.turnHumanPlayer("look", -1, null);
          out.append(lookResult + "\n");
          validAction = true;
          break;

        case "p":
          List<ItemInterface> itemsInRoom = player.getCurrentRoom().getItems();
          if (itemsInRoom.isEmpty()) {
            out.append("No items to pick up in this room.\n");
            validAction = true;
            break;
          }
          // Display available items
          displayItemsInRoom(player);
          out.append("Enter the item name to pick up: ");
          String itemName = scanner.nextLine().trim();

          // Validate the item name
          ItemInterface itemToPick = null;
          for (ItemInterface item : itemsInRoom) {
            if (item.getName().equalsIgnoreCase(itemName)) {
              itemToPick = item;
              break;
            }
          }

          if (itemToPick != null) {
            String pickupResult = world.turnHumanPlayer("pickup", -1, itemToPick.getName());
            out.append(pickupResult + "\n");
            validAction = true;
          } else {
            out.append("Invalid item name. Please enter a valid item from the list.\n");
          }
          break;

        case "m":
          displayNeighborRooms(player);
          out.append("Enter the room index to move to: ");
          int roomIndex = Integer.parseInt(scanner.nextLine().trim());
          if (isNeighborRoom(player, roomIndex)) {
            String moveResult = world.turnHumanPlayer("move", roomIndex, null);
            out.append(moveResult + "\n");
            validAction = true;
          } else {
            out.append("Invalid move. Please select a neighboring room.\n");
          }
          break;

        default:
          out.append("Invalid action. Please enter 'l', 'p', or 'm'.\n");
      }
    }
  }

  /**
   * Displays the neighboring rooms for the human player.
   */
  private void displayNeighborRooms(PlayerInterface player) throws IOException {
    List<RoomInterface> neighbors = player.getCurrentRoom().getListofNeighbors();
    out.append("Neighboring rooms:\n");
    for (RoomInterface neighbor : neighbors) {
      out.append(neighbor.getRoomInd() + ": " + neighbor.getName() + "\n");
    }
  }

  /**
   * Displays the items in the room for the human player.
   */
  private void displayItemsInRoom(PlayerInterface player) throws IOException {
    List<ItemInterface> items = player.getCurrentRoom().getItems();
    out.append("Items in the room:\n");
    for (ItemInterface item : items) {
      out.append(item.getName() + " (Damage: " + item.getDamage() + ")\n");
    }
  }

  /**
   * Checks if the selected room index is a neighboring room for the player.
   */
  private boolean isNeighborRoom(PlayerInterface player, int roomIndex) {
    RoomInterface targetRoom = world.getRooms().get(roomIndex);
    return player.getCurrentRoom().getListofNeighbors().contains(targetRoom);
  }

}
