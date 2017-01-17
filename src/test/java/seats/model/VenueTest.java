package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


/**
 * <p>
 * Unit tests for the Venue class
 * </p>
 */
public class VenueTest {

  @Test
  public void testValidateRowNumber() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);

    // verify negative row number does not work
    try {
      venue.validateRowNumber(-1);
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify 0 row number does not work
    try {
      venue.validateRowNumber(0);
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify too high row number does not work
    try {
      venue.validateRowNumber(100);
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    }

    try {
      venue.validateRowNumber(5);
    } catch (IllegalArgumentException e) {
      fail("failed to validate valid row number");
    }
  }

  @Test
  public void testValidateEmailAddress() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);

    // verify a null email address is invalid
    try {
      venue.validateEmailAddress(null);
      fail("did not recognize invalid email address");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify an empty email address is invalid
    try {
      venue.validateEmailAddress("");
      fail("did not recognize invalid email address");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a blank email address is invalid
    try {
      venue.validateEmailAddress("   ");
      fail("did not recognize invalid email address");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a valid email address works
    try {
      venue.validateEmailAddress("customer@gmail.com");
    } catch (IllegalArgumentException e) {
      fail("recognized valid email address as invalid");
    }
    
  }

  @Test
  public void testFindSeat() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    Seat seat = null;

    // verify a negative row
    try {
      seat = venue.findSeat(-1, 1);
      fail("found invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a 0 row
    try {
      seat = venue.findSeat(0, 1);
      fail("found invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a negative seat
    try {
      seat = venue.findSeat(1, -1);
      fail("found invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a zero seat
    try {
      seat = venue.findSeat(1, 0);
      fail("found invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a seat that's too high
    try {
      seat = venue.findSeat(1, 100);
      fail("found invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify a valid seat
    try {
      seat = venue.findSeat(1, 5);
      assertNotNull("seat is null", seat);
    } catch (IllegalArgumentException e) {
      fail("found invalid seat");
    }
    
  }


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

    // attempt to hold a valid seat
    try {
      heldSeat = venue.holdSeat(1, 1, customerEmailAddress);
    } catch (SeatUnavailableException e) {
      fail("seat is available");
    }
    assertNotNull("held seat is null", heldSeat);
    assertEquals("row not correct", 1, heldSeat.getRowNumber());
    assertEquals("seat not correct", 1, heldSeat.getSeatNumber());
    assertFalse("seat is open", heldSeat.isOpen());
    assertEquals("customer email address not set", customerEmailAddress, heldSeat.getCustomerEmailAddress());
  }

  @Test
  public void testReserve() {
    Seat reservedSeat = null;
    String customerEmailAddress = "customer@gmail.com";

    Venue venue = VenueFactory.createVenue(10, 10, 4);

    // attempt to reserve negative row
    try {
      reservedSeat = venue.reserveSeat(-1, 5, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve 0 row
    try {
      reservedSeat = venue.reserveSeat(0, 5, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve negative seat
    try {
      reservedSeat = venue.reserveSeat(1, -1, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve seat 0
    try {
      reservedSeat = venue.reserveSeat(1, 0, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve with a null email address
    try {
      reservedSeat = venue.reserveSeat(1, 1, null);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve with a empty email address
    try {
      reservedSeat = venue.reserveSeat(1, 1, "");
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve with a blank email address
    try {
      reservedSeat = venue.reserveSeat(1, 1, "   ");
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve with an invalid seat
    try {
      reservedSeat = venue.reserveSeat(1, 100, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve with an invalid row
    try {
      reservedSeat = venue.reserveSeat(100, 1, customerEmailAddress);
      fail("reserved seat that does not exist");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // attempt to reserve a seat that is not held
    try {
      reservedSeat = venue.reserveSeat(1, 1, customerEmailAddress);
      fail("reserved seat that was not held");
    } catch (IllegalArgumentException e) {
      fail("should have thrown SeatUnavailableException");
    } catch (SeatUnavailableException e) {
      // do nothing; this is what we expect to happen
    }

    // verify we can reserve a seat that is held
    try {
      Seat heldSeat = venue.holdSeat(1, 1, customerEmailAddress);
      reservedSeat = venue.reserveSeat(1, 1, customerEmailAddress);
      assertNotNull("reserved seat is null", reservedSeat);
      assertTrue("seat not reserved", reservedSeat.isReserved());
      assertTrue("seat not reserved", reservedSeat.isHeld());
      assertEquals("email address does not match", customerEmailAddress, reservedSeat.getCustomerEmailAddress());
    } catch (IllegalArgumentException e) {
      fail("failed to reserve seat");
    } catch (SeatUnavailableException e) {
      fail("failed to reserve seat");
    }

    // verify we can't reserve the seat after it's already reserved
    try {
      reservedSeat = venue.reserveSeat(1, 1, customerEmailAddress);
      fail("reserved seat twice");
    } catch (SeatUnavailableException e) {
      // do nothing; this is what we expect to happen
    } catch (IllegalArgumentException e) {
      fail("should have thrown SeatUnavailableException");
    }
  }

  @Test
  public void testGetOpenSeats() {
    int rowCount = 10;
    int seatCount = 10;
    Venue venue = VenueFactory.createVenue(rowCount, seatCount, 4);

    List<Seat> openSeats = venue.getOpenSeats();
    assertNotNull("no open seats", openSeats);
    int expectedSeatCount = rowCount * seatCount;
    assertEquals("invalid number of open seats", expectedSeatCount, openSeats.size());

    try {
      Seat heldSeat = venue.holdSeat(1, 1, "customer@gmail.com");
    } catch (SeatUnavailableException e) {
      fail("failed to hold seat");
    }
    openSeats = venue.getOpenSeats();
    assertEquals("invalid number of open seats", (expectedSeatCount - 1), openSeats.size());
  }

  @Test
  public void testUnholdSeat() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    Seat seat = null;

    // verify we cannot unhold a negative row
    try {
      venue.unholdSeat(-1, 1);
      fail("unheld invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    } catch (SeatNotHeldException e) {
      fail("expected IllegalArgumentException");
    }

    // verify we cannot unhold a 0 row
    try {
      venue.unholdSeat(0, 1);
      fail("unheld invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    } catch (SeatNotHeldException e) {
      fail("expected IllegalArgumentException");
    }

    // verify we cannot unhold a negative seat
    try {
      venue.unholdSeat(1, -1);
      fail("unheld invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    } catch (SeatNotHeldException e) {
      fail("expected IllegalArgumentException");
    }

    // verify we cannot unhold a 0 seat
    try {
      venue.unholdSeat(1, 0);
      fail("unheld invalid seat");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    } catch (SeatNotHeldException e) {
      fail("expected IllegalArgumentException");
    }

    // verify we cannot unhold an open seat
    try {
      venue.unholdSeat(1, 1);
      fail("unheld open seat");
    } catch (IllegalArgumentException e) {
      fail("expected SeatNotHeldException");
    } catch (SeatNotHeldException e) {
      // do nothing; this is what we expect to have happen
    }

    // verify we can unhold a held seat
    try {
      venue.holdSeat(1, 1, "customer@gmail.com");
      venue.unholdSeat(1, 1);
    } catch (IllegalArgumentException e) {
      fail("failed to unhold seat");
    } catch (SeatNotHeldException e) {
      fail("failed to unhold seat");
    } catch (SeatUnavailableException e) {
      fail("failed to unhold seat");
    }

    // verify we cannot unhold a reserved seat
    try {
      venue.holdSeat(1, 1, "customer@gmail.com");
      venue.reserveSeat(1, 1, "customer@gmail.com");
      venue.unholdSeat(1, 1);
      fail("unheld reserved seat");
    } catch (IllegalArgumentException e) {
      fail("failed to unhold seat");
    } catch (SeatNotHeldException e) {
      // do nothing; this is what we expect to happen
    } catch (SeatUnavailableException e) {
      fail("failed to unhold seat");
    }
    
  }
}
