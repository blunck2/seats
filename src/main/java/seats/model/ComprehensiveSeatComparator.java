package seats.model;

import java.util.Comparator;

/**
 * <p>
 * A Comparator for Seat instances that considers their row number,
 * whether or not they are considered "center row", and whether or not
 * they are an aisle seat.
 * </p>
 */
public class ComprehensiveSeatComparator implements Comparator<Seat> {
  protected static int CENTER_SEAT_WEIGHT = 3;
  protected static int CENTER_ROW_WEIGHT = 2;
  protected static int AISLE_SEAT_WEIGHT = 1;
  protected static int DEFAULT_WEIGHT = 0;
  protected static int ROW_MULTIPLIER = 10;


  /**
   * Assess a Seat's value based on it's position within the row and it's
   * proximity to the stage.
   */
  protected int scoreSeat(Seat seat) {
    int score = 0;

    // the initial score is based on the row
    score += seat.getRowNumber() * ROW_MULTIPLIER;

    // increase score based on if the seat is an aisle seat
    if (seat.isAisleSeat()) {
      score += AISLE_SEAT_WEIGHT;
    }

    // increase score based on if the seat is considered in the "center row"
    if (seat.isCenterRow()) {
      score += CENTER_ROW_WEIGHT;
    }

    // increase score based on if the seat is the center seat of the row
    if (seat.isCenterSeat()) {
      score += CENTER_SEAT_WEIGHT;
    }

    return score;
  }


  /**
   * @see Comparator#compare
   */ 
  public int compare(Seat seat1, Seat seat2) {
    int score1 = scoreSeat(seat1);
    int score2 = scoreSeat(seat2);

    return score1 - score2;
  }
}
