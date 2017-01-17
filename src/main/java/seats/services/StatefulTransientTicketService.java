package seats.services;

import seats.model.Venue;
import seats.model.SeatHold;
import seats.model.Seat;
import static seats.model.SeatHoldRequestStatusEnum.*;

import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>
 * An implementation of the TransientTicketService that maintains the
 * state of SeatHold instances as seats are held, released, and reserved.
 * </p>
 */
public class StatefulTransientTicketService implements TransientTicketService {
  // the underlying venue
  private Venue venue;

  // locates the best seats
  private SeatLocatorService seatLocator;

  // a thread-safe numeric id generator for SeatHold ids
  private AtomicInteger seatHoldIdGenerator;

  
  /**
   * Creates a StatefuleTransientTicketService
   */
  public StatefulTransientTicketService() { }

  /**
   * Returns the Venue 
   */
  public Venue getVenue() { return venue; }

  /**
   * Sets the Venue
   */
  public void setVenue(Venue venue) { this.venue = venue; }


  /**
   * Returns the seat locator service
   */
  public SeatLocatorService getSeatLocator() { return seatLocator; }

  /**
   * Sets the seat locator service
   */
  public void setSeatLocator(SeatLocatorService seatLocator) {
    this.seatLocator = seatLocator;
  }
  

  /**
   * @see TransientTicketService#numSeatsAvailable
   */
  public int numSeatsAvailable() {
    // if no venue is set then 0 seats are available
    if (venue == null) {
      return 0;
    }
    
    List<Seat> openSeats = venue.getOpenSeats();
    return openSeats.size();
  }

  
  /**
   * @see TransientTicketService#findAndHoldSeats
   */
  public SeatHold findAndHoldSeats(int numSeats,
                                   String customerEmailAddress) {
    // create a new thread-safe integer id
    int seatHoldId = seatHoldIdGenerator.incrementAndGet();

    // create a SeatHold 
    SeatHold seatHold = new SeatHold();
    seatHold.setCustomerEmailAddress(customerEmailAddress);
    seatHold.setId(seatHoldId);
    
    // locate the seats, updating the SeatHold if errors occurr
    boolean seatsLocated = false;
    List<Seat> seatsToHold = null;
    try {
      seatsToHold = seatLocator.locateSeats(numSeats);
      seatsLocated = true;
      seatHold.setStatus(SUCCESS);
    } catch (InsufficientAvailableSeatsException e) {
      seatHold.setStatus(FAILURE_DUE_TO_INSUFFICIENT_OPEN_SEATS);
      seatHold.setStatusDetails(e.getMessage());
    } catch (IllegalArgumentException e) {
      seatHold.setStatus(FAILURE_DUE_TO_INVALID_PARAMETERS);
      seatHold.setStatusDetails(e.getMessage());
    }

    // if seats were not located then return
    if (! seatsLocated) {
      return seatHold;
    }

    return seatHold;
  }

  
  /**
   * @see TransientTicketService#reserveSeats
   */
  public String reserveSeats(int seatHoldId, String customerEmail) {
    return "ok";
  }    
  

}
