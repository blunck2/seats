package seats.services;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;

import seats.model.Venue;
import seats.model.SeatHold;

import static seats.common.Messages.*;


/**
 * <p>
 * An in-memory seat holding service that auto-expires seat holdings
 * after a configurable amount of time.
 * </p>
 */
public class ExpiringTransientSeatHoldingService
  implements TransientSeatHoldingService {
  // the venue in which the seats are held
  private Venue venue;

  // the SeatHold instances that have been created
  private ConcurrentMap<Integer, SeatHold> index;

  // a thread-safe numeric id generator for SeatHold ids
  private AtomicInteger idGenerator;

  // how long we should wait (in seconds) before expiring seat holds
  private int expirationTimeInSeconds;

  // how often we should look for seat holds that have expired
  private int expirationCheckCycleTimeInSeconds;

  // an executor that will periodically check for expired seat holds
  private ScheduledExecutorService expirationCheckExecutor;

  // a Runnable that will clean up the index when called
  class SeatHoldIndexExpirationTask implements Runnable {
    SeatHoldIndexExpirationTask() { }

    /**
     * @Override
     */
    public void run() {
      // determine the maximum age of permitted seat holds
      DateTime maxAgePermitted = new DateTime();
      maxAgePermitted.minusSeconds(expirationTimeInSeconds);

      // iterate over the seat holds in the index
      for (SeatHold seatHold : index.values()) {
        DateTime seatHoldCreationTime = seatHold.getCreationTime();

        /*
         * if the current seat was created before the maximum age permitted
         * then remove it from the map
         */
        if (seatHoldCreationTime.isBefore(maxAgePermitted)) {
          index.remove(seatHold.getId());
        }
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

  
  @PostConstruct
  public void init() throws Exception {
    /*
     * schedule a task to periodically walk through the index and expire
     * old seat hold records
     */
    Runnable task = new SeatHoldIndexExpirationTask();
    long delay = expirationCheckCycleTimeInSeconds;
    long period = expirationCheckCycleTimeInSeconds;
    expirationCheckExecutor.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS);
  }


  /**
   * Returns the amount of time (in seconds) that SeatHold instances
   * should be permitted to reside in the cache before they are expired
   */
  public int getExpirationTimeInSeconds() {
    return expirationTimeInSeconds;
  }

  /**
   * Sets the amount of time (in seconds) that SeatHold instances
   * should be permitted to reside in the cache before they are expired
   */
  public void setExpirationTimeInSeconds(int expirationTimeInSeconds) {
    this.expirationTimeInSeconds = expirationTimeInSeconds;
  }

  
  /**
   * Returns the cycle time for how often we check the seat hold cache
   */
  public int getExpirationCheckCycleTimeInSeconds() {
    return expirationCheckCycleTimeInSeconds;
  }

  /**
   * Sets the cycle time for how often we check the seat hold cache
   */
  public void setExpirationCheckCycleTimeInSeconds(int expirationCheckCycleTimeInSeconds) {
    this.expirationCheckCycleTimeInSeconds = expirationCheckCycleTimeInSeconds;
  }

  
  /**
   * @see SeatHoldingService#addSeatHolding
   */
  public SeatHold addSeatHolding(SeatHold holding) {
    // create a new thread-safe integer id and set it in the holding
    int seatHoldId = idGenerator.incrementAndGet();
    holding.setId(seatHoldId);

    // store the holding in the index
    index.put(seatHoldId, holding);

    // return the holding with the new id
    return holding;
  }

  
  /**
   * @see SeatHoldingService#removeSeatHoldingById
   */
  public void removeSeatHoldingById(int seatHoldId)
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
  
  
      
}
