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
  private String worldFilePath;

  /**
   * Constructs a {@code GamePanel} that represents the visual component of the
   * game world. The panel initializes by rendering the world map and setting its
   * size based on the map dimensions.
   *
   * @param world the game world model to be rendered, which provides the rooms,
   *              players, and other elements of the game.
   * @throws IllegalArgumentException if the provided {@code world} is
   *                                  {@code null}.
   */

  public GamePanel(WorldInterface world, String worldFilePath) {
    this.world = world;
    this.worldFilePath = worldFilePath;
    pixel = this.world.getPixel();
    setBackground(Color.WHITE);
    try {
      worldImage = world.generateWorldMap(worldFilePath);
      // Set preferred size based on the world image dimensions
      setPreferredSize(new Dimension(worldImage.getWidth(), worldImage.getHeight()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Renders the game world, including players, target, and other elements.
   *
   * @param g the graphics context to use for rendering.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(worldImage, 0, 0, this);

    // Add any additional painting logic for players, target, etc.
    drawPlayers(g);
    drawTarget(g);
  }

  /**
   * Draws the world representation.
   *
   * @param g the graphics context to use for rendering.
   */
  public void drawWorld(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(50, 50, 600, 400); // Example room representation
  }

  /**
   * Draws the target character within its current room.
   *
   * @param g the graphics context to use for rendering.
   */
  public void drawTarget(Graphics g) {
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

  /**
   * Draws all players within their respective rooms.
   *
   * @param g the graphics context to use for rendering.
   */
  public void drawPlayers(Graphics g) {
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
          g.fillOval(drawX, drawY, playerSize, playerSize);
        }
      }
    }
  }

  /**
   * Retrieves the boundary rectangle of a player for interaction purposes.
   *
   * @param player the player whose boundary is being retrieved.
   * @return a {@code Rectangle} representing the player's boundary, or
   *         {@code null} if not found.
   */
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
          int offsetX = (playerIndex % 3) * offset;
          int offsetY = (playerIndex / 3) * offset;

          int drawX = centerX - playerSize / 2 + offsetX;
          int drawY = centerY - playerSize / 2 + offsetY;

          return new Rectangle(drawX, drawY, playerSize, playerSize);
        }
      }
    }
    return null; // If the player is not found in any room
  }

  /**
   * Retrieves the boundary rectangle of a room for interaction purposes.
   *
   * @param room the room whose boundary is being retrieved.
   * @return a {@code Rectangle} representing the room's boundary, or {@code null}
   *         if not found.
   */
  public Rectangle getRoomBounds(RoomInterface room) {
    if (room == null || room.getCoordinateUpperLeft() == null
        || room.getCoordinateLowerRight() == null) {
      return null;
    }

    CoordinateInterface upperLeft = room.getCoordinateUpperLeft();
    CoordinateInterface lowerRight = room.getCoordinateLowerRight();

    int x = upperLeft.getY() * pixel;
    int y = upperLeft.getX() * pixel;
    int width = (lowerRight.getY() - upperLeft.getY() + 1) * pixel;
    int height = (lowerRight.getX() - upperLeft.getX() + 1) * pixel;

    return new Rectangle(x, y, width, height);
  }

}
