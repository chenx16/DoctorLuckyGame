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
import room.RoomInterface;
import target.TargetInterface;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * takes a world specification file or string as input and showcases various
 * functionalities of the model such as loading the world, displaying space
 * information, moving the target character, and generating the world map.
 */
public class Driver {
  /**
   * The main entry point for the program. This method initializes the game by
   * reading command-line arguments, setting up the input and output streams, and
   * starting the game controller.
   *
   * @param args command-line arguments where: - args[0]: A file path or string
   *             representing the world specification. - args[1] (optional): The
   *             maximum number of turns allowed in the game.
   * 
   *
   * @throws IllegalArgumentException if the required argument for world
   *                                  specification is missing.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out
          .println("Usage: java -jar Milestone2.jar <path-to-world-specification-file or string>");
      System.exit(1);
    }

    WorldInterface world = new World();
    StringBuilder output = new StringBuilder();
    Readable inputSource;

    String worldData = args[0]; // The argument could be a file path or a string

    // Check if the argument is a valid file path
    File worldFile = new File(worldData);
    if (worldFile.exists() && worldFile.isFile()) {
      // If it's a file, use a FileReader
      try {
        inputSource = new FileReader(worldFile);
      } catch (IOException e) {
        System.err.println("Error: Unable to read file - " + e.getMessage());
        return;
      }
    } else {
      // If it's not a file, assume it's a world specification string
      worldData = worldData.replace("\\n", "\n");
      inputSource = new StringReader(worldData);
    }

    try {
      // Load the world from the specified input (file or string)
      System.out.println("Loading world...");
      output.append("Loading world...\n");
      world.loadFromFile(inputSource);

      // Show the name of the world
      System.out.println("World: " + world.getName());
      output.append("World: ").append(world.getName()).append("\n");

      // Display target character information
      TargetInterface target = world.getTargetCharacter();
      System.out.println(target.toString());
      output.append(target.toString()).append("\n");

      // Show space information, including items
      System.out.println("\nSpace information for each room:");
      for (RoomInterface room : world.getRooms()) {
        System.out.println(world.getSpaceInfo(room));
        output.append(world.getSpaceInfo(room)).append("\n");
      }

      // Start the game using the controller
      Controller controller = new Controller(world, new InputStreamReader(System.in), System.out);
      controller.startGame();

      // Save the output to a file
      saveOutputToFile(output.toString());

    } catch (IOException e) {
      System.err.println("Failed to load scanner or process the world input: " + e.getMessage());
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
      System.out.println("Output saved to output.txt");
    } catch (IOException e) {
      System.err.println("Failed to save output: " + e.getMessage());
    }
  }
}
