package cs3500.pa03.model;

import cs3500.pa03.view.ManualDataEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * class representing console player
 */
public class UserPlayer implements Player {
  private final Random rand;
  private final ManualDataEntry manualDataEntry;
  private int remainingShots;
  private List<Ship> ships;
  private int boardHeight;
  private int boardWidth;

  /**
   * testing constructor
   *
   * @param rand            seed for testing
   * @param manualDataEntry allows for connection between view and model to obtain shots
   */
  public UserPlayer(Random rand, ManualDataEntry manualDataEntry) {
    this.rand = rand;
    this.manualDataEntry = manualDataEntry;
  }

  /**
   * truly random constructor
   *
   * @param manualDataEntry allows for connection between view and model to obtain shots
   */
  public UserPlayer(ManualDataEntry manualDataEntry) {
    this(new Random(), manualDataEntry);
    remainingShots = 0;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Player";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    ships = new ArrayList<>();
    boardHeight = height;
    boardWidth = width;
    // iterate over hashmap
    for (Map.Entry<ShipType, Integer> specs : specifications.entrySet()) {
      ShipType shipType = specs.getKey();
      int shipCount = specs.getValue();
      // perform for each amount of ship per fleet
      for (int i = 0; i < shipCount; i++) {
        ArrayList<Coord> coordinates = shipPlacement(height, width, shipType);
        // create new ship object with list of coordinates the same length as its size
        Ship ship = new Ship(shipType, coordinates);
        ships.add(ship);
      }
    }
    return ships;
  }

  /**
   * randomly generated coordinates capped at height & width of board
   *
   * @param height of board
   * @param width  of board
   * @return random coordinate
   */
  private Coord generateRandomCoordinates(int height, int width) {
    int x = rand.nextInt(height);
    int y = rand.nextInt(width);
    return new Coord(x, y);
  }

  /**
   * helper for setup, aids in generating a random list of coordinates per given ship
   *
   * @param height   of board
   * @param width    of board
   * @param shipType type of shit
   * @return a randomly generated list of valid coordinates whose size depends on ship type
   */
  private ArrayList<Coord> shipPlacement(int height, int width, ShipType shipType) {
    ArrayList<Coord> coordinates = new ArrayList<>();

    boolean isHorizontal = rand.nextBoolean();

    int coordX;
    int coordY;

    while (true) {
      Coord coord = generateRandomCoordinates(height, width);

      int boundaryH;
      int boundaryV;

      if (isHorizontal) {
        boundaryH = coord.getCoordY() + shipType.getSize() - 1;
        boundaryV = coord.getCoordX();
      } else {
        boundaryH = coord.getCoordY();
        boundaryV = coord.getCoordX() + shipType.getSize() - 1;
      }

      if (boundaryH >= 0 && boundaryH < width && boundaryV >= 0 && boundaryV < height) {
        coordinates.add(coord);

        for (int i = 1; i < shipType.getSize(); i++) {
          coordX = coord.getCoordX() + (isHorizontal ? 0 : i);
          coordY = coord.getCoordY() + (isHorizontal ? i : 0);
          Coord c = new Coord(coordX, coordY);
          coordinates.add(c);
        }

        break;
      }
    }
    return coordinates;
  }

  /**
   * counts number of shots a user can take
   *
   * @param specifications shiptype to number of occurrences mapping
   * @return initial amount of shots a user can take
   */
  public int countInitialShots(Map<ShipType, Integer> specifications) {
    for (int value : specifications.values()) {
      remainingShots += value;
    }
    return remainingShots;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return manualDataEntry.getShots(remainingShots);
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<>();
    // check each coordinate in opponents shot
    for (Coord shot : opponentShotsOnBoard) {
      // see if ships coordinates contain said shot
      for (Ship ship : ships) {
        if (ship.getCoordinates().contains(shot)) {
          // add to list if so
          hits.add(shot);
        }
      }
    }
    return hits;
  }

  /**
   * list of missed shots a player made as coordinates
   *
   * @param shots    total shots player look
   * @param hitShots shots they hit
   * @return filtered list of shots they missed
   */
  public List<Coord> missedShots(List<Coord> shots, List<Coord> hitShots) {
    ArrayList<Coord> missed = new ArrayList<>();
    // if the current element in shots is not in the hit list add it to the new list
    for (Coord shot : shots) {
      if (!(hitShots.contains(shot))) {
        missed.add(shot);
      }
    }
    return missed;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    UserBoard board = new UserBoard(boardHeight, boardWidth);
    board.updateBoard(shotsThatHitOpponentShips, 'H');
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }
}