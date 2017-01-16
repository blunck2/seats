package seats.services;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import seats.model.Venue;
import seats.model.VenueFactory;
import seats.model.Seat;
import seats.model.SeatUnavailableException;


/**
 * <p>
 * Unit tests for the StageProximitySeatLocatorService class
 * </p>
 */
public class StageProximitySeatLocatorServiceTest {

  @Test
  public void testLocateSeats() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    StageProximitySeatLocatorService locatorService = new StageProximitySeatLocatorService();
    locatorService.setVenue(venue);

    List<Seat> locatedSeats = null;
    
    // verify we can't locate a negative number of seats
    try {
      locatedSeats = locatorService.locateSeats(-1);
      fail("located negative seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    } catch (InsufficientAvailableSeatsException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // verify we can't locate 0 seats
    try {
      locatedSeats = locatorService.locateSeats(0);
      fail("located 0 seats");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect to happen
    } catch (InsufficientAvailableSeatsException e) {
      fail("should have thrown IllegalArgumentException");
    }

    // verify we can't locate more seats than are available
    try {
      locatedSeats = locatorService.locateSeats(1000);
      fail("located 1000 seats");
    } catch (IllegalArgumentException e) {
      fail("should have thrown InsufficientAvailableSeatsException");
    } catch (InsufficientAvailableSeatsException e) {
      // do nothing;  this is what we expect to happen
    }

    // verify we can locate a seat (nominal use case)
    try {
      locatedSeats = locatorService.locateSeats(1);
      assertNotNull("failed to locate seats", locatedSeats);
      assertEquals("incorrect seat count", 1, locatedSeats.size());

      Seat firstSeat = locatedSeats.get(0);
      assertEquals("incorrect row", 1, firstSeat.getRowNumber());
    } catch (IllegalArgumentException e) {
      fail("failed to locate seats");
    } catch (InsufficientAvailableSeatsException e) {
      fail("failed to locate seats");
    }

    // verify we can locate seats in multiple rows
    try {
      locatedSeats = locatorService.locateSeats(11);
      assertNotNull("failed to locate seats", locatedSeats);
      assertEquals("incorrect seat count", 11, locatedSeats.size());

      Seat seat = null;
      for (int i = 0; i < 10; i++) {
        seat = locatedSeats.get(i);
        assertEquals("incorrect row", 1, seat.getRowNumber());
      }

      seat = locatedSeats.get(10);
      assertEquals("incorrect row", 2, seat.getRowNumber());
    } catch (IllegalArgumentException e) {
      fail("failed to locate seats");
    } catch (InsufficientAvailableSeatsException e) {
      fail("failed to locate seats");
    }
    
    // verify held seats are not considered for location
    try {
      for (int seatNumber = 1; seatNumber <= 10; seatNumber++) {
        venue.holdSeat(1, seatNumber, "customer@gmail.com");
      }
    } catch (SeatUnavailableException e) {
      fail("failed to hold seat");
    }

    try {
      locatedSeats = locatorService.locateSeats(1);
      assertNotNull("failed to locate seats", locatedSeats);
      assertEquals("incorrect seat count", 1, locatedSeats.size());
      Seat seat = locatedSeats.get(0);
      assertNotNull("failed to locate seat", seat);
      assertEquals("incorrect row number", 2, seat.getRowNumber());
    } catch (InsufficientAvailableSeatsException e) {
      fail("failed to locate seats");
    }
    
  }
}
