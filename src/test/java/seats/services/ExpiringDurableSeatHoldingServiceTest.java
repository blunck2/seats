package seats.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

import seats.model.SeatHold;
import seats.model.Venue;
import seats.model.VenueFactory;
import seats.model.Seat;
import seats.dao.repositories.SeatHoldRepository;

import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.beans.factory.annotation.Autowired;

import static seats.model.SeatHoldRequestStatusEnum.*;


/**
 * <p>
 * Unit tests for ExpiringDurableSeatHoldingService class
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class ExpiringDurableSeatHoldingServiceTest {
  @Autowired
  SeatHoldRepository repository;

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
  public void testRepositoryAuthowired() {
    assertNotNull("repository null", repository);
  }

  @Test
  public void testAddSeatHold() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    ExpiringDurableSeatHoldingService service = new ExpiringDurableSeatHoldingService();
    service.setVenue(venue);
    service.setRepository(repository);


    // create the seat hold
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());

    // add the seat hold to the service
    seatHold = service.addSeatHold(seatHold);

    // verify the id is not null
    assertNotNull("id null", seatHold.getId());

    // verify the getSeatHoldCount() method works
    int instanceCount = service.getSeatHoldCount();
    assertEquals("failed to add", 1, instanceCount);

    // clean up
    repository.delete(seatHold.getId());
  }


  @Test
  public void testRemoveSeatHold() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    ExpiringDurableSeatHoldingService service = new ExpiringDurableSeatHoldingService();
    service.setVenue(venue);
    service.setRepository(repository);
    repository.deleteAll();
    
    // verify you cannot remove a seat hold that does not exist
    try {
      service.removeSeatHoldById(1000);
      fail("removed non-existent seat hold");
    } catch (NoSuchSeatHoldException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you can remove a seat hold that does exist
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());
    seatHold = service.addSeatHold(seatHold);
    int id = seatHold.getId();
    try {
      service.removeSeatHoldById(id);
    } catch (NoSuchSeatHoldException e) {
      fail("unable to remove existing seat hold");
    }
  }

  @Test
  public void testGetSeatHoldById() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    ExpiringDurableSeatHoldingService service = new ExpiringDurableSeatHoldingService();
    service.setVenue(venue);
    service.setRepository(repository);
    repository.deleteAll();
    
    // verify you cannot remove a seat hold that does not exist
    try {
      service.getSeatHoldById(1000);
      fail("located non-existent seat hold");
    } catch (NoSuchSeatHoldException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you can get a seat hold that does exist
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());
    seatHold = service.addSeatHold(seatHold);
    int id = seatHold.getId();
    try {
      seatHold = service.getSeatHoldById(id);
      assertNotNull("seat hold not found", seatHold);
    } catch (NoSuchSeatHoldException e) {
      fail("failed to locate seat hold");
    }

    // clean up
    repository.delete(id);
  }

  @Test
  public void testGetSeatHoldCount() {
    Venue venue = VenueFactory.createVenue(10, 10, 4);
    ExpiringDurableSeatHoldingService service = new ExpiringDurableSeatHoldingService();
    service.setVenue(venue);
    service.setRepository(repository);
    repository.deleteAll();
    
    // verify no seat holds exist initially
    int actual = service.getSeatHoldCount();
    assertEquals("incorrect count", 0, actual);

    // verify we can create 1 seat hold and get the count
    actual = service.getSeatHoldCount();
    assertEquals("incorrect count", 0, actual);
    SeatHold seatHold = createSeatHold(venue.getRow(1).getSeats());
    seatHold = service.addSeatHold(seatHold);
    actual = service.getSeatHoldCount();
    assertEquals("incorrect count", 1, actual);
    
  }
}
