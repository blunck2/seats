package seats.services;

import org.joda.time.DateTime;

import seats.model.Venue;
import seats.model.Seat;
import seats.model.SeatHold;
import seats.model.SeatNotHeldException;

import seats.dao.repositories.SeatHoldRepository;

import org.springframework.beans.factory.annotation.Autowired;

import static seats.common.Messages.*;


/**
 * <p>
 * A persistent seat holding service that auto-expires seat holdings after
 * a configurable amount of time.
 * </p>
 *
 * <p>
 * TODO: Add periodic refresh of Venue based on a periodic query to 
 * the mongo for the current set of held seats.  This would allow for 
 * the SeatLocatorService to offer seats that were held (but not reserved)
 * that have then expired.
 * </p>
 */
public class ExpiringDurableSeatHoldingService
  implements DurableSeatHoldingService {

  // the venue in which the seats are held
  private Venue venue;

  // the backing durable store
  @Autowired
  private SeatHoldRepository repository;


  /**
   * Creates an ExpiringDurableSeatHoldingService
   */
  public ExpiringDurableSeatHoldingService() { }
        

  /**
   * Returns the Venue in use
   */
  public Venue getVenue() { return venue; }

  /**
   * Sets the Venue to use
   */
  public void setVenue(Venue venue) { this.venue = venue; }

  
  /**
   * Return the SeatHoldRepository in use
   */
  public SeatHoldRepository getRepository() { return repository; }

  /**
   * Sets the SeatHoldRepository to use
   */
  public void setRepository(SeatHoldRepository repository) {
    this.repository = repository;
  }


  /**
   * @see SeatHoldingService#addSeatHold
   */
  public SeatHold addSeatHold(SeatHold holding) {
    long now = System.currentTimeMillis();
    holding.setId(Long.valueOf(now).intValue());
    return repository.save(holding);
  }
  

  /**
   * @see SeatHoldingService#removeSeatHoldById
   */
  public void removeSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException {

    SeatHold existing = repository.findById(seatHoldId);
    if (existing == null) {
      throw new NoSuchSeatHoldException(SEAT_HOLD_ID_UNKNOWN);
    }
    
    repository.delete(seatHoldId);
  }

  /**
   * @see SeatHoldingService#getSeatHoldById
   */
  public SeatHold getSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException {
    SeatHold seatHold = repository.findById(seatHoldId);
    if (seatHold == null) {
      throw new NoSuchSeatHoldException(SEAT_HOLD_ID_UNKNOWN);
    }

    return seatHold;
  }

  
  /**
   * @see SeatHoldingService#getSeatHoldCount
   */
  public int getSeatHoldCount() {
    long count = repository.count();
    return Long.valueOf(count).intValue();
  }

}
