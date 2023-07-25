package cs3500.pa03.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * viewable system output
 */
public class BattleSalvoView {
  private final Scanner input;
  private final Appendable output;

  /**
   * constructor initializes system in scanner allowing user to type input
   *
   * @param reader system in
   * @param append system out
   */
  public BattleSalvoView(Readable reader, Appendable append) {
    output = append;
    input = new Scanner(reader);
  }

  /**
   * prints given message
   *
   * @param prompt to be printed
   */
  public void showPrompt(String prompt) {
    try {
      output.append(prompt).append("\n");
    } catch (IOException e) {
      System.out.println("IO error");
    }
  }

  /**
   * prints prompt and returns user input
   *
   * @param prompt instructions given so user can play properly
   * @return stored value of user input
   */
  public int getVal(String prompt) {
    try {
      output.append(prompt).append("\n");
    } catch (IOException e) {
      System.out.println("IO error");
    }
    return input.nextInt();
  }

  /**
   * adds all integers a user inputs to system.in with a given size restriction to an arrayList
   * and returns that list
   *
   * @param prompt to print for user
   * @param size how many consecutive items to read for (in context of game this is the amount
   *             of ships users should enter a value 4)
   * @return arraylist of integers user inputs
   */
  public ArrayList<Integer> listOfValues(String prompt, int size) {
    ArrayList<Integer> values = new ArrayList<>();
    try {
      output.append(prompt).append("\n");
      for (int i = 0; i < size; i++) {
        int next = input.nextInt();
        values.add(next);
      }
    } catch (IOException e) {
      System.out.println("IO error");
    }
    return values;
  }

  /**
   * prints a view of given board by removing commas and brackets of 2D-array
   *
   * @param board 2D array to print out based on certain specifications
   */
  public void boardView(char[][] board) {
    for (char[] chars : board) {
      for (char marker : chars) {
        System.out.print(marker + " ");
      }
      System.out.println();
    }
  }
}