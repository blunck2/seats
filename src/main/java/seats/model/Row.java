package seats.model;

import java.util.List;

import static seats.common.Messages.*;


/**
 * <p>
 * Representation of a series of Seats within the venue.
 * </p>
 *
 * <p>
 * Rows with lower row numbers are considered more favorable as they
 * are closer to the stage.
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

  /**
   * Verifies the seatNumber is valid for the row
   * @throws IllegalArgumentException if the seat does not exist
   */
  void validateSeatNumber(int seatNumber) {
    if (seatNumber <= 0) {
      throw new IllegalArgumentException(SEAT_DOES_NOT_EXIST);
    }
    
    if (seatNumber > (getSeatCount() + 1)) {
      throw new IllegalArgumentException(SEAT_DOES_NOT_EXIST);
    }
  }

  /**
   * Returns the requested
   * @throws IllegalArgumentException if the seat requested does not exist
   */
  public Seat getSeat(int seatNumber) {
    validateSeatNumber(seatNumber);
    
    return seats.get(seatNumber - 1);
  }
  

}
