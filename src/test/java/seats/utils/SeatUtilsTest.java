package seats.utils;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * <p>
 * Unit tests for the SeatUtils class
 * </p>
 */
public class SeatUtilsTest {
  @Test
  public void testCalculateCenterSeat() {
    // verify that negative seat numbers cannot be used
    int result = 0;
    try {
      result = SeatUtils.calculateCenterSeat(-1);
      fail("calculated center of row that has negative seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify that a row with 0 seats cannot be used
    try {
      result = SeatUtils.calculateCenterSeat(0);
      fail("calculated center of row that has 0 seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify that a row with 1 seat returns the 1 seat as the center
    result = SeatUtils.calculateCenterSeat(1);
    assertEquals("failed to identify single seat as the center", 1, result);

    // verify that in a row with 2 seats the first seat is the center
    result = SeatUtils.calculateCenterSeat(2);
    assertEquals("failed to identify center seat", 1, result);

    // verify that in a row with 3 seats that the second seat is the center
    result = SeatUtils.calculateCenterSeat(3);
    assertEquals("failed to identify center seat", 2, result);

    // verify that in a row with 5 seats the third seat is the center
    result = SeatUtils.calculateCenterSeat(5);
    assertEquals("failed to identify center seat", 3, result);

    // verify that in a row with 6 seats the third seat is the center
    result = SeatUtils.calculateCenterSeat(6);
    assertEquals("failed to identify center seat", 3, result);

    // verify that in a row with 9 seats the fifth seat is the center
    result = SeatUtils.calculateCenterSeat(9);
    assertEquals("failed to identify center seat", 5, result);

    // verify that in a row with 10 seats the fifth seat is the center
    result = SeatUtils.calculateCenterSeat(10);
    assertEquals("failed to identify center seat", 5, result);
  }

  @Test
  public void testCalculateMinimumCenterSeat() {
    // verify that negative seat numbers cannot be used
    int result = 0;
    try {
      result = SeatUtils.calculateMinimumCenterSeat(-1, 2);
      fail("calculated minimuum center of row that has negative seat count");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify that a row with 0 seats cannot be used
    try {
      result = SeatUtils.calculateMinimumCenterSeat(0, 2);
      fail("calculated minimum center of row that has 0 seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }
    
    // verify that a row with 0 seats and 0 centerRowSeatCount cannot be used
    try {
      result = SeatUtils.calculateMinimumCenterSeat(0, 0);
      fail("calculated minimum center of row that has 0 seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify that a 0 size centerRowSeatCount cannot be used
    try {
      result = SeatUtils.calculateMinimumCenterSeat(2, 0);
      fail("calculated minimum center of row that has 0 center row seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify we calculate properly for 1 seat row
    result = SeatUtils.calculateMinimumCenterSeat(1, 1);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 2 seat row
    result = SeatUtils.calculateMinimumCenterSeat(2, 1);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 3 seat row with 1 center row seat
    result = SeatUtils.calculateMinimumCenterSeat(3, 1);
    assertEquals("failed to identify minimum center seat", 2, result);

    // verify we calculate properly for 3 seat row with 2 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(3, 2);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 3 seat row with 3 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(3, 3);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 4 seat row with 1 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(4, 1);
    assertEquals("failed to identify minimum center seat", 2, result);

    // verify we calculate properly for 4 seat row with 2 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(4, 2);
    assertEquals("failed to identify minimum center seat", 2, result);

    // verify we calculate properly for 4 seat row with 3 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(4, 3);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 4 seat row with 3 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(4, 4);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 9 seat row with 1 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 1);
    assertEquals("failed to identify minimum center seat", 5, result);

    // verify we calculate properly for 9 seat row with 1 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 1);
    assertEquals("failed to identify minimum center seat", 5, result);

    // verify we calculate properly for 9 seat row with 2 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 2);
    assertEquals("failed to identify minimum center seat", 4, result);

    // verify we calculate properly for 9 seat row with 3 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 3);
    assertEquals("failed to identify minimum center seat", 4, result);

    // verify we calculate properly for 9 seat row with 4 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 4);
    assertEquals("failed to identify minimum center seat", 3, result);

    // verify we calculate properly for 9 seat row with 5 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 5);
    assertEquals("failed to identify minimum center seat", 3, result);

    // verify we calculate properly for 9 seat row with 6 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 6);
    assertEquals("failed to identify minimum center seat", 2, result);

    // verify we calculate properly for 9 seat row with 7 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 7);
    assertEquals("failed to identify minimum center seat", 2, result);

    // verify we calculate properly for 9 seat row with 8 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 8);
    assertEquals("failed to identify minimum center seat", 1, result);

    // verify we calculate properly for 9 seat row with 9 center row seats
    result = SeatUtils.calculateMinimumCenterSeat(9, 9);
    assertEquals("failed to identify minimum center seat", 1, result);
    
  }
}
