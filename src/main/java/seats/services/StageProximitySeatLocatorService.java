package seats.services;

import seats.model.Venue;
import seats.model.Seat;
import seats.model.RowPrioritizedSeatComparator;

import static seats.common.Messages.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


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
public class StageProximitySeatLocatorService implements SeatLocatorService {
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
   * @throws IllegalArgumentException if the numSeats is 0 or negative
   */
  public synchronized List<Seat> locateSeats(int numSeats)
    throws InsufficientAvailableSeatsException {

    // make sure callers request a positive number of seats
    if (numSeats <= 0) {
      throw new IllegalArgumentException(UNABLE_TO_LOCATE_ZERO_OR_NEGATIVE_SEATS);
    }

    // get all of the open seats in the venue
    List<Seat> openSeats = venue.getOpenSeats();

    // throw an exception if there are not sufficient open seats
    if (numSeats > openSeats.size()) {
      throw new InsufficientAvailableSeatsException(INSUFFICIENT_OPEN_SEATS);
    }

    // sort the open seats
    Comparator comparator = new RowPrioritizedSeatComparator();
    Collections.sort(openSeats, comparator);

    // add the located seats to a new collection
    List<Seat> locatedSeats = new ArrayList<>();
    for (int i = 0; i < numSeats; i++) {
      locatedSeats.add(openSeats.get(i));
    }

    return locatedSeats;
  }

}
