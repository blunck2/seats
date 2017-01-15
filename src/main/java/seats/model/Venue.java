package seats.model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static seats.common.Messages.*;


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
   * Returns the row with the row number provided
   * @throws IllegalArgumentException if the rowNumber is 0 or
   * negative, or if the rowNumber exceeds the number of rows in the
   * Venue
   */
  public Row getRow(int rowNumber) {
    // if the row number is 0 or negative throw an exception
    if (rowNumber <= 0) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }

    // if the row number is greater than the number of rows in the venue throw an exception
    if (rowNumber > rows.size()) {
      throw new IllegalArgumentException(ROW_NUMBER_DOES_NOT_EXIST);
    }

    return rows.get(rowNumber - 1);
  }
  
  /**
   * Returns the number of rows in the venue
   */
  public int getRowCount() { return rows.size(); }


  /**
   * Holds a seat in the venue
   * @param rowNumber the row number of the seat
   * @param seatNumber the seat number in the row
   * @param customerEmailAddress the email address for the customer
   * holding the seat
   * @throws IllegalArgumentException if the seat does not exist
   * @throws SeatUnavailableException if the seat is not open
   */
  public void holdSeat(int rowNumber, int seatNumber, String customerEmailAddress)
    throws SeatUnavailableException {

    // if the row number is 0 or negative throw an exception
    if (rowNumber <= 0) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }

    // if the row number is greater than the number of rows in the venue throw an exception
    if (rowNumber > rows.size()) {
      throw new IllegalArgumentException(ROW_NUMBER_DOES_NOT_EXIST);
    }

    
    
    
      

  }

}
