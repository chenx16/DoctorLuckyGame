package coordinate;

import java.util.Objects;

/**
 * Represents a 2D coordinate with x and y values.
 */
public class Coordinate implements CoordinateInterface {
  private final int x;
  private final int y;

  /**
   * Constructs a coordinate with the specified x and y values.
   * 
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public CoordinateInterface copy() {
    return new Coordinate(this.x, this.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coordinate other = (Coordinate) obj;
    return x == other.x && y == other.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Coordinate(" + x + ", " + y + ")";
  }
}
