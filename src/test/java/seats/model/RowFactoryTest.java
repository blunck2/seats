package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


/**
 * <p>
 * Unit tests for the RowFactory class
 * </p>
 */
public class RowFactoryTest {
  @Test
  public void testCreateRow() {
    Row row = null;

    // verify you cannot create a row with negative row numbers
    try {
      row = RowFactory.createRow(-1, 10, 2);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a row with number 0
    try {
      row = RowFactory.createRow(0, 10, 2);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }
    
    // verify you cannot create a row with negative seat count
    try {
      row = RowFactory.createRow(1, -1, 0);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a row with 0 seats
    try {
      row = RowFactory.createRow(1, 0, 0);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a row with negative center row seats
    try {
      row = RowFactory.createRow(1, 10, -1);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a row with 0 center row seats
    try {
      row = RowFactory.createRow(1, 10, 0);
      fail("created row with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }
    
    // verify that you can create a row with 2 center row seats
    int centerRowSeatCount = 4;
    row = RowFactory.createRow(1, 10, centerRowSeatCount);
    assertNotNull("failed to create row", row);
    assertEquals("failed to create row", 10, row.getSeatCount());

    List<Seat> seats = row.getSeats();
    assertNotNull("failed to create seats", seats);

    // verify the aisle seat flags are set on the first seat of the row
    Seat firstSeat = seats.get(0);
    assertNotNull("aisle seat null", firstSeat);
    assertEquals("aisle seat flag not set", true, firstSeat.isAisleSeat());

    // verify the aisle seat flags are set on the last seat of the row
    Seat lastSeat = seats.get(seats.size() - 1);
    assertNotNull("aisle seat null", lastSeat);
    assertEquals("aisle seat flag not set", true, lastSeat.isAisleSeat());

    int lowerMiddleSeat = 2;
    int upperMiddleSeat = seats.size() - 2;
    for (int seatNumber = lowerMiddleSeat; seatNumber <= upperMiddleSeat; seatNumber++) {
      Seat middleSeat = seats.get(seatNumber);
      assertNotNull("seat not created", middleSeat);
      assertEquals("middle seat identified as aisle seat", false, middleSeat.isAisleSeat());
    }

    /*
     * verify the center row seats are identified.  note that we don't care
     * if they are accurately identified because that notion is unit tested
     * elsewhere.  we just want to verify that if we specified a center row
     * seat count of N that N seats in the List<Seat> are flagged as
     * being center row seats
     */
    int totalCenterRowSeats = 0;
    for (Seat seat : seats) {
      assertNotNull("seat not created", seat);
      if (seat.isCenterRow()) {
        totalCenterRowSeats++;
      }
    }
    assertEquals("center row seats not flagged", centerRowSeatCount, totalCenterRowSeats);

    /*
     * verify that 1 (and only 1) seat is identified as the center row seat.
     * same assumption as above - we're not testing for accuracy of the 
     * center seat algorithm - we're just testing that a single seat was
     * flagged as being the center seat
     */
    int totalCenterSeats = 0;
    for (Seat seat : seats) {
      assertNotNull("seat not created", seat);
      if (seat.isCenterSeat()) {
        totalCenterSeats++;
      }
    }
    assertEquals("center seat not flagged", 1, totalCenterSeats);
    
    
  }
}
