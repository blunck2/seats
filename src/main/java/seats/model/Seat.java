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

  // set to true to indicate this seat is an aisle seat
  private boolean isAisleSeat = false;

  // set to true to indicate this seat is considered "center row"
  private boolean isCenterRow = false;

  /*
   * set to true to indicate this seat is considered to be exactly in
   * the center of the row
   */
  private boolean isCenterSeat = false;

  // set to true to indicate the seat is held
  private boolean isHeld = false;

  // set to true to indicate the seat is reserved
  private boolean isReserved = false;


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
   * Returns true if this seat is considered an aisle seat
   */
  public boolean isAisleSeat() { return isAisleSeat; }

  /**
   * Sets the isAisleSeat flag
   */ 
  public void setAisleSeat(boolean isAisleSeat) {
    this.isAisleSeat = isAisleSeat;
  }

  
  /**
   * Returns true if this seat is considered part of the "center row"
   */
  public boolean isCenterRow() { return isCenterRow; }

  /**
   * Sets the isCenterRow flag
   */
  public void setCenterRow(boolean isCenterRow) {
    this.isCenterRow = isCenterRow;
  }


  /**
   * Returns true if this seat is considered the center of the row
   */
  public boolean isCenterSeat() { return isCenterSeat; }

  /**
   * Sets the isCenterSeat flag
   */
  public void setCenterSeat(boolean isCenterSeat) {
    this.isCenterSeat = isCenterSeat;
  }

  
  /**
   * Returns true if this seat is held
   */
  public boolean isHeld() { return isHeld; }

  
  /**
   * Holds the seat for potential reservation
   */
  public void hold() { this.isHeld = true; }

  
  /**
   * Returns true if this seat is reserved
   */
  public boolean isReserved() { return isReserved; }

  /**
   * Reserves the seat 
   */
  public void reserve() { this.isReserved = true; }
  
}
