package seats.services;

import seats.model.SeatHold;

/**
 * <p>
 * A service that keeps track of seats that are held.
 * </p>
 */
public interface SeatHoldingService {

  /**
   * Adds a SetHolding to the cache of seats that are held
   * @param holding the SeatHold to add
   * @return the SeatHold provided where additional fields may be filled in
   */
  public SeatHold addSeatHolding(SeatHold holding);

  /**
   * Removes the SeatHold with the id provided
   * @throws NoSuchSeatHoldException if a seat hold with the id
   * provided cannot be located
   */
  public void removeSeatHoldingById(int seatHoldId)
    throws NoSuchSeatHoldException;

}