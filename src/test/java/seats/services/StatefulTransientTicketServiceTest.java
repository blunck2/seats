package seats.services;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import seats.model.Venue;
import seats.model.VenueFactory;
import seats.model.Seat;
import seats.model.SeatHold;


/**
 * <p>
 * Unit tests for the StatefulTransientTicketService class
 * </p>
 */
public class StatefulTransientTicketServiceTest {

  @Test
  public void testNumSeatsAvailable() {
    StatefulTransientTicketService service = new StatefulTransientTicketService();

    // verify that when a venue has not been set then 0 open seats exist
    int openSeatCount = service.numSeatsAvailable();
    assertEquals("empty venue has seats", 0, openSeatCount);

    // mock the venue's call to getOpenSeats() so it returns 1 open seat
    Seat seat = new Seat();
    seat.setRowNumber(1);
    seat.setSeatNumber(1);
    List<Seat> openSeats = new ArrayList<>();
    openSeats.add(seat);
    Venue mockedVenue = mock(Venue.class);
    when(mockedVenue.getOpenSeats()).thenReturn(openSeats);
    service.setVenue(mockedVenue);
    openSeatCount = service.numSeatsAvailable();
    assertEquals("inaccurate open seat count", 1, openSeatCount);

    // create a Venue as any other caller would and verify seat count
    int rowCount = 10;
    int seatCount = 10;
    Venue venue = VenueFactory.createVenue(rowCount, seatCount, 4);
    service.setVenue(venue);
    openSeatCount = service.numSeatsAvailable();
    int expectedSeatCount = rowCount * seatCount;
    assertEquals("inaccurate open seat count", expectedSeatCount, openSeatCount);

    // hold some seats and verify the seat count has dropped
    int numberOfSeatsToHold = 10;
    StageProximitySeatLocatorService locatorService = new StageProximitySeatLocatorService();
    locatorService.setVenue(venue);
    service.setSeatLocator(locatorService);
    SeatHold seatHold = service.findAndHoldSeats(numberOfSeatsToHold, "customer@gmail.com");
    openSeatCount = service.numSeatsAvailable();
    expectedSeatCount = (rowCount * seatCount) - numberOfSeatsToHold;
    assertEquals("inaccurate open seat count", expectedSeatCount, openSeatCount);
  }
}
