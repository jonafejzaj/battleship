package cs3500.pa03.controller;

import cs3500.pa03.model.AiBoard;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.ManualDataEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * connects the model and view components so game can run properly
 */
public class BattleSalvoController {
  private final Readable input;
  private final Appendable output;
  private final BattleSalvoView view;
  private ManualDataEntry manualDataEntry;
  private UserPlayer userPlayer;
  private AiPlayer aiPlayer;
  private final Random rand;
  private Map<ShipType, Integer> specifications;
  private UserBoard boardUser;
  private AiBoard boardAi;
  private int height;
  private int width;


  /**
   * random seed constructor
   *
   * @param input  Readable allows for testing and for the purpose of the game is implemented
   *               as an input stream reader to read from System.in
   * @param output Appendable allows for testing and for the purpose of the game is implemented
   *               as print stream to append System.out
   * @param rand   seed for testing
   */
  public BattleSalvoController(Readable input, Appendable output, Random rand) {
    this.rand = rand;
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    view = new BattleSalvoView(this.input, this.output);
    manualDataEntry = new ManualDataEntry(this.input);
    this.userPlayer = new UserPlayer(manualDataEntry);
    this.aiPlayer = new AiPlayer();
  }

  /**
   * truly random constructor
   *
   * @param input  Readable allows for testing and for the purpose of the game is implemented
   *               as an input stream reader to read from System.in
   * @param output Appendable allows for testing and for the purpose of the game is implemented
   *               as print stream to append System.out
   */
  public BattleSalvoController(Readable input, Appendable output) {
    this(input, output, new Random());
  }

  /**
   * connects the view and model aspects of the program to give user prompts and use
   * the input to begin the gameplay and set up
   */
  public void run() {
    // welcome message
    view.showPrompt("Welcome to the BattleSalvo Game!");

    // valid width/height
    String validHeightWidth = "Please enter a valid height "
        + "and width below between 6 and 15 inclusive.";
    String dimensionError = "Uh Oh! You've entered invalid dimensions."
        + " The height and width of the game must be in the range\n (6, 15), inclusive."
        + " Please try again!";

    int[] dimensions = getValidDimensions(validHeightWidth, dimensionError);
    height = dimensions[0];
    width = dimensions[1];
    ArrayList<Integer> fleetSizes = validFleetSize(height, width);

    // map of shiptype to integer
    specifications = shipTypeToAmtMap(fleetSizes);

    // initial board creation:
    // opponents board and view
    view.showPrompt("Opponent Board Data:");
    boardAi = new AiBoard(height, width);
    List<Ship> aiShipList = aiPlayer.setup(height, width, specifications);
    char[][] opponentBoard = boardAi.createBoard(aiShipList);
    view.boardView(opponentBoard);

    // players board and view
    view.showPrompt("Your Board:");
    boardUser = new UserBoard(height, width);
    List<Ship> userShipList = userPlayer.setup(height, width, specifications);
    char[][] playingBoard = boardUser.createBoard(userShipList);
    view.boardView(playingBoard);
  }

  /**
   * allows for game play mode
   */
  public void gamePlay() {
    char hit = 'H';
    char miss = 'M';

    // inital shots correspond with amount of ships user has
    int shotsRemaining = userPlayer.countInitialShots(specifications);

    while (shotsRemaining != 0) {
      view.showPrompt("Please Enter " + shotsRemaining + " Shots. "
          + "A shot consists of an X (first argument) and Y (second argument) coordinate");
      List<Coord> userShots = userPlayer.takeShots();
      List<Coord> aiShots = aiPlayer.takeShots();

      // shots ai hit that caused damage on user's ship
      List<Coord> userDamage = userPlayer.reportDamage(aiShots);
      // shots user hit that caused damage on ai's ship
      List<Coord> aiDamage = aiPlayer.reportDamage(userShots);

      userPlayer.successfulHits(userDamage);
      aiPlayer.successfulHits(aiDamage);

      List<Coord> userMissed = userPlayer.missedShots(aiShots, userDamage);
      boardUser.updateBoard(userDamage, hit);
      boardAi.updateBoard(aiDamage, hit);

      List<Coord> aiMissed = aiPlayer.missedShots(userShots, aiDamage);
      boardUser.updateBoard(userMissed, miss);
      boardAi.updateBoard(aiMissed, miss);

      view.showPrompt("Opponent Board Data:");
      char[][] updatedOpponentBoard = boardAi.getBoard();
      view.boardView(updatedOpponentBoard);

      view.showPrompt("Your Board:");
      char[][] updatedUserBoard = boardUser.getBoard();
      view.boardView(updatedUserBoard);

      shotsRemaining--;
    }

    view.showPrompt("Game Over");
  }

