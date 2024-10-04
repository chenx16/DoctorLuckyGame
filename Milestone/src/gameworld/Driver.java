
package gameworld;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * takes a world specification file as input and showcases various
 * functionalities of the model such as loading the world, displaying space
 * information, moving the target character, and generating the world map.
 */
public class Driver {
  /**
   * The main method that drives the program. This method uses a command-line
   * argument to specify the world specification file. It demonstrates the
   * following functionalities:
   * <ul>
   * <li>Loading the world from a file</li>
   * <li>Displaying information about each room (including items and
   * neighbors)</li>
   * <li>Moving the target character to new rooms</li>
   * <li>Generating the world map and saving it as an image file</li>
   * </ul>
   *
   * @param args the command-line arguments. The first argument must be the file
   *             path to the world specification file.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: java -jar Milestone1.jar <path-to-world-specification-file>");
      System.exit(1);
    }

    String worldFilePath = args[0];
    File worldFile = new File(worldFilePath);

    // Check if the input is a valid file
    if (!worldFile.exists() || !worldFile.isFile()) {
      System.err.println("Error: The specified file does not exist or is not a valid file.");
      System.exit(1);
    }

    WorldInterface world = new World();

    try (FileReader fileReader = new FileReader(worldFile)) {
      // Load the world from the specified file
      System.out.println("Loading world from: " + worldFilePath);
      world.loadFromFile(fileReader);

      // Show the name of the world
      System.out.println("World: " + world.getName());

      // Display target character information
      TargetInterface target = world.getTargetCharacter();
      System.out
          .println("Target character: " + target.getName() + " with health: " + target.getHealth());

      // Show space information for each room in the world
      System.out.println("\nSpace information for each room:");
      for (RoomInterface room : world.getRooms()) {
        System.out.println(world.getSpaceInfo(room));
      }

      // Display target character initial room information
      RoomInterface initialRoom = world.getTargetCharacter().getCurrentRoom();
      System.out.println("Target is now in: " + initialRoom.getName());

      // Move the target character and display the updated room
      System.out.println("\nMoving the target character...");
      world.moveTargetCharacter();
      RoomInterface secondRoom = world.getTargetCharacter().getCurrentRoom();
      System.out.println("Target is now in: " + secondRoom.getName());

      world.moveTargetCharacter();
      RoomInterface thirdRoom = world.getTargetCharacter().getCurrentRoom();
      System.out.println("Target is now in: " + thirdRoom.getName());

      world.moveTargetCharacter();
      RoomInterface fourthRoom = world.getTargetCharacter().getCurrentRoom();
      System.out.println("Target is now in: " + fourthRoom.getName());

      // Generate the world map and save it as an image
      System.out.println("\nGenerating world map...");
      world.generateWorldMap();

      System.out.println("World map saved successfully!");

    } catch (IOException e) {
      System.err.println("Failed to load or process the world file: " + e.getMessage());
    }
  }
}
