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
    service.setSeatHoldingService(createMockedSeatHoldingService());
    SeatHold seatHold = service.findAndHoldSeats(numberOfSeatsToHold, "customer@gmail.com");
    openSeatCount = service.numSeatsAvailable();
    expectedSeatCount = (rowCount * seatCount) - numberOfSeatsToHold;
    assertEquals("inaccurate open seat count", expectedSeatCount, openSeatCount);
  }

  /**
   * Creates a mocked SeatHoldingService that returns a SeatHold with
   * a populated id
   */
  private SeatHoldingService createMockedSeatHoldingService() {
    SeatHoldingService mockedSeatHoldingService = mock(SeatHoldingService.class);
    SeatHold mockedSeatHold = mock(SeatHold.class);
    when(mockedSeatHold.getId()).thenReturn(1);
    when(mockedSeatHoldingService.addSeatHold(any(SeatHold.class))).thenReturn(mockedSeatHold);

    return mockedSeatHoldingService;
  }

  @Test
  public void testValidateEmailAddressesMatch() {
    StatefulTransientTicketService service = new StatefulTransientTicketService();
    String customerEmailAddress = "customer@gmail.com";
    boolean valid = false;
  
    // verify we can handle null seats
    valid = service.validateEmailAddressesMatch(customerEmailAddress, null);
    assertFalse("did not handle null", valid);

    // verify we can handle 0 length seats
    List<Seat> seats = new ArrayList<>();
    valid = service.validateEmailAddressesMatch(customerEmailAddress, seats);
    assertFalse("did not handle 0 length seats", valid);

    // verify we can handle 1 seat that's the same
    Seat seat = mock(Seat.class);
    when(seat.getCustomerEmailAddress()).thenReturn(customerEmailAddress);
    seats.clear();
    seats.add(seat);
    valid = service.validateEmailAddressesMatch(customerEmailAddress, seats);
    assertTrue("did not handle 1 seat that matched", valid);

    // verify we can handle multiple seats that are the same
    seats.clear();
    for (int i = 0; i < 5; i++) {
      seats.add(seat);
    }
    valid = service.validateEmailAddressesMatch(customerEmailAddress, seats);
    assertTrue("did not handle multiple seats that matched", valid);

    // verify we can handle differing email addresses
    Seat seat1 = mock(Seat.class);
    when(seat1.getCustomerEmailAddress()).thenReturn("a");
    Seat seat2 = mock(Seat.class);
    when(seat2.getCustomerEmailAddress()).thenReturn("b");
    seats.clear();
    seats.add(seat1);
    seats.add(seat2);
    valid = service.validateEmailAddressesMatch(customerEmailAddress, seats);
    assertFalse("did not handle multiple seats that differed", valid);
  }
  
  
  @Test
  public void testFindAndHoldSeats() {
    StatefulTransientTicketService service = new StatefulTransientTicketService();
    
    service.setSeatHoldingService(createMockedSeatHoldingService());

  }    
}
