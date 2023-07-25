package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.view.ManualDataEntry;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing user player
 */
public class UserPlayerTest {
  Reader input = new StringReader("(0, 1)");
  ManualDataEntry manualDataEntry = new ManualDataEntry(input);
  UserPlayer userPlayer = new UserPlayer(new Random(1), manualDataEntry);
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
   * setup to generate ships w/ random coordinats
   */
  @BeforeEach
  public void setup() {
    userPlayer.setup(height, width, shipTypeIntegerMap);
  }

  /**
   * testing if inital shots are counted properly
   */
  @Test
  public void testCountInitialShots() {
    assertEquals(6, userPlayer.countInitialShots(shipTypeIntegerMap));
  }

  /**
   * testing setup method: i.e. if given a height, width, and ship parameters, a
   * list of randomly generated ships should be created with coordinates
   * that fit on the board & don't overlap
   */
  @Test
  public void testSetup() {
    List<Ship> ships = userPlayer.setup(height, width, shipTypeIntegerMap);
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

    System.out.println(ships.toString());
    assertEquals(testShips.size(), ships.size());
  }

  /**
   * testing if Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board
   */
  @Test
  public void testReportDamage() {
    //userPlayer.setup(height, width, shipTypeIntegerMap);
    ArrayList<Coord> coord5 = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3),
        new Coord(0, 4), new Coord(0, 5)));

    ArrayList<Coord> coord6 = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(4, 3), new Coord(4, 4)));

    List<Ship> testShips = new ArrayList<>(Arrays.asList(new Ship(ShipType.DESTROYER, coord6),
        new Ship(ShipType.CARRIER, coord5)));

    List<Coord> opponentShots = new ArrayList<>(Arrays.asList(new Coord(4, 1),
        new Coord(4, 2), new Coord(6, 6)));

    List<Coord> damage = userPlayer.reportDamage(opponentShots);
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

    assertEquals(missedShots, userPlayer.missedShots(opponentShots, opponentShotsHit));
  }

  /**
   * testing player name
   */
  @Test
  public void testName() {
    assertEquals("Player", userPlayer.name());
  }

  /**
   * testing succesful hits
   */
  @Test
  public void testSuccessfulHits() {
    // board before hits:
    Board board = new UserBoard(height, width);
    ArrayList<Coord> battleShipCoord = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2)));

    char[][] initialBoard = board.createBoard(new ArrayList<>(Arrays.asList(
        new Ship(ShipType.BATTLESHIP, battleShipCoord))));

    char[][] expectedBoard = new char[][] {
        {'X', 'X', 'X', '0', '0', '0'},
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

    userPlayer.successfulHits(damage);
    board.updateBoard(damage, 'H');

    char[][] hitBoard = new char[][] {
        {'H', 'H', 'X', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0'}};

    assertArrayEquals(hitBoard, initialBoard);
  }
}