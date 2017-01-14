package seats.utils;

import org.apache.commons.math3.analysis.function.Floor;

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
   */
  public static int calculateCenterSeat(int rowSeatCount) {
    float exactCenterSeat = Float.valueOf(((float) rowSeatCount) / 2);
    Double adjustedCenterSeat = Math.floor(exactCenterSeat);
    return adjustedCenterSeat.intValue();
  }

  
  /**
   * Returns the minimum seat in a row with the count provided given
   * the number of seats in the row.
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSeatCount the number of seats in the row that are
   * considered center row
   */
  public static int calculateMinimumCenterSeat(int rowSeatCount,
                                               int centerRowSeatCount) {
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    // locate the middle seat
    int middleSeat = calculateCenterSeat(rowSeatCount);

    // calculate the exact minimum seat
    float exactMinimumSeat = Float.valueOf(((float) middleSeat) / 2);
    Double adjustedMinimumSeat = Math.floor(exactMinimumSeat);

    // return the seat that was partially occupied
    return adjustedMinimumSeat.intValue();    
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
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }

    // locate the middle seat
    int middleSeat = calculateCenterSeat(rowSeatCount);

    // calculate the exact maximum seat
    float exactMaximumSeat = Float.valueOf(((float) middleSeat) / 2);
    Double adjustedMaximumSeat = Math.ceil(exactMaximumSeat);

    // return the seat that was partially occupied
    return adjustedMaximumSeat.intValue();    
  }
  


  /**
   * Returns the first seat in the row that is considered to be "center row"
   *
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSeatCount the number of seats in the row
   * considered to be "center row"
   */ 
  public static int calculateMinimumCenterRowSeatNumber(int rowSeatCount,
                                                        int centerRowSeatCount) {
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }
    
    // locate the center seat
    int centerSeat = calculateCenterSeat(rowSeatCount);

    // calculate the left hand side of the seats
    int minimumCenterSeat = calculateMinimumCenterSeat(rowSeatCount, centerRowSeatCount);

    // return the seat location
    return minimumCenterSeat;
  }


  /**
   * Returns the last seat in the row that is considered to be "center row"
   *
   * @param rowSeatCount the number of seats in the row
   * @param centerRowSeatCount the number of seats in the row
   * considered to be "center row"
   */ 
  public static int calculateMaximumCenterRowSeatNumber(int rowSeatCount,
                                                        int centerRowSeatCount) {
    if (rowSeatCount < centerRowSeatCount) {
      throw new IllegalArgumentException(CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE);
    }
    
    // locate the center seat
    int centerSeat = calculateCenterSeat(rowSeatCount);

    // calculate the right hand side of the seats
    int maximumCenterSeat = calculateMaximumCenterSeat(rowSeatCount, centerRowSeatCount);

    // return the seat location
    return maximumCenterSeat;
  }

}
