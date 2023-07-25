package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * represents a user board whose contents will be shown to terminal
 */
public class UserBoard extends Board {
  private final char[][] board;

  /**
   * initialize board to size of height (rows) and width (columns)
   *
   * @param height of board
   * @param width  of board
   */
  public UserBoard(int height, int width) {
    super();
    this.board = new char[height][width];
  }

  /**
   * creates a board for a user showing where the randomly generated ships are set
   *
   * @param ships list of ships containing their type and coordinates they occupy
   * @return a board with ships marked in their proper respective locations
   */
  @Override
  public char[][] createBoard(List<Ship> ships) {
    // initialize board to containing all '0'
    for (char[] chars : board) {
      Arrays.fill(chars, '0');
    }

    for (Ship ship : ships) {
      // get coordinates of this ship
      ArrayList<Coord> coordinates = ship.getCoordinates();
      for (Coord coordinate : coordinates) {
        int x = coordinate.getCoordX();
        int y = coordinate.getCoordY();
        // set row and column with "x" to denote a piece of a ship exists in this location
        board[x][y] = 'X';
      }
    }
    return board;
  }

  /**
   * changes value of element in 2d array with given marker for each shot that hit
   * an opponents ship, signifying the hit
   *
   * @param shotsHitOpponentBoard list of coordinates that hit a ship on opponents board
   * @param marker                character to replace element in array so shots can be viewed
   */
  @Override
  public void updateBoard(List<Coord> shotsHitOpponentBoard, char marker) {
    for (Coord coordinate : shotsHitOpponentBoard) {
      int x = coordinate.getCoordX();
      int y = coordinate.getCoordY();
      // set row and column with marker to denote a piece of a ship exists in this location
      board[x][y] = marker;
    }
  }

  /**
   * gets current board
   *
   * @return board
   */
  public char[][] getBoard() {
    return board;
  }
}
