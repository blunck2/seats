package seats.model;

import java.util.List;


/**
 * <p>
 * Representation of a series of Seats within the venue.
 * </p>
 */
public class Row {
  // the numerical id of the row
  private int rowNumber;

  // the seats in the row
  private List<Seat> seats;

  /**
   * Creates an empty Row
   */
  public Row() { }


  /**
   * Returns the row number
   */
  public int getRowNumber() { return rowNumber; }

  /**
   * Sets the row number
   */
  public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

  /**
   * Returns the seats in the row
   */
  public List<Seat> getSeats() { return seats; }

  /**
   * Sets the seats in the row
   */
  public void setSeats(List<Seat> seats) { this.seats = seats; }

  /**
   * Returns the number of seats in the row
   */
  public int getSeatCount() { return seats.size(); }
  

}
