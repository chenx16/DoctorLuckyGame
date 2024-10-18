package coordinate;

/**
 * Represents a coordinate in the game world.
 */
public interface CoordinateInterface {

  /**
   * Gets the x-coordinate.
   * 
   * @return the x-coordinate value
   */
  int getX();

  /**
   * Gets the y-coordinate.
   * 
   * @return the y-coordinate value
   */
  int getY();

  /**
   * Creates a defensive copy of the current coordinate.
   * 
   * @return a new instance of the coordinate with the same values
   */
  CoordinateInterface copy();
}
