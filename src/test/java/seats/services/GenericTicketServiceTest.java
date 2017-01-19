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
import seats.model.RowPrioritizedSeatComparator;

import static seats.model.SeatHoldRequestStatusEnum.*;


/**
 * <p>
 * Unit tests for the GenericTicketService class
 * </p>
 */
public class GenericTicketServiceTest {
  private Venue createVenue() {
    int rowCount = 10;
    int seatCount = 10;
    Venue venue = VenueFactory.createVenue(rowCount, seatCount, 4);

    return venue;
  }
  
  private GenericTicketService createTransientTicketService(Venue venue) {
    GenericTicketService ticketService = new GenericTicketService();
    ticketService.setVenue(venue);

    ComparatorBasedSeatLocatorService seatLocatorService = new ComparatorBasedSeatLocatorService();
    seatLocatorService.setVenue(venue);
    seatLocatorService.setComparator(new RowPrioritizedSeatComparator());
    ticketService.setSeatLocatorService(seatLocatorService);

    SeatHoldingService seatHoldingService = new ExpiringTransientSeatHoldingService();
    seatHoldingService.setVenue(venue);
    ticketService.setSeatHoldingService(seatHoldingService);

    return ticketService;
  }


  private GenericTicketService createDurableTicketService(Venue venue) {
    GenericTicketService ticketService = new GenericTicketService();
    ticketService.setVenue(venue);

    ComparatorBasedSeatLocatorService seatLocatorService = new ComparatorBasedSeatLocatorService();
    seatLocatorService.setVenue(venue);
    seatLocatorService.setComparator(new RowPrioritizedSeatComparator());
    ticketService.setSeatLocatorService(seatLocatorService);

    SeatHoldingService seatHoldingService = new ExpiringDurableSeatHoldingService();
    seatHoldingService.setVenue(venue);
    ticketService.setSeatHoldingService(seatHoldingService);

    return ticketService;
  }
  

  @Test
  public void testTransientNumSeatsAvailable() {
    Venue venue = createVenue();
    GenericTicketService service = createTransientTicketService(venue);

    // verify that when a venue has not been set then 0 open seats exist
    int openSeatCount = service.numSeatsAvailable();
    assertEquals("empty venue has seats", venue.getSeatCount(), openSeatCount);

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

    service.setVenue(mockedVenue);
    openSeatCount = service.numSeatsAvailable();
    int expectedSeatCount = 1;
    assertEquals("inaccurate open seat count", expectedSeatCount, openSeatCount);

    // hold some seats and verify the seat count has dropped
    int rowCount = 1;
    int seatCount = 10;
    venue = VenueFactory.createVenue(1, 10, 4);
    int numberOfSeatsToHold = 10;
    ComparatorBasedSeatLocatorService locatorService = new ComparatorBasedSeatLocatorService();
    locatorService.setVenue(venue);
    locatorService.setComparator(new RowPrioritizedSeatComparator());
    service.setSeatLocatorService(locatorService);
    service.setSeatHoldingService(createMockedSeatHoldingService());
    service.setVenue(venue);
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
  public void testTransientValidateEmailAddressesMatch() {
    Venue venue = createVenue();
    GenericTicketService service = createTransientTicketService(venue);

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
  public void testTransientFindAndHoldSeats() {
    Venue venue = createVenue();
    GenericTicketService service = createTransientTicketService(venue);
    String customerEmailAddress = "customer@gmail.com";

    SeatHold seatHold = null;

    // verify you can't negative seats
    seatHold = service.findAndHoldSeats(-1, customerEmailAddress);
    assertEquals("invalid state", FAILURE_DUE_TO_INVALID_PARAMETERS, seatHold.getStatus());

    // verify you can't locate 0 seats
    seatHold = service.findAndHoldSeats(0, customerEmailAddress);
    assertEquals("invalid state", FAILURE_DUE_TO_INVALID_PARAMETERS, seatHold.getStatus());

    // verify you can't use a null email address
    seatHold = service.findAndHoldSeats(1, null);
    assertEquals("invalid state", FAILURE_DUE_TO_INVALID_PARAMETERS, seatHold.getStatus());

    // verify you can't use an empty address
    seatHold = service.findAndHoldSeats(1, "");
    assertEquals("invalid state", FAILURE_DUE_TO_INVALID_PARAMETERS, seatHold.getStatus());

    // verify you can't use a blank address
    seatHold = service.findAndHoldSeats(1, "  ");
    assertEquals("invalid state", FAILURE_DUE_TO_INVALID_PARAMETERS, seatHold.getStatus());

    // verify you can't locate more seats than exist in the venue
    int numSeatsAvailable = service.numSeatsAvailable();
    seatHold = service.findAndHoldSeats(numSeatsAvailable + 1, customerEmailAddress);
    assertEquals("invalid seat hold state", FAILURE_DUE_TO_INSUFFICIENT_OPEN_SEATS, seatHold.getStatus());

    // verify you can locate a seat
    seatHold = service.findAndHoldSeats(1, customerEmailAddress);
    assertEquals("invalid seat hold state", SUCCESS, seatHold.getStatus());
    int newNumSeatsAvailable = service.numSeatsAvailable();
    assertEquals("invalid number of seats available", (numSeatsAvailable - 1), newNumSeatsAvailable);
  }

  @Test
  public void testTransientReserveSeats() {
    Venue venue = createVenue();
    GenericTicketService service = createTransientTicketService(venue);
    String customerEmailAddress = "customer@gmail.com";

    SeatHold seatHold = null;
    String confirmationCode = null;

    // verify you cannot reserve using a negative seat hold id
    try {
      confirmationCode = service.reserveSeats(-1, customerEmailAddress);
      fail("reserved negative id");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect
    }

    // verify you cannot reserve using a seat hold id that does not exist
    try {
      confirmationCode = service.reserveSeats(100, customerEmailAddress);
      fail("reserved non-existing hold");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect
    }
    
    // verify you cannot reserve using a different email address
    seatHold = service.findAndHoldSeats(1, customerEmailAddress);
    try {
      confirmationCode = service.reserveSeats(seatHold.getId(), "someoneElse");
    } catch (IllegalArgumentException e) {
      // do nothing;  this is what we expect
    }

    // verify you can reserve with the same email address
    confirmationCode = service.reserveSeats(seatHold.getId(), customerEmailAddress);
    assertNotNull("confirmation code null", confirmationCode);

    // verify the hold was removed
    assertEquals("seat hold not removed", 0, service.getSeatHoldingService().getSeatHoldCount());
  }
}
