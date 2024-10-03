package gameworld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WorldMapSaver {

  public static void main(String[] args) {
    WorldInterface world = new World();
    try {
      String localDir = System.getProperty("user.dir");
      world.loadFromFile(localDir + "/res/mansion.txt");

      // Generate the world map as a BufferedImage
      BufferedImage worldMap = world.generateWorldMap();

      // Save the BufferedImage to a file (e.g., PNG format)
      File outputfile = new File("worldmap.png");
      ImageIO.write(worldMap, "png", outputfile);

      System.out.println("World map saved as 'worldmap.png'!");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
