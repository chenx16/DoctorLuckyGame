package view;

import java.awt.Graphics;
import java.awt.Rectangle;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * Interface for the {@code GamePanel}, defining methods to render and interact
 * with the game world, players, and target.
 */
public interface GamePanelInterface {

  /**
   * Renders the game world, including players, target, and other elements.
   *
   * @param g the graphics context to use for rendering.
   */
  void paintComponent(Graphics g);

  /**
   * Draws the world representation.
   *
   * @param g the graphics context to use for rendering.
   */
  void drawWorld(Graphics g);

  /**
   * Draws the target character within its current room.
   *
   * @param g the graphics context to use for rendering.
   */
  void drawTarget(Graphics g);

  /**
   * Draws all players within their respective rooms.
   *
   * @param g the graphics context to use for rendering.
   */
  void drawPlayers(Graphics g);

  /**
   * Retrieves the boundary rectangle of a player for interaction purposes.
   *
   * @param player the player whose boundary is being retrieved.
   * @return a {@code Rectangle} representing the player's boundary, or
   *         {@code null} if not found.
   */
  Rectangle getPlayerBounds(PlayerInterface player);

  /**
   * Retrieves the boundary rectangle of a room for interaction purposes.
   *
   * @param room the room whose boundary is being retrieved.
   * @return a {@code Rectangle} representing the room's boundary, or {@code null}
   *         if not found.
   */
  Rectangle getRoomBounds(RoomInterface room);
}
