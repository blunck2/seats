package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * <p>
 * Unit tests for the Row class
 * </p>
 */
public class RowTest {

  @Test
  public void testValidateSeatNumber() {
    Row row = RowFactory.createRow(1, 10, 4);

    // verify negative seat numbers do not work
    try {
      row.validateSeatNumber(-1);
      fail("failed to recognize invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify that seat number 0 does not work
    try {
      row.validateSeatNumber(0);
      fail("failed to recognize invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify that a high seat number does not work
    try {
      row.validateSeatNumber(100);
      fail("failed to recognize invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify that a valid number works
    try {
      row.validateSeatNumber(5);
    } catch (IllegalArgumentException e) {
      fail("failed to recognize valid seat");
    }
    
  }
}
