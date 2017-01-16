package seats.model;

import java.util.Comparator;

/**
 * <p>
 * A Comparator for Seat instances that only considers their row number
 * </p>
 * 
 * <p>
 * This comparator for seats compares two seats and returns the seat that
 * is closer to the stage (has the lower row number)
 * </p>
 */
public class RowPrioritizedSeatComparator implements Comparator<Seat> {

  /**
   * @see Comparator#compare
   */ 
  public int compare(seats.model.Seat seat1, seats.model.Seat seat2) {
    int row1 = seat1.getRowNumber();
    int row2 = seat2.getRowNumber();

    return row1 - row2;
  }
}
