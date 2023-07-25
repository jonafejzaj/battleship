package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * represents the Ai player of a game
 */
public class AiPlayer implements Player {
  private final Random rand;
  private List<Ship> ships;
  private int boardWidth;
  private int boardHeight;

  /**
   * random seed for testing
   *
   * @param rand seed
   */
  public AiPlayer(Random rand) {
    this.rand = rand;
  }

  /**
   * truly random constructor
   */
  public AiPlayer() {
    this(new Random());
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Ai";
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
   * generates a random coordinate for ai to setup board with
   *
   * @param height of board
   * @param width of board
   * @return a new random generated coordinate
   */
  private Coord generateRandomCoordinates(int height, int width) {
    int x = rand.nextInt(height);
    int y = rand.nextInt(width);
    return new Coord(x, y);
  }

  /**
   * helper for setup, aids in generating a random list of coordinates per given ship
   *
   * @param height of board
   * @param width of board
   * @param shipType type of shit
   *
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
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    ArrayList<Coord> shots = new ArrayList<>();
    while (shots.size() < 6) {
      Coord randCoord = generateRandomCoordinates(6, 6);
      if (!shots.contains(randCoord)) {
        shots.add(randCoord);
      }
    }
    return shots;
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
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    AiBoard board = new AiBoard(boardHeight, boardWidth);
    board.updateBoard(shotsThatHitOpponentShips, 'H');
  }

  /**
   * list of coordinates of missed shots Ai generated
   *
   * @param shots    total shots Ai generated
   * @param hitShots shots that actually hit a ship
   * @return a filtered list containing the shots Ai missed
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