package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * <p>
 * Unit tests for the Venue class
 * </p>
 */
public class VenueTest {

  @Test
  public void testHold() {
    Seat heldSeat = null;
    String customerEmailAddress = "customer@gmail.com";

    Venue venue = VenueFactory.createVenue(10, 10, 4);

    // attempt to hold negative row
    try {
      heldSeat = venue.holdSeat(-1, 5, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold 0 row
    try {
      heldSeat = venue.holdSeat(0, 5, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold negative seat
    try {
      heldSeat = venue.holdSeat(1, -1, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold seat 0
    try {
      heldSeat = venue.holdSeat(1, 0, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold with a null email address
    try {
      heldSeat = venue.holdSeat(1, 1, null);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold with a empty email address
    try {
      heldSeat = venue.holdSeat(1, 1, "");
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold with a blank email address
    try {
      heldSeat = venue.holdSeat(1, 1, "   ");
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold with an invalid seat
    try {
      heldSeat = venue.holdSeat(1, 100, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to hold with an invalid row
    try {
      heldSeat = venue.holdSeat(100, 1, customerEmailAddress);
      fail("held seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }
    
  }
}
