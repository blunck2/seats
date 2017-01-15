package seats.model;

import static seats.common.Messages.*;

import java.util.List;
import java.util.ArrayList;


/**
 * <p>
 * Convenience class for producing Seats
 * </p>
 */ 
public class SeatFactory {

  /**
   * Private constructor to enforce non-instantiability
   */ 
  private SeatFactory() { }

  
  /**
   * Creates seats for a row given a row number, the number of seats
   * in the row, and the number of seats that are considered to be
   * "center row"
   * @param rowNumber the row of the seat
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSize the number of seats in the row that are
   * considered to be "center row"
   * @throws IllegalArgumentException if the rowNumber or rowSeatCount
   * are <= to 0
   */
  public static List<Seat> createSeatsForRow(int rowNumber,
                                             int rowSeatCount) {
    if ((rowNumber <= 0) || (rowSeatCount <= 0)) {
      throw new IllegalArgumentException(EMPTY_VENUE_NOT_PERMITTED);
    }

    List<Seat> seats = new ArrayList<>();
    for (int seatNumber = 1; seatNumber <= rowSeatCount; seatNumber++) {
      Seat seat = createSeat(rowNumber, seatNumber);
      seats.add(seat);
    }

    return seats;
  }


  /**
   * Creates a Seat given a row and seat number
   * @param rowNumber the row of the seat
   * @param seatNumber the seat number in the row
   * @throws IllegalArgumentException if the rowNumber or seatNumber are <= 0
   */
  private static Seat createSeat(int rowNumber,
                                 int seatNumber) {
    if ((rowNumber <= 0) || (seatNumber <= 0)) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }
    
    Seat seat = new Seat();
    seat.setRowNumber(rowNumber);
    seat.setSeatNumber(seatNumber);

    return seat;
  }

  
  
}
