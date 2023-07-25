package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.BattleSalvoController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.ManualDataEntry;
import java.io.Reader;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * controller test
 */
public class BattleSalvoControllerTest {
  BattleSalvoController controller;
  Appendable output;
  String test;
  String testInput;
  Reader input;
  BattleSalvoView view;
  ManualDataEntry dataEntry;
  AiPlayer ap;
  UserPlayer up;

  /**
   * setting up player input
   */
  @BeforeEach
  public void setup() {
    testInput = """
        6 6
        1 1 1 1
        0 0
        0 1
        3 3
        5 5
        0 0
        0 1
        3 3
        5 5
        1 1
        1 2
        0 0
        0 1
        3 3
        5 5
        3 3
        5 5
        """;

    input = new StringReader(testInput);
    output = new StringBuilder();
    view = new BattleSalvoView(input, output);
    dataEntry = new ManualDataEntry(input);
    ap = new AiPlayer(new Random(1));
    up = new UserPlayer(new Random(1), dataEntry);
    controller = new BattleSalvoController(input, output, new Random(1));
  }

  /**
   * testing if initial setup is correct
   */
  @Test
  public void testRun() {
    assertEquals("", output.toString());
    test = """
        Welcome to the BattleSalvo Game!
        Please enter a valid height and width below between 6 and 15 inclusive.


        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]. Remember, your fleet may not exceed size 6.
        Opponent Board Data:
        Your Board:
        """;
    controller.run();
    assertEquals(test, output.toString());
  }
}
