package driver;

import controller.Controller;
import gameworld.World;
import gameworld.WorldInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Random;
import javax.swing.SwingUtilities;
import view.GameView;

/**
 * Driver class to demonstrate the functionality of the game world model. It
 * handles both graphical and text-based modes.
 */
public class Driver {
  private static Appendable output = System.out;
  private static Appendable errorOutput = System.err;

  /**
   * The main entry point for the program. This method initializes the game by
   * reading command-line arguments and starting the appropriate interface.
   *
   * @param args command-line arguments: - "view" to launch the graphical
   *             interface. - A file path or string representing the world
   *             specification. - Max turns (optional).
   */
  public static void main(String[] args) {
    try {
      // Create a TeePrintStream that writes to both System.out and a file
      PrintStream fileOut = new PrintStream(new FileOutputStream("output.txt", false));
      TeePrintStream tee = new TeePrintStream(System.out, fileOut);
      System.setOut(tee);
      output = System.out;

      if (args.length < 1) {
        errorOutput.append("Usage: java -jar Milestone4.jar [view] "
            + "<path-to-world-specification-file> <max-turns (optional)>\n");
        System.exit(1);
      }

      String worldData = args[0];
      boolean isViewMode = "view".equalsIgnoreCase(worldData);

      // Adjust arguments if "view" is specified
      if (isViewMode && args.length < 2) {
        errorOutput.append("Usage: java -jar Milestone4.jar view "
            + "<path-to-world-specification-file> <max-turns (optional)>\n");
        System.exit(1);
      }

      String worldFilePath = isViewMode ? args[1] : args[0];
      int maxTurns = args.length > (isViewMode ? 2 : 1) ? Integer.parseInt(args[isViewMode ? 2 : 1])
          : 20;

      WorldInterface world = new World();
      Readable inputSource = loadWorldSource(worldFilePath);
      world.loadFromFile(inputSource);

      if (isViewMode) {
        // Launch the graphical user interface
        launchViewMode(world, worldFilePath, maxTurns);
      } else {
        // Launch the text-based interface
        launchTextMode(world, worldFilePath, maxTurns);
      }
    } catch (IOException ex) {
      handleError("An error occurred: " + ex.getMessage());
    }
  }

  /**
   * Loads the world source as a readable stream.
   *
   * @param worldFilePath Path to the world file or inline data.
   * @return Readable input source.
   */
  private static Readable loadWorldSource(String worldFilePath) throws IOException {
    File worldFile = new File(worldFilePath);
    if (worldFile.exists() && worldFile.isFile()) {
      return new FileReader(worldFile);
    } else {
      return new StringReader(worldFilePath.replace("\\n", "\n"));
    }
  }

  /**
   * Launches the text-based interface.
   *
   * @param world         the game world.
   * @param worldFilePath the world file path or description.
   * @param maxTurns      the maximum number of turns allowed.
   */
  private static void launchTextMode(WorldInterface world, String worldFilePath, int maxTurns) {
    try {
      output.append("Launching text-based mode...\n");
      Controller controller = new Controller(new InputStreamReader(System.in), output);
      controller.start(world, maxTurns, new Random());
    } catch (IOException e) {
      handleError("Text-based mode encountered an error: " + e.getMessage());
    }
  }

  /**
   * Launches the graphical user interface.
   *
   * @param world         the game world.
   * @param worldFilePath the path to the world file.
   * @param maxTurns      the maximum number of turns allowed.
   */
  private static void launchViewMode(WorldInterface world, String worldFilePath, int maxTurns) {
    SwingUtilities.invokeLater(() -> {
      try {
        output.append("Launching graphical mode...\n");
        GameView view = new GameView(world, worldFilePath, maxTurns);
        view.setVisible(true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
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
    public void println(String s) {
      super.println(s);
      second.println(s);
      flush();
    }

    @Override
    public void flush() {
      super.flush();
      second.flush();
    }
  }
}
