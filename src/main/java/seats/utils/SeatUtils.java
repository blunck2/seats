package seats.utils;

import static seats.common.Messages.*;



/**
 * <p>
 * Utility methods that facilitate usage of seats.model classes
 * </p>
 */
public class SeatUtils {

  /**
   * Returns the middle seat number given the number of seats in a
   * row.  The middle seat is determined to be the mathematical floor
   * of the rowSeatCount divided by 2.
   * @param rowSeatCount the number of seats in the row
   * @throws IllegalArgumentException if a 0 or negative rowSeatCount is provided
   */
  public static int calculateCenterSeat(int rowSeatCount) {
    // row sizes of 0 or negative are not supported
    if (rowSeatCount < 1) {
      throw new IllegalArgumentException(ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO);
    }

    int mod = rowSeatCount % 2;
    int center = (rowSeatCount / 2) + mod;

    return center;
  }

  
  /**
   * Returns the minimum seat in a row with the count provided given
   * the number of seats in the row.
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSeatCount the number of seats in the row that are
   * considered center row
   * @throws IllegalArgumentException if negative or 0 values are
   * passed as arguments or if more seats are considered center row
   * than are actually in the row
   */
  public static int calculateMinimumCenterSeat(int rowSeatCount,
                                               int centerRowSeatCount) {
    /*
     * throw an exception if there are more center row seats than
     * there are seats in the row
     */
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    /*
     * throw an exception if there are not any seats in the row that
     * are considered center
     */
    if (centerRowSeatCount == 0) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO);
    }

    /*
     * calculate the minimum center row seat by subtracting the 
     * seat count that is considered "center row" from the overall
     * seat count for the row.  this produces how many seats are
     * considered "non center row".  div that amount by 2 and add 
     * the value to the first seat in order to determine the first
     * seat considered to be "center row"
     */
    int nonCenterRowSeatCount = rowSeatCount - centerRowSeatCount;
    int splitNonCenterRowSeatCount = nonCenterRowSeatCount / 2;
    int minimumCenterRowSeat = 1 + splitNonCenterRowSeatCount;

    return minimumCenterRowSeat;
  }

  
  /**
   * Returns the maximum seat in a row with the count provided given
   * the number of seats in the row.
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSeatCount the number of seats in the row that are
   * considered center row
   */
  public static int calculateMaximumCenterSeat(int rowSeatCount,
                                               int centerRowSeatCount) {
    /*
     * throw an exception if there are more center row seats than
     * there are seats in the row
     */
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    /*
     * throw an exception if there are not any seats in the row that
     * are considered center
     */
    if (centerRowSeatCount == 0) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO);
    }

    /*
     * if only 1 seat is considered center row then the maximum center
     * row seat is the same as the minimum center row seat
     */
    if (centerRowSeatCount == 1) {
      return calculateMinimumCenterSeat(rowSeatCount, centerRowSeatCount);
    }

    /*
     * to calculate the maximum center row seat first locate the minimum 
     * center row seat and then add the number of seats considered to be
     * center row
     */
    int minimumCenterRowSeat = calculateMinimumCenterSeat(rowSeatCount, centerRowSeatCount);
    int maximumCenterRowSeat = minimumCenterRowSeat + centerRowSeatCount - 1;

    return maximumCenterRowSeat;
  }
  


}
