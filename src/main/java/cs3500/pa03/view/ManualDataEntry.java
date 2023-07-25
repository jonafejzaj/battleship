package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * class to loosely connect model to view for the purpose of sending input to takeShots
 */
public class ManualDataEntry {
  private final Scanner input;

  /**
   * constructor initializes scanner
   *
   * @param reader readable input
   */
  public ManualDataEntry(Readable reader) {
    input = new Scanner(reader);
  }

  /**
   * create a list of coordinates based on user input
   *
   * @param numShotsRemaining how many shots the user has left based on ships standing
   *
   * @return a list of coordinates
   */
  public List<Coord> getShots(int numShotsRemaining) {
    List<Coord> shots = new ArrayList<>();
    // iterate until you reach number of shots remaining
    for (int i = 0; i < numShotsRemaining; i++) {
      // first integer a user enters
      int x = input.nextInt();
      // second integer a user enters
      int y = input.nextInt();
      // instantiate new coordinates with x and y values
      Coord coordinate = new Coord(x, y);
      // add to a list of coordinates
      shots.add(coordinate);
    }
    return shots;
  }
}