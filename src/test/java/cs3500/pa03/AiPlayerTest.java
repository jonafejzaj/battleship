package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.AiBoard;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing AI player
 */
public class AiPlayerTest {
  AiPlayer aiPlayer = new AiPlayer(new Random(1));
  Map<ShipType, Integer> shipTypeIntegerMap = new HashMap<>() {
    {
      put(ShipType.BATTLESHIP, 2);
      put(ShipType.DESTROYER, 1);
      put(ShipType.CARRIER, 2);
      put(ShipType.SUBMARINE, 1);
    }
  };

  int height = 7;
  int width = 6;

  /**
   * set up list of ships w/ random coorindates
   */
  @BeforeEach
  public void setup() {
    aiPlayer.setup(height, width, shipTypeIntegerMap);
  }

  /**
   * testing setup method: i.e. if given a height, width, and ship parameters, a
   * list of randomly generated ships should be created with coordinates
   * that fit on the board & don't overlap
   */
  @Test
  public void testSetup() {
    List<Ship> ships = aiPlayer.setup(height, width, shipTypeIntegerMap);
    ArrayList<Coord> coord1 = new ArrayList<>(Arrays.asList(new Coord(0, 4),
        new Coord(1, 4), new Coord(2, 4)));

    ArrayList<Coord> coord2 = new ArrayList<>(Arrays.asList(new Coord(2, 1),
        new Coord(2, 2), new Coord(2, 3), new Coord(2, 4),
        new Coord(2, 5)));

    ArrayList<Coord> coord3 = new ArrayList<>(Arrays.asList(new Coord(3, 0),
        new Coord(3, 1), new Coord(3, 2), new Coord(3, 3),
        new Coord(3, 4)));

    ArrayList<Coord> coord4 = new ArrayList<>(Arrays.asList(new Coord(5, 0),
        new Coord(5, 1), new Coord(5, 2), new Coord(5, 3),
        new Coord(5, 4), new Coord(5, 5)));

    ArrayList<Coord> coord5 = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3),
        new Coord(0, 4), new Coord(0, 5)));

    ArrayList<Coord> coord6 = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(4, 3), new Coord(4, 4)));

    List<Ship> testShips = new ArrayList<>(Arrays.asList(new Ship(ShipType.DESTROYER, coord6),
        new Ship(ShipType.SUBMARINE, coord1),
        new Ship(ShipType.CARRIER, coord4),
        new Ship(ShipType.CARRIER, coord5),
        new Ship(ShipType.BATTLESHIP, coord2),
        new Ship(ShipType.BATTLESHIP, coord3)));

    assertEquals(testShips.size(), ships.size());
  }

  /**
   * testing if Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board
   */
  @Test
  public void testReportDamage() {
    ArrayList<Coord> coord5 = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3),
        new Coord(0, 4), new Coord(0, 5)));

    ArrayList<Coord> coord6 = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(4, 3), new Coord(4, 4)));

    List<Ship> testShips = new ArrayList<>(Arrays.asList(new Ship(ShipType.DESTROYER, coord6),
        new Ship(ShipType.CARRIER, coord5)));

    List<Coord> opponentShots = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(6, 6)));

    List<Coord> damage = aiPlayer.reportDamage(opponentShots);
    List<Coord> expected = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2)));

    //assertArrayEquals(expected.toArray(), damage.toArray());
  }

  /**
   * testing missed shots
   */
  @Test
  public void testMissedShots() {
    List<Coord> opponentShots = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(6, 6)));

    List<Coord> opponentShotsHit = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2)));

    List<Coord> missedShots = new ArrayList<>(Arrays.asList(new Coord(6, 6)));

    assertEquals(missedShots, aiPlayer.missedShots(opponentShots, opponentShotsHit));
  }

  /**
   * testing name
   */
  @Test
  public void testName() {
    assertEquals("Ai", aiPlayer.name());
  }

  /**
   * testing successful hits
   */
  @Test
  public void testSuccessfulHits() {
    // board before hits:
    Board board = new AiBoard(height, width);
    ArrayList<Coord> battleShipCoord = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2)));

    char[][] initialBoard = board.createBoard(new ArrayList<>(Arrays.asList(new Ship(
        ShipType.BATTLESHIP, battleShipCoord))));

    char[][] expectedBoard = new char[][] {
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'}};
    assertArrayEquals(expectedBoard, initialBoard);


    // board after valid hits
    List<Coord> damage = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1)));

    aiPlayer.successfulHits(damage);
    board.updateBoard(damage, 'H');

    char[][] hitBoard = new char[][] {
        {'H', 'H', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'}};

    assertArrayEquals(hitBoard, initialBoard);
  }
}