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
 */
public class Seat {
  // the location of the seat in relation to distance from the stage
  private int rowNumber;

  // the location of the seat in relation to the entry aisle
  private int seatNumber;


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

  
}
