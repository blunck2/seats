package seats.services;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import seats.model.Venue;
import seats.model.Seat;
import seats.model.SeatHold;
import seats.model.Row;

import static seats.model.SeatHoldRequestStatusEnum.*;


/**
 * <p>
 * Unit tests for the ExpiringTransientSeatHoldingService class
 * </p>
 */
public class ExpiringTransientSeatHoldingServiceTest {

  /**
   * Creates a Venue with a single open seat located at row 1, seat 1
   */
  private Venue createTestVenue() {
    // create a seat
    Seat seat = new Seat();
    seat.setRowNumber(1);
    seat.setSeatNumber(1);
    List<Seat> seatList = new ArrayList<>();
    seatList.add(seat);

    // make a row and add our mocked seat to it
    Row row = new Row();
    row.setSeats(seatList);

    // make a venue and add our row to it
    Venue venue = new Venue();
    venue.addRow(row);

    return venue;
  }

  
  /**
   * Creates a SeatHold for row 1, seat 1
   */
  private SeatHold createSeatHold(List<Seat> seatList) {
    // create a seat holding
    SeatHold seatHold = new SeatHold();
    seatHold.setNumberOfSeatsHeld(1);
    seatHold.setNumberOfSeatsRequested(1);
    seatHold.setCustomerEmailAddress("customer@gmail.com");
    seatHold.setSeatsHeld(seatList);
    seatHold.setStatus(SUCCESS);

    return seatHold;
  }

  
  @Test
  public void testExpireSeatHoldings() {
    Venue venue = createTestVenue();

    // create the ExpiringTransientSeatHoldingService
    ExpiringTransientSeatHoldingService service = new ExpiringTransientSeatHoldingService();
    service.setVenue(venue);

    // set a very short lookback period
    service.setExpirationTimeInMilliSeconds(1);
    
    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);

    // manually expire the holdings
    service.expireSeatHoldings();

    // get the number of seat hold objects that are being managed
    instanceCount = service.getSeatHoldCount();
    assertEquals("failed to expire", 0, instanceCount);
  }


  @Test
  public void testAddSeatHold() {
    Venue venue = createTestVenue();

    // create the ExpiringTransientSeatHoldingService
    ExpiringTransientSeatHoldingService service = new ExpiringTransientSeatHoldingService();
    service.setVenue(venue);

    /*
     * set to expire holdings that are older than 1 milliseconds and check 
     * every 60 seconds
     */
    int cycleTimeInMilliSeconds = 60 * 1000;
    service.setExpirationTimeInMilliSeconds(1);
    service.setExpirationCheckCycleTimeInMilliSeconds(cycleTimeInMilliSeconds);

    // start the service
    try {
      service.init();
    } catch (Exception e) {
      fail("failed to start service");
    }

    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);
  }


  @Test
  public void testRemoveSeatHold() {
    Venue venue = createTestVenue();

    // create the ExpiringTransientSeatHoldingService
    ExpiringTransientSeatHoldingService service = new ExpiringTransientSeatHoldingService();
    service.setVenue(venue);

    /*
     * set to expire holdings that are older than 1 milliseconds and check 
     * every 60 seconds
     */
    int expirationTimeInMilliseconds = 60 * 1000;
    int cycleTimeInMilliSeconds = 60 * 1000;
    service.setExpirationTimeInMilliSeconds(expirationTimeInMilliseconds);
    service.setExpirationCheckCycleTimeInMilliSeconds(cycleTimeInMilliSeconds);

    // start the service
    try {
      service.init();
    } catch (Exception e) {
      fail("failed to start service");
    }

    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);

    // remove the seat hold
    try {
      service.removeSeatHoldById(seatHold.getId());
    } catch (NoSuchSeatHoldException e) {
      fail("did not locate seat hold");
    }
    instanceCount = service.getSeatHoldCount();
    assertEquals("failed to remove", 0, instanceCount);

    // now try to remove a random seat hold that does not exist
    try {
      service.removeSeatHoldById(1000);
      fail("removed non-existent seat hold");
    } catch (NoSuchSeatHoldException e) {
      // do nothing; this is what we expect to happen
    }
  }


  @Test
  public void testGetSeatHoldById() {
    Venue venue = createTestVenue();
    
    // create the ExpiringTransientSeatHoldingService
    ExpiringTransientSeatHoldingService service = new ExpiringTransientSeatHoldingService();
    service.setVenue(venue);

    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);

    // look for the seat hold
    try {
      seatHold = service.getSeatHoldById(seatHold.getId());
      assertNotNull("failed to locate seat hold", seatHold);
    } catch (NoSuchSeatHoldException e) {
      fail("failed to locate seat hold");
    }
  }
  
  
  @Test
  public void testService() {
    Venue venue = createTestVenue();
    
    // create the ExpiringTransientSeatHoldingService
    ExpiringTransientSeatHoldingService service = new ExpiringTransientSeatHoldingService();
    service.setVenue(venue);

    /*
     * set to expire holdings that are older than 1 milliseconds and check 
     * every 5 seconds
     */
    int cycleTimeInMilliSeconds = 100;
    service.setExpirationTimeInMilliSeconds(1);
    service.setExpirationCheckCycleTimeInMilliSeconds(cycleTimeInMilliSeconds);

    // start the service
    try {
      service.init();
    } catch (Exception e) {
      fail("failed to start service");
    }

    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);

    // give the expiration a chance to occur
    try {
      Thread.sleep(cycleTimeInMilliSeconds * 2);
    } catch (InterruptedException e) {
      fail("failed to sleep");
    }

    // get the number of seat hold objects that are being managed
    instanceCount = service.getSeatHoldCount();
    assertEquals("failed to expire", 0, instanceCount);
  }
}
