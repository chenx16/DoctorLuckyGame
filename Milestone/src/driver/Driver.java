package driver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import controller.Controller;
import controller.ControllerInterface;
import gameworld.World;
import gameworld.WorldInterface;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * takes a world specification file or string as input and delegates the control
 * of the game to the Controller class.
 */
public class Driver {

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
      // Replace literal "\n" with actual newline characters
      worldData = worldData.replace("\\n", "\n");
      inputSource = new StringReader(worldData);
    }

    try {
      // Load the world from the specified input (file or string)
      System.out.println("Loading world...");
      output.append("Loading world...\n");
      world.loadFromFile(inputSource);

      // Create a controller and start the game
      ControllerInterface controller = new Controller(world, new Scanner(System.in));
      controller.startGame();

    } catch (IOException e) {
      System.err.println("Failed to load scanner or process the world input: " + e.getMessage());
    }
  }

}
