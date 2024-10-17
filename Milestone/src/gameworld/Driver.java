package gameworld;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * takes a world specification file or string as input and showcases various
 * functionalities of the model such as loading the world, displaying space
 * information, moving the target character, and generating the world map.
 */
public class Driver {

  /**
   * The main method that drives the program. This method uses a command-line
   * argument to specify the world specification file. It demonstrates the
   * following functionalities:
   * <ul>
   * <li>Loading the world from a file or string</li>
   * <li>Displaying information about each room (including items and
   * neighbors)</li>
   * <li>Moving the target character to new rooms</li>
   * <li>Generating the world map and saving it as an image file</li>
   * </ul>
   *
   * @param args the command-line arguments. The first argument can be the path to
   *             a world specification file, or a string input representing the
   *             world.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out
          .println("Usage: java -jar Milestone1.jar <path-to-world-specification-file or string>");
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
      // Replace literal "\n" with actual newline characters
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

      // Display initial space information
      RoomInterface initialRoom = target.getCurrentRoom();
      System.out.println("Target is in: " + initialRoom.getName());
      System.out.println(world.getSpaceInfo(initialRoom));
      output.append(world.getSpaceInfo(initialRoom)).append("\n");

      try (Scanner scanner = new Scanner(System.in)) {
        while (true) {
          System.out.println("Enter 'm' to move the target or 'q' to quit: ");
          output.append("Enter 'm' to move the target or 'q' to quit: ");
          String input = scanner.nextLine();
          output.append(input).append("\n");

          if ("q".equalsIgnoreCase(input)) {
            System.out.println("Exiting the program.");
            output.append("Exiting the program.\n");
            break;
          } else if ("m".equalsIgnoreCase(input)) {
            world.moveTargetCharacter();
            TargetInterface targetMove = world.getTargetCharacter();
            RoomInterface currentRoom = targetMove.getCurrentRoom();
            System.out.println(targetMove.toString());
            output.append(targetMove.toString()).append("\n");
            System.out.println("Target is now in: " + currentRoom.getName());
            System.out.println(world.getSpaceInfo(currentRoom));
            output.append(world.getSpaceInfo(currentRoom)).append("\n");
          } else {
            System.out.println("Invalid input. Please enter 'm' or 'q'.");
            output.append("Invalid input. Please enter 'm' or 'q'.\n");
          }
        }
      }

      // Generate the world map and save it as an image
      System.out.println("\nGenerating world map...");
      output.append("\nGenerating world map...\n");
      world.generateWorldMap("");
      System.out.println("World map saved successfully!");
      output.append("World map saved successfully!\n");

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
