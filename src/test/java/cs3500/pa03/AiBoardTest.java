package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import cs3500.pa03.model.AiBoard;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing AI board
 */
public class AiBoardTest {
  ArrayList<Coord> destroyerList =
      new ArrayList<>(Arrays.asList(new Coord(0, 1), new Coord(0, 2),
          new Coord(0, 3), new Coord(0, 4)));
  Ship shipD = new Ship(ShipType.DESTROYER, destroyerList);

  ArrayList<Coord> subList =
      new ArrayList<>(Arrays.asList(new Coord(2, 2), new Coord(2, 3),
          new Coord(2, 4)));
  Ship shipS = new Ship(ShipType.SUBMARINE, subList);

  ArrayList<Coord> battleShipList = new ArrayList<>(Arrays.asList(
      new Coord(3, 2),
      new Coord(3, 3),
      new Coord(3, 4),
      new Coord(3, 5),
      new Coord(3, 6)));
  Ship shipB = new Ship(ShipType.BATTLESHIP, battleShipList);

  ArrayList<Coord> carrierList = new ArrayList<>(Arrays.asList(new Coord(0, 0),
      new Coord(0, 1),
      new Coord(0, 2),
      new Coord(0, 3),
      new Coord(0, 4),
      new Coord(0, 5)));
  Ship shipC = new Ship(ShipType.CARRIER, carrierList);

  ArrayList<Ship> ships = new ArrayList<>(Arrays.asList(shipD, shipB, shipC, shipS));

  AiBoard aiBoard = new AiBoard(7, 7);
  char[][] board = aiBoard.createBoard(ships);

  char[][] expectedBoard = new char[][] {
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'}};

  /**
   * testing board creation
   */
  @Test
  public void testCreateBoard() {
    assertArrayEquals(expectedBoard, board);
  }

  /**
   * testing get board
   */
  @Test
  public void testGetBoard() {
    assertArrayEquals(board, aiBoard.getBoard());
  }

  /**
   * testing the updating of a board
   */
  @Test
  public void testUpdateBoard() {
    List<Coord> shots = new ArrayList<>(Arrays.asList(new Coord(0, 1),
        new Coord(0, 3)));

    aiBoard.updateBoard(shots, 'H');

    char[][] updatedBoard = new char[][] {
        {'0', 'H', '0', 'H', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'},
        {'0', '0', '0', '0', '0', '0', '0'}};

    assertArrayEquals(updatedBoard, board);
  }
}
