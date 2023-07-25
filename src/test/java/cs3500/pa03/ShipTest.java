package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * testing ship class
 */
public class ShipTest {
  ArrayList<Coord> destroyerList =
      new ArrayList<>(Arrays.asList(new Coord(0, 1), new Coord(0, 2),
          new Coord(0, 3), new Coord(0, 4)));
  Ship shipD = new Ship(ShipType.DESTROYER, destroyerList);

  ArrayList<Coord> subList =
      new ArrayList<>(Arrays.asList(new Coord(2, 1), new Coord(2, 2),
          new Coord(2, 3)));
  Ship shipS = new Ship(ShipType.SUBMARINE, subList);

  /**
   * testing get coordinates
   */
  @Test
  public void testGetCoordinates() {
    assertEquals(destroyerList, shipD.getCoordinates());
    assertEquals(subList, shipS.getCoordinates());
  }

  /**
   * testing string representation
   */
  @Test
  public void testToString() {
    String subString = "Ship: SUBMARINE Coordinates: [(2,1), (2,2), (2,3)]";
    assertEquals(subString, shipS.toString());
  }
}
