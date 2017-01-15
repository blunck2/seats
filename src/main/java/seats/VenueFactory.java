package seats.model;

import static seats.common.Messages.*;

import java.util.List;
import java.util.ArrayList;


/**
 * <p>
 * Convenience class for producing Venues
 * </p>
 */
public class VenueFactory {

  /**
   * Private constructor to enforce non-instantiability
   */
  private VenueFactory() { }

  
  /**
   * Creates a Venue that has a fixed number of rows, seats per row, and 
   * a center row size.  The returned Venue contains Seat instances that
   * have not been held or reserved.
   * @param rowCount the number of rows in the venue
   * @param rowSeatCount the number of seats in each row
   * @param centerRowSize the number of seats in each row that are
   * considered to be "center row"
   * @throws IllegalArgumentException if any of the parameters passed
   * are <= 0 or if the centerRowSize exceeds the rowSeatCount
   */
  public static Venue createVenue(int rowCount,
                                  int rowSeatCount,
                                  int centerRowSize) {
    if ((rowCount < 1) || (rowSeatCount < 1)) {
      throw new IllegalArgumentException(EMPTY_VENUE_NOT_PERMITTED);
    }

    Venue venue = new Venue();
    venue.setRowCount(rowCount);
    venue.setRowSeatCount(rowSeatCount);
    venue.setCenterRowSize(centerRowSize);

    for (int rowNumber = 1; rowNumber <= rowCount; rowNumber++) {
      List<Seat> seats = SeatFactory.createSeatsForRow(rowNumber, rowSeatCount);
      venue.addSeatsForRow(rowNumber, seats);
    }
    
    return venue;
  }
}
