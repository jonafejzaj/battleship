package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * ship class containing its type and coordinates
 */
public class Ship {
  private final ShipType shipType;
  private final ArrayList<Coord> coordinates;

  /**
   * @param shipType    type of ship: submarine, carrier, battleship, destroyer
   * @param coordinates representing where a part of a ship resides on board
   */
  public Ship(ShipType shipType, ArrayList<Coord> coordinates) {
    this.shipType = shipType;
    this.coordinates = coordinates;
  }

  /**
   * list of coordinates of this ship
   *
   * @return a list of coordinates
   */
  public ArrayList<Coord> getCoordinates() {
    return this.coordinates;
  }

  /**
   * string representation of a ship
   *
   * @return name of ship and a list of its coordinates as a string
   */
  @Override
  public String toString() {
    return "Ship: " + shipType + " Coordinates: " + coordinates;
  }
}