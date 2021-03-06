package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

import static seats.model.ComprehensiveSeatComparator.*;


/**
 * <p>
 * Unit tests for the ComprehensiveSeatComparatorTest
 * </p>
 */
public class ComprehensiveSeatComparatorTest {

  @Test
  public void testScoreSeat() {
    ComprehensiveSeatComparator comparator = new ComprehensiveSeatComparator();

    // check aisle seat
    Seat seat = new Seat();
    seat.setRowNumber(1);
    seat.setAisleSeat(true);
    int expected = (ROW_MULTIPLIER * 1) - AISLE_SEAT_WEIGHT;
    int actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);

    // check center seat
    seat = new Seat();
    seat.setRowNumber(1);
    seat.setCenterSeat(true);
    expected = (ROW_MULTIPLIER * 1) - CENTER_SEAT_WEIGHT;
    actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);

    // check center row
    seat = new Seat();
    seat.setRowNumber(1);
    seat.setCenterRow(true);
    expected = (ROW_MULTIPLIER * 1) - CENTER_ROW_WEIGHT;
    actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);

    // check center row, center seat, and aisle (a row length of 1)
    seat = new Seat();
    seat.setRowNumber(1);
    seat.setCenterRow(true);
    seat.setCenterSeat(true);
    seat.setAisleSeat(true);
    expected = (ROW_MULTIPLIER * 1) - CENTER_ROW_WEIGHT - CENTER_SEAT_WEIGHT - AISLE_SEAT_WEIGHT;
    actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);
    
    // check a seat that's not in the center row and not on the aisle
    seat = new Seat();
    seat.setRowNumber(1);
    expected = (ROW_MULTIPLIER * 1);
    actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);

    // check a seat that's in a different row other than 1
    seat = new Seat();
    int rowNumber = 2;
    seat.setRowNumber(rowNumber);
    expected = (ROW_MULTIPLIER * rowNumber);
    actual = comparator.scoreSeat(seat);
    assertEquals("score not accurate", expected, actual);
  }

  @Test
  public void testCompare() {
    ComprehensiveSeatComparator comparator = new ComprehensiveSeatComparator();

    // verify that the row is respected
    Seat seat1 = new Seat();
    seat1.setRowNumber(1);
    Seat seat2 = new Seat();
    seat2.setRowNumber(2);
    int actual = comparator.compare(seat1, seat2);
    assertTrue("incorrect result", actual < 0);

    // verify that aisle seats are respected
    seat1 = new Seat();
    seat1.setRowNumber(1);
    seat1.setAisleSeat(true);
    seat2 = new Seat();
    seat2.setRowNumber(1);
    seat2.setAisleSeat(false);
    actual = comparator.compare(seat1, seat2);
    assertTrue("incorrect result", actual < 0);

    // verify that the center seat is respected
    seat1 = new Seat();
    seat1.setRowNumber(1);
    seat1.setCenterSeat(true);
    seat2 = new Seat();
    seat2.setRowNumber(1);
    seat2.setAisleSeat(false);
    actual = comparator.compare(seat1, seat2);
    assertTrue("incorrect result", actual < 0);

    // verify that the center row is respected
    seat1 = new Seat();
    seat1.setRowNumber(1);
    seat1.setCenterRow(true);
    seat2 = new Seat();
    seat2.setRowNumber(1);
    seat2.setAisleSeat(false);
    actual = comparator.compare(seat1, seat2);
    assertTrue("incorrect result", actual < 0);

    /*
     * verify that the best seat in row 2 is not better than an interior
     * seat in row 1
     */
    seat1 = new Seat();
    seat1.setRowNumber(1);
    seat2 = new Seat();
    seat2.setRowNumber(2);
    seat2.setAisleSeat(true);
    seat2.setCenterRow(true);
    seat2.setCenterSeat(true);
    actual = comparator.compare(seat1, seat2);
    assertTrue("incorrect result", actual < 0);
  }
}
