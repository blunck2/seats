package seats.model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static seats.common.Messages.*;

import org.apache.commons.lang.StringUtils;


/**
 * <p>
 * Representation of the performance house.
 * </p>
 */
public class Venue {
  // all of the rows in the venue
  private List<Row> rows;


  /**
   * Creates a Venue
   */
  public Venue() {
    rows = new ArrayList<>();
  }

  /**
   * Returns the rows in the venue
   */
  public List<Row> getRows() { return rows; }

  /**
   * Sets the rows in the venue
   */
  public void setRows(List<Row> rows) { this.rows = rows; }

  /**
   * Adds a row to the venue
   */
  public void addRow(Row row) { rows.add(row); }

  /**
   * Validates that the rowNumber provided exists.
   * @throws IllegalArgumentException if the rowNumber is invalid
   */
  void validateRowNumber(int rowNumber) {
    // if the row number is 0 or negative throw an exception
    if (rowNumber <= 0) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }

    // if the row number is greater than the number of rows in the venue throw an exception
    if (rowNumber > rows.size()) {
      throw new IllegalArgumentException(ROW_NUMBER_DOES_NOT_EXIST);
    }
  }

  
  /**
   * Validates that the email address is not blank or null
   * @throws IllegalArgumentException if the email address is blank or null
   */
  void validateEmailAddress(String emailAddress) {
    // if the email address is blank throw an exception
    if (StringUtils.isBlank(emailAddress)) {
      throw new IllegalArgumentException(EMAIL_ADDRESS_IS_NULL_OR_BLANK);
    }

  }
  
  /**
   * Returns the row with the row number provided
   * @throws IllegalArgumentException if the rowNumber is 0 or
   * negative, or if the rowNumber exceeds the number of rows in the
   * Venue
   */
  public Row getRow(int rowNumber) {
    validateRowNumber(rowNumber);
    return rows.get(rowNumber - 1);
  }
  
  /**
   * Returns the number of rows in the venue
   */
  public int getRowCount() { return rows.size(); }

  
  /**
   * Returns the total number of seats in the venue
   */
  public int getSeatCount() {
    int totalSeats = 0;
    
    for (Row row : getRows()) {
      totalSeats += row.getSeatCount();
    }

    return totalSeats;
  }

  
  /**
   * Returns the seat at the rowNumber and seatNumber provided
   * @param rowNumber the row number of the seat
   * @param seatNumber the seat number of the seat
   * @throws IllegalArgumentException if the row or seat number are invalid
   */
  Seat findSeat(int rowNumber, int seatNumber) {
    validateRowNumber(rowNumber);

    Row row = rows.get(rowNumber - 1);
    Seat seat = row.getSeat(seatNumber);

    return seat;
  }

  /**
   * Holds a seat in the venue
   * @param rowNumber the row number of the seat
   * @param seatNumber the seat number in the row
   * @param customerEmailAddress the email address for the customer
   * holding the seat
   * @return the held seat
   * @throws IllegalArgumentException if the seat does not exist or the email address is blank or empty
   * @throws SeatUnavailableException if the seat is not open
   */
  public Seat holdSeat(int rowNumber,
                       int seatNumber,
                       String customerEmailAddress)
    throws SeatUnavailableException {

    // verify the email address is not blank or null
    validateEmailAddress(customerEmailAddress);

    // locate the seat
    Seat seat = findSeat(rowNumber, seatNumber);
    
    // if the seat is not open then throw an exception
    if (! seat.isOpen()) {
      throw new SeatUnavailableException(SEAT_IS_NOT_OPEN);
    }

    // hold the seat
    seat.hold(customerEmailAddress);

    // return the seat
    return seat;
  }


  /**
   * Reserves a seat in the venue
   * @param rowNumber the row number of the seat
   * @param seatNumber the seat number in the row
   * @param customerEmailAddress the email address for the customer
   * holding the seat
   * @return the held seat
   * @throws IllegalArgumentException if the seat does not exist or the email address is blank or empty
   * @throws SeatUnavailableException if the seat is not held by the customerEmailAddress provided or if the seat is not held at all
   */
  public Seat reserveSeat(int rowNumber,
                          int seatNumber,
                          String customerEmailAddress)
    throws SeatUnavailableException {

    // verify the email address is not blank or null
    validateEmailAddress(customerEmailAddress);

    // locate the seat
    Seat seat = findSeat(rowNumber, seatNumber);

    // verify the seat is held
    if (! seat.isHeld()) {
      throw new SeatUnavailableException(SEAT_IS_NOT_HELD);
    }

    // verify the seat is not already reserved
    if (seat.isReserved()) {
      throw new SeatUnavailableException(SEAT_IS_RESERVED);
    }

    // verify the email address matches
    String heldEmailAddress = seat.getCustomerEmailAddress();
    boolean emailAddressMatches = customerEmailAddress.equals(heldEmailAddress);
    if (! emailAddressMatches) {
      throw new IllegalArgumentException(EMAIL_ADDRESS_DOES_NOT_MATCH);
    }

    // reserve the seat
    seat.reserve();

    // return the seat
    return seat;
  }

  /**
   * Returns all of the open seats in the venue
   */
  public List<Seat> getOpenSeats() {
    List<Seat> seats = new ArrayList<>();
    for (Row row : getRows()) {
      for (Seat seat : row.getSeats()) {
        if (seat.isOpen()) {
          seats.add(seat);
        }
      }
    }

    return seats;
  }

  
  /**
   * Unholds the seat identified.
   * @param rowNumber the row number of the seat
   * @param seatNumber the seat number of the seat
   * @return the Seat after it has been unheld
   * @throws IllegalArgumentException if the rowNumber or seatNumber are <= 0
   * @throws SeatNotHeldException if the seat is open or reserved
   */
  public Seat unholdSeat(int rowNumber, int seatNumber)
    throws SeatNotHeldException {
    
    // locate the seat
    Seat seat = findSeat(rowNumber, seatNumber);

    // reserved seats cannot be unheld
    if (seat.isReserved()) {
      throw new SeatNotHeldException(SEAT_IS_RESERVED);
    }

    // open seats cannot be unheld
    if (seat.isOpen()) {
      throw new SeatNotHeldException(SEAT_IS_OPEN);
    }

    // unhold the seat
    seat.unhold();

    return seat;
  }
  

}
