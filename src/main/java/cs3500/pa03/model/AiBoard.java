package cs3500.pa03.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * represents the Ai board that is hidden to user
 */
public class AiBoard extends Board {
  private final char[][] board;

  /**
   * initialize abstracted elements and create new board with given dimensions
   *
   * @param height of board
   * @param width of board
   */
  public AiBoard(int height, int width) {
    super();
    this.board = new char[height][width];
  }

  /**
   * create AI board
   *
   * @param ships list of ships
   * @return board that is private so user cannot see AI ship placements
   */
  @Override
  public char[][] createBoard(List<Ship> ships) {
    IntStream.range(0, board.length).forEach(i -> Arrays.fill(board[i], '0'));
    return board;
  }

  /**
   * changes value of element in 2d array with given marker for each shot that hit
   * an opponents ship, signifying the hit
   *
   * @param shotsHitOpponentBoard list of coordinates that hit a ship on opponents board
   * @param marker character to replace element in array so shots can be viewed
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
   * get this board
   *
   * @return board
   */
  public char[][] getBoard() {
    return board;
  }
}