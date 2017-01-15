package seats.services;

import seats.model.Venue;
import seats.model.Seat;

import java.util.List;
import java.util.ArrayList;


/**
 * <p>
 * An implementation of the SeatLocatorService that uses row number to locate 
 * the best seats in the venue.
 * </p>
 * 
 * <p>
 * This implementation only considers the row number of the seats within
 * the venue and does not locate seats towards the center of the row or 
 * on the aisle unless they are chosen at random.
 * </p>
 */
public class StageProximitySeatLocatorService {
  // the venue to use
  private Venue venue;

  /**
   * Returns the Venue that is used
   */
  public Venue getVenue() { return venue; }

  /**
   * Sets the Venue to use
   */
  public void setVenue(Venue venue) { this.venue = venue; }

  
  /**
   * @see SeatLocatorService#locateSeats
   */
  public synchronized List<Seat> locateSeats(int numSeats)
    throws InsufficientAvailableSeatsException {

    return new ArrayList<>();

  }

}
