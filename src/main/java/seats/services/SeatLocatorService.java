package seats.services;

import java.util.List;
import seats.model.Seat;


/**
 * <p>
 * Facilitates the location of seats based on a seat selection criteria.
 * </p>
 *
 * <p>
 * Because there are numerous ways of scoring seats this interface exists
 * as an abstraction that all scoring algorithms must abide by.
 * </p>
 */
public interface SeatLocatorService {

  /**
   * Locates a fixed number of seats and returns them as a List<Seat>.
   * @return the best located seats
   * @param numSeats the number of seats to reserve
   * @throws InsufficientAvailableSeatsException if more seats are
   * requested than are currently available
   */
  List<Seat> locateSeats(int numSeats) throws InsufficientAvailableSeatsException;

    
}

