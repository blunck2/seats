package seats.services;

import seats.model.Venue;
import seats.model.SeatHold;
import seats.model.Seat;
import seats.model.SeatHoldRequestStatusEnum;

import java.util.List;


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
    List<Seat> openSeats = venue.getOpenSeats();
    return openSeats.size();
  }

  
  /**
   * @see TransientTicketService#findAndHoldSeats
   */
  public SeatHold findAndHoldSeats(int numSeats, String customerEmailAddress) {
    SeatHold seatHold = new SeatHold();
    seatHold.setCustomerEmailAddress(customerEmailAddress);
    
    // locate the seats, updating the SeatHold if errors occurr
    boolean seatsLocated = false;
    List<Seat> seatsToHold = null;
    try {
      seatsToHold = seatLocator.locateSeats(numSeats);
      seatsLocated = true;
      seatHold.setStatus(SeatHoldRequestStatusEnum.SUCCESS);
    } catch (InsufficientAvailableSeatsException e) {
      seatHold.setStatus(SeatHoldRequestStatusEnum.FAILURE_DUE_TO_INSUFFICIENT_OPEN_SEATS);
      seatHold.setStatusDetails(e.getMessage());
    } catch (IllegalArgumentException e) {
      seatHold.setStatus(SeatHoldRequestStatusEnum.FAILURE_DUE_TO_INVALID_PARAMETERS);
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
