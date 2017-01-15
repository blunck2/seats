package seats.model;

import static seats.common.Messages.*;

import seats.utils.SeatUtils;

import java.util.List;
import java.util.ArrayList;


/**
 *
 */
public class RowFactory {
  
  /**
   * Private constructor to enforce non-instantiability
   */
  private RowFactory() { }

  
  /**
   * Creates a Row with the number, seat count, and center row
   * configuration provided.
   * @param rowNumber the number of the row
   * @param maximumSeatCount the number of seats in the row
   * @param centerRowSize the number of seats in the row that are
   * considered to be "center row"
   * @throws IllegalArgumentException if any parameters are negative, or
   * if the rowNumber and maximumSeatCount are zero, or if the centerRowSize
   * exceeds the maximumSeatCount for the row.
   */
  public static Row createRow(int rowNumber,
                              int rowSeatCount,
                              int centerRowSeatCount) {
    // throw an exception if the row number or seat count is invalid
    if ((rowNumber < 1) || (rowSeatCount < 1)) {
      throw new IllegalArgumentException(ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
    }

    // throw an exception if the center row size is negative
    if (centerRowSeatCount < 0) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO);
    }

    // throw an exception if the centerRowSeatCount exceeds the rowSeatCount
    if (centerRowSeatCount > rowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    // determine the minimum and maximum seat that are considered center row
    int minimumCenterRow = SeatUtils.calculateMinimumCenterSeat(rowSeatCount, centerRowSeatCount);
    int maximumCenterRow = SeatUtils.calculateMaximumCenterSeat(rowSeatCount, centerRowSeatCount);

    // determine the center seat
    int centerRowSeatNumber = SeatUtils.calculateCenterSeat(rowSeatCount);

    // create the seats in the row
    List<Seat> seats = new ArrayList<>();
    for (int seatNumber = 1; seatNumber <= rowSeatCount; seatNumber++) {
      Seat seat = new Seat();

      /*
       * if the seat is the first or last seat in the row it is considered
       * to be an aisle seat
       */
      if ((seatNumber == 1) || (seatNumber == rowSeatCount)) {
        seat.setAisleSeat(true);
      }

      // if this seat is the center seat then set the center seat flag
      if (seatNumber == centerRowSeatNumber) {
        seat.setCenterSeat(true);
      }

      // if this seat is considered center row then set the center row flag
      if ((seatNumber >= minimumCenterRow) && (seatNumber <= maximumCenterRow)) {
        seat.setCenterRow(true);
      }

      seats.add(seat);
    }

    // create the Row and add the seats
    Row row = new Row();
    row.setRowNumber(rowNumber);
    row.setSeats(seats);

    return row;
  }

}
