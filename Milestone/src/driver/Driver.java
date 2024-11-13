package driver;

import controller.Controller;
import gameworld.World;
import gameworld.WorldInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Random;
import pet.PetInterface;
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
    try {
      // Create a TeePrintStream that writes to both System.out and a file
      PrintStream fileOut = new PrintStream(new FileOutputStream("output.txt", false));
      TeePrintStream tee = new TeePrintStream(System.out, fileOut);
      System.setOut(tee);
      output = System.out;

      // Check if the necessary arguments are provided
      if (args.length < 1) {
        try {
          errorOutput.append(
              "Usage: java -jar Milestone3.jar <path-to-world-specification-file or string> "
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
        output.append(target.toString());

        // Display pet information
        PetInterface pet = world.getPet();
        output.append(pet.toString()).append("\n");

        // Show space information, including items
        // output.append("\nSpace information for each room:\n");
        // for (RoomInterface room : world.getRooms()) {
        // output.append(world.getSpaceInfo(room)).append("\n");
        // }

        // Start the game using the controller, passing the max turns
        Controller controller = new Controller(new InputStreamReader(System.in), output);
        controller.start(world, maxTurns, new Random());

        // Save the output to a file
        // saveOutputToFile();
        output.append("Output saved to output.txt\n");
      } catch (IOException e) {
        try {
          errorOutput.append("World file is invalid: ").append(e.getMessage()).append("\n");
        } catch (IOException ioException) {
          handleError("Failed to output error message.");
        }
      }
    } catch (IOException e) {
      handleError("Failed to output usage information.");
    }
  }

  /**
   * Saves the program output to a file.
   * 
   * @param content The content to be saved.
   */
  private static void saveOutputToFile() {

    File outputFile = new File("output.txt");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

      writer.write(String.valueOf(output));
      output.append("Output saved to output.txt\n");
      writer.close();
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

  /**
   * Custom PrintStream to write output to both System.out and a file.
   */
  static class TeePrintStream extends PrintStream {
    private final PrintStream second;

    public TeePrintStream(PrintStream primary, PrintStream second) {
      super(primary, true); // Set autoFlush to true
      this.second = second;
    }

    @Override
    public void print(String s) {
      super.print(s);
      second.print(s);
      flush();
    }

    @Override
    public void print(boolean b) {
      super.print(b);
      second.print(b);
      flush();
    }

    @Override
    public void print(int i) {
      super.print(i);
      second.print(i);
      flush();
    }

    @Override
    public void print(char c) {
      super.print(c);
      second.print(c);
      flush();
    }

    @Override
    public void print(double d) {
      super.print(d);
      second.print(d);
      flush();
    }

    @Override
    public void println(int i) {
      super.println(i);
      second.println(i);
      flush();
    }

    @Override
    public void println(String s) {
      super.println(s);
      second.println(s);
      flush();
    }

    @Override
    public void println(char c) {
      super.println(c);
      second.println(c);
      flush();
    }

    @Override
    public void println(boolean b) {
      super.println(b);
      second.println(b);
      flush();
    }

    @Override
    public void println(double d) {
      super.println(d);
      second.println(d);
      flush();
    }

    @Override
    public void flush() {
      super.flush();
      second.flush();
    }

    // Override other necessary print/println methods as needed
  }
}
