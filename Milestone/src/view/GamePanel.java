package view;

import coordinate.CoordinateInterface;
import gameworld.WorldInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.JPanel;
import player.PlayerInterface;
import room.RoomInterface;
import target.TargetInterface;

/**
 * GamePanel class is responsible for rendering the game world, including
 * players, the target, and other elements.
 */
public class GamePanel extends JPanel {
  private WorldInterface world;
  private BufferedImage worldImage;
  private int pixel;

  public GamePanel(WorldInterface world) {
    this.world = world;
    pixel = this.world.getPixel();
    setBackground(Color.WHITE);
    try {
      worldImage = world.generateWorldMap("./res/mansion.txt");
      // Set preferred size based on the world image dimensions
      setPreferredSize(new Dimension(worldImage.getWidth(), worldImage.getHeight()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(worldImage, 0, 0, this);

    // Add any additional painting logic for players, target, etc.
    drawPlayers(g);
    drawTarget(g);
  }

  // Draw the world map
  private void drawWorld(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(50, 50, 600, 400); // Example room representation
  }

  private void drawTarget(Graphics g) {
    TargetInterface target = world.getTargetCharacter();
    if (target != null) {
      RoomInterface targetRoom = target.getCurrentRoom();
      if (targetRoom != null) {
        CoordinateInterface upperLeft = targetRoom.getCoordinateUpperLeft();
        CoordinateInterface lowerRight = targetRoom.getCoordinateLowerRight();

        int roomWidth = (lowerRight.getY() - upperLeft.getY() + 1) * pixel;
        int roomHeight = (lowerRight.getX() - upperLeft.getX() + 1) * pixel;

        // Calculate the center of the room
        int centerX = (upperLeft.getY() * pixel) + roomWidth * 7 / 8;
        int centerY = (upperLeft.getX() * pixel) + roomHeight / 5;

        // Draw the target character in the center of the room
        int targetSize = 10;
        g.setColor(Color.RED);
        // g.drawString(target.getName(), x, y - 10);
        g.fillOval(centerX - targetSize / 2, centerY - targetSize / 2, targetSize, targetSize);
      }
    }
  }

  private void drawPlayers(Graphics g) {
    for (RoomInterface room : world.getRooms()) {
      List<PlayerInterface> playersInRoom = room.getPlayers();
      if (!playersInRoom.isEmpty()) {
        // Get room's coordinates
        CoordinateInterface upperLeft = room.getCoordinateUpperLeft();
        CoordinateInterface lowerRight = room.getCoordinateLowerRight();

        int roomWidth = (lowerRight.getY() - upperLeft.getY() + 1) * pixel;
        int roomHeight = (lowerRight.getX() - upperLeft.getX() + 1) * pixel;

        // Calculate the center of the room
        int centerX = (upperLeft.getY() * pixel) + roomWidth / 8;
        int centerY = (upperLeft.getX() * pixel) + roomHeight / 2;

        // Adjust player size and define an offset for multiple players
        int playerSize = 10;
        int offset = 35;

        for (int i = 0; i < playersInRoom.size(); i++) {
          PlayerInterface player = playersInRoom.get(i);
          if (player.getIsComputerControlled()) {
            g.setColor(Color.BLUE);
          } else {
            g.setColor(Color.GREEN);
          }
          // Offset each player slightly so they do not overlap
          int offsetX = (i % 3) * offset; // Change this if you want to place them differently
          int offsetY = (i / 3) * offset;

          // Calculate the position to draw the player
          int drawX = centerX - playerSize / 2 + offsetX;
          int drawY = centerY - playerSize / 2 + offsetY;

          // Draw the player
//        g.drawString(player.getName(), x, y - 10);
          g.fillOval(drawX, drawY, playerSize, playerSize);
        }
      }
    }
  }

  public Rectangle getPlayerBounds(PlayerInterface player) {
    for (RoomInterface room : world.getRooms()) {
      if (room.getPlayers().contains(player)) {
        CoordinateInterface upperLeft = room.getCoordinateUpperLeft();
        CoordinateInterface lowerRight = room.getCoordinateLowerRight();

        int roomWidth = (lowerRight.getY() - upperLeft.getY() + 1) * pixel;
        int roomHeight = (lowerRight.getX() - upperLeft.getX() + 1) * pixel;

        // Calculate the center of the room for placing players
        int centerX = (upperLeft.getY() * pixel) + roomWidth / 8;
        int centerY = (upperLeft.getX() * pixel) + roomHeight / 2;

        int playerSize = 10;
        int offset = 35;

        // Find the player's position within the room and return its bounds
        int playerIndex = room.getPlayers().indexOf(player);
        if (playerIndex >= 0) {
          int offsetX = (playerIndex % 3) * offset; // Change this if you want to place them
                                                    // differently
          int offsetY = (playerIndex / 3) * offset;

          int drawX = centerX - playerSize / 2 + offsetX;
          int drawY = centerY - playerSize / 2 + offsetY;

          return new Rectangle(drawX, drawY, playerSize, playerSize);
        }
      }
    }
    return null; // If the player is not found in any room
  }

  public Rectangle getRoomBounds(RoomInterface room) {
    // TODO Auto-generated method stub
    return null;
  }

}
