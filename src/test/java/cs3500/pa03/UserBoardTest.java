package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing board class
 */
public class UserBoardTest {
  ArrayList<Coord> destroyerList =
      new ArrayList<>(Arrays.asList(new Coord(0, 1), new Coord(0, 2),
          new Coord(0, 3), new Coord(0, 4)));
  Ship shipD = new Ship(ShipType.DESTROYER, destroyerList);

  ArrayList<Coord> subList =
      new ArrayList<>(Arrays.asList(new Coord(2, 1), new Coord(2, 2),
          new Coord(2, 3)));
  Ship shipS = new Ship(ShipType.SUBMARINE, subList);

  ArrayList<Coord> battleShipList = new ArrayList<>(Arrays.asList(
      new Coord(3, 1),
      new Coord(3, 2),
      new Coord(3, 3),
      new Coord(3, 4),
      new Coord(3, 5)));
  Ship shipB = new Ship(ShipType.BATTLESHIP, battleShipList);

  ArrayList<Coord> carrierList = new ArrayList<>(Arrays.asList(new Coord(0, 6),
          new Coord(1, 6),
          new Coord(2, 6),
          new Coord(3, 6),
          new Coord(4, 6),
          new Coord(5, 6)));
  Ship shipC = new Ship(ShipType.CARRIER, carrierList);

  ArrayList<Ship> ships = new ArrayList<>(Arrays.asList(shipD, shipB, shipC, shipS));

  UserBoard userBoard = new UserBoard(7, 7);
  char[][] board = userBoard.createBoard(ships);

  char[][] expectedBoard = new char[][] {
      {'0', 'X', 'X', 'X', 'X', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', 'X'},
      {'0', 'X', 'X', 'X', '0', '0', 'X'},
      {'0', 'X', 'X', 'X', 'X', 'X', 'X'},
      {'0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', 'X'},
      {'0', '0', '0', '0', '0', '0', '0'}};

  /**
   * testing board creation
   */
  @Test
  public void testCreateBoard() {
    assertArrayEquals(expectedBoard, board);
  }

  /**
   * testing getting board
   */
  @Test
  public void testGetBoard() {
    assertArrayEquals(board, userBoard.getBoard());
  }

  /**
   * testing updating of shot representation
   */
  @Test
  public void testUpdateBoard() {
    List<Coord> shots = new ArrayList<>(Arrays.asList(new Coord(0, 1),
        new Coord(3, 1)));

    userBoard.updateBoard(shots, 'H');

    char[][] updatedBoard = new char[][] {
        {'0', 'H', 'X', 'X', 'X', '0', 'X'},
        {'0', '0', '0', '0', '0', '0', 'X'},
        {'0', 'X', 'X', 'X', '0', '0', 'X'},
        {'0', 'H', 'X', 'X', 'X', 'X', 'X'},
        {'0', '0', '0', '0', '0', '0', 'X'},
        {'0', '0', '0', '0', '0', '0', 'X'},
        {'0', '0', '0', '0', '0', '0', '0'}};

    assertArrayEquals(updatedBoard, board);
  }
}