  /**
   * reprompt user to enter dimensions if any are below 6 or greater than 15 (or both)
   *
   * @param prompt to reask user
   * @param errorMessage to give user
   */
  private int[] getValidDimensions(String prompt, String errorMessage) {
    view.showPrompt(prompt);
    int[] dimensions = new int[2];
    dimensions[0] = view.getVal(""); // height
    dimensions[1] = view.getVal(""); // width

    while (dimensions[0] < 6 || dimensions[0] > 15 || dimensions[1] < 6 || dimensions[1] > 15) {
      view.showPrompt(errorMessage);
      dimensions[0] = view.getVal(""); // height
      dimensions[1] = view.getVal(""); // width
    }

    return dimensions;
  }

  /**
   * combine user fleets into one list & reprompt user to enter if invalid input
   *
   * @param height of board
   * @param width  of borad
   * @return list of fleet sizes indiciating numbers user enters
   */
  private ArrayList<Integer> validFleetSize(int height, int width) {
    boolean validFleetSize = false;
    // prompt and error
    String fleetSizePrompt = "Please enter your fleet in the order "
        + "[Carrier, Battleship, Destroyer, Submarine]."
        + " Remember, your fleet may not exceed size "
        + Math.min(height, width) + ".";

    String fleetSizeError = "Uh Oh! You've entered invalid fleet sizes.\n "
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + Math.min(height, width)
        + ".";
    // determine amount of ship types so scanner only reads until this amount is reached
    int totalShipTypes = ShipType.values().length;
    ArrayList<Integer> fleetSizes = new ArrayList<>();

    while (!validFleetSize) {
      fleetSizes = view.listOfValues(fleetSizePrompt, ShipType.values().length);
      int sum = 0;
      boolean invalidSize = false;

      for (int i = 0; i < fleetSizes.size(); i++) {
        // gather sum of user input
        sum += fleetSizes.get(i);
        // if any entry is 0 or sum of fleet amount is greater than min dimension
        // prompt user again
        if (fleetSizes.get(i) == 0 || sum > Math.min(height, width)) {
          invalidSize = true;
          break;
        }
      }
      if (invalidSize) {
        view.showPrompt(fleetSizeError);
      } else {
        validFleetSize = true;
      }
    }
    return fleetSizes;
  }

  /**
   * create a hashmap of ship types to their fleet sizes
   *
   * @param fleetSizes list of amount of each ship user entered
   * @return a hashmap of ships to their corresponding amount values
   */
  private Map<ShipType, Integer> shipTypeToAmtMap(ArrayList<Integer> fleetSizes) {
    // given list of user input for fleetsize add elements to hashmap
    Map<ShipType, Integer> specifications = new HashMap<>();
    ArrayList<Ship> ships = new ArrayList<>();
    ShipType[] shipTypes = ShipType.values();

    for (int i = 0; i < fleetSizes.size(); i++) {
      // amount of user specified fleet per ship
      int amount = fleetSizes.get(i);
      // user must enter in order: [Carrier, Battleship, Destroyer, Submarine]
      ShipType shipType = shipTypes[i];
      specifications.put(shipType, amount);
    }
    return specifications;
  }
}