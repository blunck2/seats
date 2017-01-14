package seats.model;

import static seats.common.Messages.*;

import seats.utils.SeatUtils;

import java.lang.IllegalArgumentException;


/**
 * <p>
 * Representation of a Seat in the venue.
 * </p>
 *
 * <p> 
 * A seat has a row number that can be used to infer distance from
 * the stage.
 * </p>
 *
 * <p> 
 * The first row of seats in a venue has a rowNumber of 1 and the
 * maximum rowNumber is bounded by Integer.MAX_VALUE.  As the row
 * number of a seat increases it's value decreases.  
 * </p>
 *
 * <p>
 * 
 * </p>
 */
public class Seat {
  // the location of the seat in relation to distance from the stage
  private int rowNumber;

  // the location of the seat in relation to the entry aisle
  private int seatNumber;

  // set to true to indicate the seat adjoins the aisle
  private boolean isAisleSeat;

  // set the true if the seat is considered in "the center row"
  private boolean isCenterRow;


  /**
   * Creates a Seat
   */
  public Seat() { }

  
  /**
   * Returns the row number
   */
  public int getRowNumber() { return rowNumber; }

  /**
   * Sets the row number
   */
  public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

  /**
   * Returns the seat number
   */
  public int getSeatNumber() { return seatNumber; }

  /**
   * Sets the seat number
   */
  public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

  
  /**
   * Returns true if the seat is an aisle seat, false otherwise
   * 
   * @param rowLength the number of seats in the row
   */
  public boolean isAisleSeat(int rowSeatCount) {
    return (seatNumber == 1) || (seatNumber == rowSeatCount);
  }

  
  /**
   * Returns true if this seat is considered center row, false otherwise
   * 
   * @param centerRowSeatCount the number of seats in the middle of
   * the row that are considered center row
   * @param rowSeatCount the number of seats in the row
   * @throws IllegalArgumentException if the centerRowSeatCount
   * exceeds the rowSeatCount
   */
  public boolean isCenterRow(int centerRowSeatCount, int rowSeatCount) {    
    /*
     * ensure the number of seats that are identified as "center row" is
     * less than or equal to the total number of seats in the row.
     */
    if (rowSeatCount <= centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    // locate the middle seat
    int centerSeat = SeatUtils.calculateCenterSeat(rowSeatCount);

    // identify min and max seats to be considered center row
    //    int minCenterRowSeatNumber = centerSeat 
     
    
    //    return (seatNumber >

    return true;
  
  }

}
