package cs3500.pa03.model;

import java.util.List;

/**
 * abstract board class for Ai and player
 */
public abstract class Board {

  /**
   * initialize height and width
   */
  public Board() {
  }

  /**
   * 2d array representing ships occurrences based on their coordinates
   *
   * @param ships list of ships
   * @return board
   */
  public abstract char[][] createBoard(List<Ship> ships);

  /** updates board by changing element at given coordinate to the marker
   *
   * @param shotsHitOpponentBoard coordinates where an opponents ship exists
   * @param marker character to change element in board with given marker
   */
  public abstract void updateBoard(List<Coord> shotsHitOpponentBoard, char marker);
}