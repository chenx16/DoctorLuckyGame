package driver;

import controller.Controller;
import gameworld.World;
import gameworld.WorldInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Random;
import room.RoomInterface;
import target.TargetInterface;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * takes a world specification file or string as input and showcases various
 * functionalities of the model such as loading the world, displaying space
 * information, moving the target character, and generating the world map.
 */
public class Driver {
  private static Readable inputSource = new InputStreamReader(System.in);
  private static Appendable output = System.out;
  private static Appendable errorOutput = System.err;

  /**
   * The main entry point for the program. This method initializes the game by
   * reading command-line arguments, setting up the input and output streams, and
   * starting the game controller.
   *
   * @param args command-line arguments where: - args[0]: A file path or string
   *             representing the world specification. - args[1] (optional): The
   *             maximum number of turns allowed in the game.
   */
  public static void main(String[] args) {
    // Check if the necessary arguments are provided
    if (args.length < 1) {
      try {
        errorOutput
            .append("Usage: java -jar Milestone2.jar <path-to-world-specification-file or string> "
                + "<max-turns (optional)>\n");
      } catch (IOException e) {
        handleError("Failed to output usage information.");
      }
      System.exit(1);
    }

    // Initialize the world and other variables
    WorldInterface world = new World();

    int maxTurns = 20; // Default maximum number of turns

    String worldData = args[0]; // The first argument could be a file path or a string

    // Check if a second argument (max turns) is provided
    if (args.length > 1) {
      try {
        maxTurns = Integer.parseInt(args[1]);
        if (maxTurns <= 0) {
          handleError("Invalid number for max turns.");
          return;
        }
      } catch (NumberFormatException e) {
        handleError("Invalid number format for max turns.");
        return;
      }
    } else {
      try {
        output.append("Max turns not entered. Using default of 20 turns.\n");
      } catch (IOException e) {
        handleError("Failed to output default max turns notice.");
      }
    }

    // Check if the world data is a valid file path
    File worldFile = new File(worldData);
    if (worldFile.exists() && worldFile.isFile()) {
      // If it's a file, use a FileReader
      try {
        inputSource = new FileReader(worldFile);
      } catch (IOException e) {
        try {
          errorOutput.append("Error: Unable to read file - ").append(e.getMessage()).append("\n");
        } catch (IOException ioException) {
          handleError("Failed to output file read error.");
        }
        return;
      }
    } else {
      // If it's not a file, assume it's a world specification string
      worldData = worldData.replace("\\n", "\n");
      inputSource = new StringReader(worldData);
    }

    try {
      // Load the world from the specified input (file or string)
      output.append("Loading world...\n");
      world.loadFromFile(inputSource);

      // Display world details
      output.append("World: ").append(world.getName()).append("\n");

      // Display target character information
      TargetInterface target = world.getTargetCharacter();
      output.append(target.toString()).append("\n");

      // Show space information, including items
      output.append("\nSpace information for each room:\n");
      for (RoomInterface room : world.getRooms()) {
        output.append(world.getSpaceInfo(room)).append("\n");
      }

      // Start the game using the controller, passing the max turns
      Controller controller = new Controller(new InputStreamReader(System.in), output);
      controller.start(world, maxTurns, new Random());

      // Save the output to a file
      saveOutputToFile(output.toString());

    } catch (IOException e) {
      try {
        errorOutput.append("World file is invalid: ").append(e.getMessage()).append("\n");
      } catch (IOException ioException) {
        handleError("Failed to output error message.");
      }
    }
  }

  /**
   * Saves the program output to a file.
   * 
   * @param content The content to be saved.
   */
  private static void saveOutputToFile(String content) {
    File outputFile = new File("output.txt");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
      writer.write(content);
      output.append("Output saved to output.txt\n");
    } catch (IOException e) {
      handleError("Failed to save output: " + e.getMessage());
    }
  }

  /**
   * Handles errors by appending to error output and printing to System.err as a
   * fallback if needed.
   * 
   * @param message The error message to append.
   */
  private static void handleError(String message) {
    try {
      errorOutput.append(message).append("\n");
    } catch (IOException ioException) {
      System.err.println("Critical error handling failure: " + ioException.getMessage());
    }
  }
}
