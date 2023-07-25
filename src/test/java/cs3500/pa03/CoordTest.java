package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.pa03.model.Coord;
import org.junit.jupiter.api.Test;

/**
 * testing coord class
 */
public class CoordTest {
  Coord coord1 = new Coord(0, 1);
  Coord coord2 = new Coord(2, 7);
  Coord coord3 = new Coord(2, 7);

  /**
   * testing getting x coordinate
   */
  @Test
  public void testGetX() {
    assertEquals(0, coord1.getCoordX());
    assertEquals(2, coord2.getCoordX());
  }

  /**
   * testing get y coordinate
   */
  @Test
  public void testGetY() {
    assertEquals(1, coord1.getCoordY());
    assertEquals(7, coord2.getCoordY());
  }

  /**
   * testing equals
   */
  @Test
  public void testEquals() {
    assertNotEquals(coord1, coord2);
    assertEquals(coord2, coord3);
  }
}
