package controller;

import command.AttemptOnTargetCommand;
import command.Command;
import command.LookCommand;
import command.MoveCommand;
import command.MovePetCommand;
import command.PickUpCommand;
import gameworld.WorldInterface;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import pet.PetInterface;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * Controller class to manage the game interaction and execute commands like
 * adding players, moving players, picking up items, etc.
 */
public class Controller implements ControllerInterface {

  private Appendable out;
  private Scanner scanner;
  private Map<String, Command> commandMap = new HashMap<>();

  /**
   * Constructs a Controller for managing interactions with the game world. This
   * constructor initializes the input source, output target, and the world model.
   *
   * @param in  the Readable instance from which the controller reads user input.
   *            Typically, this will be System.in or a StringReader in testing.
   * @param out the Appendable instance to which the controller writes output.
   *            Typically, this will be System.out or a StringBuilder in testing.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public Controller(Readable in, Appendable out) {
    this.scanner = new Scanner(in);
    this.out = out;
  }

  /**
   * Starts the game and handles user input for game interaction.
   */
  public void start(WorldInterface world, int maxTurns, Random random) throws IOException {
    // Display maximum turns allowed in the game
    out.append("Maximum number of turns allowed: " + maxTurns + "\n");

    // Save the world map as a PNG file
    out.append("Saving the world map to a PNG file...\n");
    world.generateWorldMap("");
    out.append("World map saved successfully!\n");

    // Add one human player and one computer-controlled player in a random order
    out.append("Adding players to the game...\n");
    addPlayersRandomly(world, random);
    // addHumanPlayerHandler(world);
    // addComputerPlayerHandler(world);

    // Game loop
    int turnCount = 0;
    world.wanderPet();
    while (turnCount < maxTurns) {
      PlayerInterface currentPlayer = world.getTurn();
      out.append("\n" + "Turn number: " + (turnCount + 1) + "/" + maxTurns + "\n");
      out.append(currentPlayer.getDescription());
      out.append(world.getTargetLocationHint() + "\n");
      PetInterface pet = world.getPet();
      out.append("Pet " + pet.getName() + " is in: " + pet.getCurrentRoom().getName() + "\n");
      out.append("Use 'look' to gather more details about your surroundings.\n");

      if (!currentPlayer.getIsComputerControlled()) {
        boolean validTurn = false; // Track if the player took a valid turn

        while (!validTurn) {
          out.append("Choose an action: [l: look, p: pickup, m: move, "
              + "mp: move pet, a: attack, q: quit]\n");
          String action = scanner.nextLine().trim().toLowerCase();

          if ("q".equalsIgnoreCase(action)) {
            out.append("Exiting the game.\n");
            return;
          }

          // Check if 'a' for attack is selected and validate if the target is in the same
          // room
          if ("a".equals(action)) {
            if (currentPlayer.getCurrentRoom()
                .getRoomInd() == (world.getTargetCharacter().getCurrentRoom().getRoomInd())) {
              out.append("Attempting to attack the target...\n");
              Command attackCommand = commandMap.get("a");
              if (attackCommand != null) {
                attackCommand.execute();
                // Check if the target is still alive
                if (!world.getTargetCharacter().isAlive()) {
                  return; // End the game if the target is dead
                }
              }
              validTurn = true; // Mark the turn as taken
            } else {
              out.append("The target is not in your current room. You cannot attack.\n");
            }
          } else {
            Command command = commandMap.get(action);
            if (command != null) {
              command.execute();
              validTurn = true; // Mark the turn as valid once a correct command executes
            } else {
              out.append("Invalid action. Please enter 'l', 'p', 'm', 'mp', or 'a'.\n");
            }
          }
        }

      } else {
        String actionResult = world.turnComputerPlayer();
        out.append(actionResult + "\n");
        // Check if the target is still alive after the computer player's turn
        if (!world.getTargetCharacter().isAlive()) {
          return; // End the game if the target is dead
        }
      }

      world.moveTargetCharacter(); // Automatically move the target character
      world.wanderPet();
      turnCount++;
    }

    out.append("Game over! Unfortunately, the maximum number of turns is reached.\n")
        .append("The target character escapes and runs away to live another day\n")
        .append("Nobody wins...\n");
  }

  /**
   * Randomly adds one human-controlled player and one computer-controlled player.
   */
  private void addPlayersRandomly(WorldInterface world, Random random) throws IOException {
    boolean humanFirst = random.nextBoolean(); // Randomize who is added first

    if (humanFirst) {
      addHumanPlayerHandler(world);
      addComputerPlayerHandler(world);
    } else {
      addComputerPlayerHandler(world);
      addHumanPlayerHandler(world);
    }
  }

  /**
   * Adds a human-controlled player to the game.
   */
  private void addHumanPlayerHandler(WorldInterface world) throws IOException {
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
    initializeCommands(world);
    out.append("Human player " + playerName + " added to the game.\n");
  }

  /**
   * Adds a computer-controlled player to the game.
   */
  private void addComputerPlayerHandler(WorldInterface world) throws IOException {
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

  private void initializeCommands(WorldInterface world) {
    // Define commands for each action
    commandMap.put("l", new LookCommand(world, out));
    commandMap.put("p", new PickUpCommand(world, out, scanner));
    commandMap.put("m", new MoveCommand(world, out, scanner));
    commandMap.put("mp", new MovePetCommand(world, out, scanner));
    commandMap.put("a", new AttemptOnTargetCommand(world, out, scanner));
  }

}
