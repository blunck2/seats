package seats.services;

import org.joda.time.DateTime;

import seats.model.Venue;
import seats.model.Seat;
import seats.model.SeatHold;
import seats.model.SeatNotHeldException;

import static seats.common.Messages.*;


/**
 * <p>
 * A persistent seat holding service that auto-expires seat holdings after
 * a configurable amount of time.
 * </p>
 */
public class ExpiringDurableSeatHoldingService
  implements DurableSeatHoldingService {


  /**
   * @see SeatHoldingService#addSeatHold
   */
  public SeatHold addSeatHold(SeatHold holding) {
    return holding;
  }
  

  /**
   * @see SeatHoldingService#removeSeatHoldById
   */
  public void removeSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException {
    
  }

  /**
   * @see SeatHoldingService#getSeatHoldById
   */
  public SeatHold getSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException {
    SeatHold seatHold = new SeatHold();
    return seatHold;
  }

  
  /**
   * @see SeatHoldingService#getSeatHoldCount
   */
  public int getSeatHoldCount() {
    return 0;
  }

}
