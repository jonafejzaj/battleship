package cs3500.pa03.model;

/**
 * represents coordinates of a board
 */
public class Coord {
  private final int coordX;
  private final int coordY;

  /**
   * constructor for coordinates
   *
   * @param x coordinate
   * @param y coordinate
   */
  public Coord(int x, int y) {
    this.coordX = x;
    this.coordY = y;
  }

  /**
   * gets x coordinate
   *
   * @return value of x coordinate
   */
  public int getCoordX() {
    return this.coordX;
  }

  /**
   * gets y coordinate
   *
   * @return value of y coordinate
   */
  public int getCoordY() {
    return this.coordY;
  }

  /**
   * string representation of x coordinate
   *
   * @return (x,y) string
   */
  public String toString() {
    return "(" + coordX + "," + coordY + ")";
  }

  /**
   * override equals for testing purposes
   *
   * @param other object
   * @return true or false if this and that object is equal, i.e. contain the same
     coordinate
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Coord that)) {
      return false;
    }
    return this.coordX == that.coordX && this.coordY == that.coordY;
  }
}
