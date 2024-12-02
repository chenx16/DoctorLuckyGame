package view;

import gameworld.World;
import gameworld.WorldInterface;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
//    // Create an instance of GameView
//    GameView gameView = new GameView();
//
//    // Display the game view to test its components
//    gameView.display();

    // Ensure correct number of command line arguments

//    if (args.length < 1) {
//      System.out.println("Usage: java Main <world_file_path>");
//      System.exit(1);
//    }
//
//    String worldFilePath = args[0];
    String worldFilePath = "./res/mansion.txt";
    WorldInterface world = new World();

    // Load the world from file
    try (FileReader reader = new FileReader(worldFilePath)) {
      world.loadFromFile(reader);
      System.out.println("World loaded successfully from: " + worldFilePath);
    } catch (IOException e) {
      System.err.println("Error loading the world: " + e.getMessage());
      System.exit(1);
    }

    SwingUtilities.invokeLater(() -> {
      GameView view = new GameView(world);
      view.setVisible(true);
    });
  }
}
