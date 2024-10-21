package coordinate;

import java.util.Objects;

/**
 * Represents a 2D coordinate with x and y values.
 */
public class Coordinate implements CoordinateInterface {
  private final int x0;
  private final int y0;

  /**
   * Constructs a coordinate with the specified x and y values.
   * 
   * @param x0 the x-coordinate
   * @param y0 the y-coordinate
   */
  public Coordinate(int x0, int y0) {
    this.x0 = x0;
    this.y0 = y0;
  }

  @Override
  public int getX() {
    return x0;
  }

  @Override
  public int getY() {
    return y0;
  }

  @Override
  public CoordinateInterface copy() {
    return new Coordinate(this.x0, this.y0);
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
    return x0 == other.x0 && y0 == other.y0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x0, y0);
  }

  @Override
  public String toString() {
    return "Coordinate(" + x0 + ", " + y0 + ")";
  }
}
