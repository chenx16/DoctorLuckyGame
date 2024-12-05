package view;

import gameworld.World;
import gameworld.WorldInterface;
import java.io.FileReader;
import javax.swing.SwingUtilities;

public class Main {
//  public static void main(String[] args) {
//    String worldFilePath = "./res/mansion.txt";
//    WorldInterface world = new World();
//
//    // Load the world from file
//    try (FileReader reader = new FileReader(worldFilePath)) {
//      world.loadFromFile(reader);
//      System.out.println("World loaded successfully from: " + worldFilePath);
//    } catch (IOException e) {
//      System.err.println("Error loading the world: " + e.getMessage());
//      System.exit(1);
//    }
//
//    SwingUtilities.invokeLater(() -> {
//      GameView view = new GameView(world);
//      view.setVisible(true);
//    });
//  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        // Step 1: Create the World (load from file or manually instantiate)
        WorldInterface world = new World();

        // Assuming you load the world from a file
        String worldFilePath = "./res/mansion.txt"; // Replace with actual path
        Readable worldFile = new FileReader(worldFilePath);
        world.loadFromFile(worldFile);

        // Step 2: Create the GameView
        GameView view = new GameView(world, worldFilePath);
//        ViewController viewController = new ViewController(view, world);
        view.setVisible(true);

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
