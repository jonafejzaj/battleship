package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * testing method in ShipType
 */
public class ShipTypeTest {
  /**
   * testing get size
   */
  @Test
  public void testGetSize() {
    assertEquals(6, ShipType.CARRIER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(3, ShipType.SUBMARINE.getSize());
  }
}
