package seats.services;

import seats.model.SeatHold;
import seats.model.Venue;


/**
 * <p>
 * A service that keeps track of seats that are held.
 * </p>
 */
public interface SeatHoldingService {

  /**
   * Sets the underlying venue upon which seats should be held
   * @param venue the venue where seats should be held
   */
  public void setVenue(Venue venue);


  /**
   * Returns the underlying venue upon which seats are held
   */
  public Venue getVenue();
  

  /**
   * Adds a SetHolding to the cache of seats that are held
   * @param holding the SeatHold to add
   * @return the SeatHold provided where additional fields may be filled in
   */
  public SeatHold addSeatHold(SeatHold holding);

  
  /**
   * Removes the SeatHold with the id provided
   * @throws NoSuchSeatHoldException if a seat hold with the id
   * provided cannot be located
   */
  public void removeSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException;

  
  /**
   * Returns the SeatHold with the id provided
   * @throws NoSuchSeatHoldException if a seat hold with the id
   * provided cannot be located
   */
  public SeatHold getSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException;


  /**
   * Returns the number of seat hold instances
   */
  public int getSeatHoldCount();

}
