package cs3500.pa03.model;

/**
 * represents 4 possible ship types
 */
public enum ShipType {
  /**
   * carrier and its size
   */
  CARRIER(6),
  /**
   * battleship and size
   */
  BATTLESHIP(5),
  /**
   * destroyer and size
   */
  DESTROYER(4),
  /**
   * submarine and size
   */
  SUBMARINE(3);

  private final int size;

  /**
   * initialize size of ShipType
   *
   * @param size of ship (i.e. how many coordinates it takes up)
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * size of ShipType
   *
   * @return size of this ShipType
   */
  public int getSize() {
    return this.size;
  }
}
