package cs3500.pa03;

import cs3500.pa03.controller.BattleSalvoController;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = new PrintStream(System.out);
    BattleSalvoController controller = new BattleSalvoController(input, output);
    controller.run();
    controller.gamePlay();
  }
}