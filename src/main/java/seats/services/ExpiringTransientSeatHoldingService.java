package seats.services;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;

import org.apache.log4j.Logger;

import seats.model.Venue;
import seats.model.Seat;
import seats.model.SeatHold;
import seats.model.SeatNotHeldException;

import static seats.common.Messages.*;


/**
 * <p>
 * An in-memory seat holding service that auto-expires seat holdings
 * after a configurable amount of time.
 * </p>
 */
public class ExpiringTransientSeatHoldingService
  implements TransientSeatHoldingService {
  private static final Logger logger = Logger.getLogger(ExpiringTransientSeatHoldingService.class);
  
  // the venue in which the seats are held
  private Venue venue;

  // the SeatHold instances that have been created
  private ConcurrentMap<Integer, SeatHold> index;

  // a thread-safe numeric id generator for SeatHold ids
  private AtomicInteger idGenerator;

  // how long we should wait (in milliseconds) before expiring seat holds
  private int expirationTimeInMilliSeconds;

  // how often we should look for seat holds that have expired
  private int expirationCheckCycleTimeInMilliSeconds;

  // an executor that will periodically check for expired seat holds
  private ScheduledExecutorService expirationCheckExecutor;

  // a Runnable that will clean up the index when called
  class SeatHoldIndexExpirationTask implements Runnable {
    SeatHoldIndexExpirationTask() { }
    /**
     * @Override
     */
    public void run() {
      /*
       * Delegate all logic to another method that can both wrapped in
       * a try-catch block and can more importantly be unit tested.  this
       * is an example of TDD influencing the design of code.
       */
      try {
        expireSeatHoldings();
      } catch (Throwable t) {
        logger.fatal(t);
      }
    }
  }

  /**
   * Creates an ExpiringTransientSeatHoldingService
   */
  public ExpiringTransientSeatHoldingService() {
    idGenerator = new AtomicInteger();
    index = new ConcurrentHashMap<>();
    expirationCheckExecutor = Executors.newSingleThreadScheduledExecutor();
  }

  
  /**
   * Expires SeatHold records from the index
   */
  protected void expireSeatHoldings() {
    // determine the maximum age of permitted seat holds
    DateTime maxAgePermitted = new DateTime();
    maxAgePermitted.minus(expirationTimeInMilliSeconds);
    
    // iterate over the seat holds in the index
    for (SeatHold seatHold : index.values()) {
      DateTime seatHoldCreationTime = seatHold.getCreationTime();
      
      /*
       * if the current seat was created before the maximum age permitted
       * then remove it from the map
       */
      if (seatHoldCreationTime.isBefore(maxAgePermitted)) {
        
        // unhold the seats in the venue
        for (Seat heldSeat : seatHold.getSeatsHeld()) {
          try {
            int rowNumber = heldSeat.getRowNumber();
            int seatNumber = heldSeat.getSeatNumber();
            venue.unholdSeat(rowNumber, seatNumber);
          } catch (SeatNotHeldException e) {
            /*
             * if the application is functioning properly this catch block
             * should not be reached as there should only be 1 
             * SeatHoldingService that is managing the Venue.  if another
             * thread and another service holds a reference to the same
             * Venue that is within this instance and is changing the
             * state of the underlying Seat instances from held to reserved
             * or open then it represents a very seriously logic error
             */
            logger.fatal(SEAT_STATE_ALTERED);
          }
          
        }
        
        index.remove(seatHold.getId());
      }
    }
  }
  
  
  @PostConstruct
  public void init() throws Exception {
    /*
     * schedule a task to periodically walk through the index and expire
     * old seat hold records
     */
    Runnable task = new SeatHoldIndexExpirationTask();
    long delay = expirationCheckCycleTimeInMilliSeconds;
    long period = expirationCheckCycleTimeInMilliSeconds;
    expirationCheckExecutor.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
  }

  /**
   * Returns the Venue in use
   */
  public Venue getVenue() { return venue; }

  /**
   * Sets the Venue to use
   */
  public void setVenue(Venue venue) { this.venue = venue; }


  /**
   * Returns the amount of time (in milliseconds) that SeatHold instances
   * should be permitted to reside in the cache before they are expired
   */
  public int getExpirationTimeInMilliSeconds() {
    return expirationTimeInMilliSeconds;
  }

  /**
   * Sets the amount of time (in milliseconds) that SeatHold instances
   * should be permitted to reside in the cache before they are expired
   */
  public void setExpirationTimeInMilliSeconds(int expirationTimeInMilliSeconds) {
    this.expirationTimeInMilliSeconds = expirationTimeInMilliSeconds;
  }

  
  /**
   * Returns the cycle time for how often we check the seat hold cache
   */
  public int getExpirationCheckCycleTimeInMilliSeconds() {
    return expirationCheckCycleTimeInMilliSeconds;
  }

  /**
   * Sets the cycle time for how often we check the seat hold cache
   */
  public void setExpirationCheckCycleTimeInMilliSeconds(int expirationCheckCycleTimeInMilliSeconds) {
    this.expirationCheckCycleTimeInMilliSeconds = expirationCheckCycleTimeInMilliSeconds;
  }

  /**
   * Returns the total number of seats that are presently held
   */
  public int getSeatHoldingCount() { return index.keySet().size(); }

  
  /**
   * @see SeatHoldingService#addSeatHold
   */
  public SeatHold addSeatHold(SeatHold holding) {
    // create a new thread-safe integer id and set it in the holding
    int seatHoldId = idGenerator.incrementAndGet();
    holding.setId(seatHoldId);
    System.out.println("addSeatHold id: " + seatHoldId);

    // store the holding in the index
    index.put(seatHoldId, holding);

    // return the holding with the new id
    return holding;
  }

  
  /**
   * @see SeatHoldingService#removeSeatHoldById
   */
  public void removeSeatHoldById(int seatHoldId)
    throws NoSuchSeatHoldException {

    /*
     * throw an exception of the index does not have the id in question
     * (this would occur if the seat hold ages off)
     */
    if (! index.containsKey(seatHoldId)) {
      throw new NoSuchSeatHoldException(SEAT_HOLD_ID_UNKNOWN);
    }

    // remove the seat hold requested
    index.remove(seatHoldId);
  }

  /**
   * @see SeatHoldingService#getSeatHoldCount
   */
  public int getSeatHoldCount() {
    return index.keySet().size();
  }


  /**
   * @see SeatHoldingService#getSeatHoldById
   */
  public SeatHold getSeatHoldById(int id) throws NoSuchSeatHoldException {
    // if the id does not exist through an exception
    if (! index.containsKey(id)) {
      throw new NoSuchSeatHoldException(SEAT_HOLD_ID_UNKNOWN);
    }

    // locate the seat hold and return it
    SeatHold seatHold = index.get(id);
    return seatHold;
  }
  
}
