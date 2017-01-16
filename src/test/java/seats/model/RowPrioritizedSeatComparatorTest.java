package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * <p>
 * Unit tests for the RowPrioritizedSeatComparatorTest
 * </p>
 */
public class RowPrioritizedSeatComparatorTest {

  @Test
  public void testCompare() {
    RowPrioritizedSeatComparator comparator = new RowPrioritizedSeatComparator();
    
    Seat seat1 = new Seat();
    seat1.setRowNumber(1);

    Seat seat2 = new Seat();
    seat2.setRowNumber(2);

    int result = comparator.compare(seat1, seat2);
    assertTrue("comparison failed", result < 0);
  }
}
