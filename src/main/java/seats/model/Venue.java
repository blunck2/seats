package seats.model;

import java.util.Map;
import java.util.List;

import static seats.common.Messages.*;


/**
 * <p>
 * Representation of the performance house.
 * </p>
 *
 * <p>
 * The current implementation is primarily concerned with managing seats
 * within the venue.  Additional extension points could exist for other
 * nominal features of the venue during a performance (lighting conditions,
 * if food service is available, if different pricing models are in place
 * for seats, etc).
 * </p>
 */
public class Venue {
  // all of the seats in the venue indexed by row number
  private Map<Integer, List<Seat>> seatsByRow;

  // the number of seats in a given row that are designated as "center row"
  private int centerRowSize;

  // the number of rows in the venue
  private int rowCount;

  // the number of seats in a row
  private int rowSeatCount;


  /**
   * Creates a Venue
   */
  public Venue() { }

  
  /**
   * Returns the seats indexed by their row number
   */
  public Map<Integer, List<Seat>> getSeatsByRow() { return seatsByRow; }

  /**
   * Sets the seats indexed by their row number
   */
  public void setSeatsByRow(Map<Integer, List<Seat>> seatsByRow) {
    this.seatsByRow = seatsByRow;
  }

  /**
   * Sets the seats for a given row
   * @param row the row for the seats
   * @param seats the seats in the row
   */
  public void addSeatsForRow(int row, List<Seat> seats) {
    if (row <= 0) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }
    
    seatsByRow.put(row, seats);
  }

  /**
   * Returns the number of seats considered to be center row
   */
  public int getCenterRowSize() { return centerRowSize; }

  /**
   * Sets the number of seats considered to be center row
   */
  public void setCenterRowSize(int centerRowSize) {
    this.centerRowSize = centerRowSize;
  }

  /**
   * Returns the number of rows in the venue
   */
  public int getRowCount() { return rowCount; }

  /**
   * Sets the number of rows in the venue
   */
  public void setRowCount(int rowCount) { this.rowCount = rowCount; }

  /**
   * Returns the number of seats in a row
   */
  public int getRowSeatCount() { return rowSeatCount; }

  /**
   * Sets the number of seats in a row
   */
  public void setRowSeatCount(int rowSeatCount) {
    this.rowSeatCount = rowSeatCount;
  }
}
