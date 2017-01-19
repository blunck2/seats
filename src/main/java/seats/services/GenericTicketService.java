package seats.services;

import seats.model.Venue;
import seats.model.SeatHold;
import seats.model.Seat;
import seats.model.SeatUnavailableException;
import static seats.model.SeatHoldRequestStatusEnum.*;
import static seats.common.Messages.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;

import org.apache.commons.lang.StringUtils;


/**
 * <p>
 * An implementation of the TicketService that relies on an abstracted
 * SeatLocatorService and SeatHoldingService.
 * </p>
 */
public class GenericTicketService implements TicketService {
  // the underlying venue
  private Venue venue;

  // locates the best seats
  private SeatLocatorService seatLocatorService;

  // keeps track of the seats that are held
  private SeatHoldingService seatHoldingService;

  private static Logger logger = Logger.getLogger(GenericTicketService.class);

  
  /**
   * Creates a StatefuleTransientTicketService
   */
  public GenericTicketService() { }

  /**
   * Returns the Venue 
   */
  public Venue getVenue() { return venue; }

  /**
   * Sets the Venue
   */
  public void setVenue(Venue venue) { this.venue = venue; }

  
  /**
   * Returns the seat holding service that is used
   */
  public SeatHoldingService getSeatHoldingService() {
    return seatHoldingService;
  }

  /**
   * Sets the seat holding service that is used
   */
  public void setSeatHoldingService(SeatHoldingService seatHoldingService) {
    this.seatHoldingService = seatHoldingService;
  }


  /**
   * Returns the seat locator service
   */
  public SeatLocatorService getSeatLocatorService() {
    return seatLocatorService;
  }

  /**
   * Sets the seat locator service
   */
  public void setSeatLocatorService(SeatLocatorService seatLocatorService) {
    this.seatLocatorService = seatLocatorService;
  }

  /**
   * @see TransientTicketService#numSeatsAvailable
   */
  public int numSeatsAvailable() {
    // if no venue is set then 0 seats are available
    if (venue == null) {
      return 0;
    }
    
    List<Seat> openSeats = venue.getOpenSeats();
    return openSeats.size();
  }

  
  /**
   * @see TransientTicketService#findAndHoldSeats
   */
  public SeatHold findAndHoldSeats(int numSeats,
                                   String customerEmailAddress) {
    // create a SeatHold 
    SeatHold seatHold = new SeatHold();
    seatHold.setCustomerEmailAddress(customerEmailAddress);
    seatHold.setNumberOfSeatsRequested(numSeats);

    // fast-fail on empty or null customerEmailAddress
    if (StringUtils.isBlank(customerEmailAddress)) {
      seatHold.setStatus(FAILURE_DUE_TO_INVALID_PARAMETERS);
      seatHold.setStatusDetails("customerEmailAddress");

      return seatHold;
    }

    // locate the seats, updating the SeatHold if errors occurr
    boolean seatsLocated = false;
    List<Seat> seatsToHold = new ArrayList<>();
    try {
      List<Seat> locatedSeats = seatLocatorService.locateSeats(numSeats);
      seatsToHold.addAll(locatedSeats);
      seatsLocated = true;
      seatHold.setStatus(SUCCESS);
      seatHold.setSeatsHeld(seatsToHold);
      seatHold.setNumberOfSeatsHeld(seatsToHold.size());
    } catch (InsufficientAvailableSeatsException e) {
      seatHold.setStatus(FAILURE_DUE_TO_INSUFFICIENT_OPEN_SEATS);
      seatHold.setStatusDetails(e.getMessage());
    } catch (IllegalArgumentException e) {
      seatHold.setStatus(FAILURE_DUE_TO_INVALID_PARAMETERS);
      seatHold.setStatusDetails(e.getMessage());
    }

    // hold the seats
    for (Seat seat : seatsToHold) {
      seat.hold(customerEmailAddress);
    }

    // notify the seat holding service
    seatHold = seatHoldingService.addSeatHold(seatHold);

    // return the seatHold
    return seatHold;
  }

  
  /**
   * @see TransientTicketService#reserveSeats
   * @throws IllegalArgumentException if the seatHoldId is negative or
   * if the email address provided does not match the email address on
   * the seat hold
   */
  public String reserveSeats(int seatHoldId, String customerEmailAddress) {
    // seat hold ids should be positive
    if (seatHoldId < 0) {
      throw new IllegalArgumentException(INVALID_SEAT_HOLD_ID);
    }

    // locate the seat hold with the id provided
    SeatHold seatHold = null;
    try {
      seatHold = seatHoldingService.getSeatHoldById(seatHoldId);
    } catch (NoSuchSeatHoldException e) {
      throw new IllegalArgumentException(SEAT_HOLD_ID_UNKNOWN);
    }

    // get the underlying seats that should be reserved
    List<Seat> heldSeats = seatHold.getSeatsHeld();

    // verify the customerEmail address matches on all seats
    boolean emailAddressesMatch = validateEmailAddressesMatch(customerEmailAddress, heldSeats);
    if (!emailAddressesMatch) {
      throw new IllegalArgumentException(SEAT_HOLD_CUSTOMER_EMAIL_ADDRESS_MISMATCH);
    }

    // reserve each held seat
    for (Seat seat : heldSeats) {
      int rowNumber = seat.getRowNumber();
      int seatNumber = seat.getSeatNumber();
      try {
        venue.reserveSeat(rowNumber, seatNumber, customerEmailAddress);
      } catch (SeatUnavailableException e) {
        /*
         * a race condition occurs between the thread that removes the
         * seat seat hold from cache and this thread to reserve the
         * seat.  in order for this to occur the thread to remove the
         * seat hold would have to update the venue while another
         * thread held the seat and this thread attempted to reserve
         * the seat.  we could use a shared semaphore to alleviate
         * this race condition.  for now we'll log it and move on as
         * this is simply a programming assignment as opposed to
         * production code.
         */
        logger.warn(SEAT_UNAVAILABLE_FOR_RESERVATION);
      }
    }

    // release the seat hold
    try {
      seatHoldingService.removeSeatHoldById(seatHoldId);
    } catch (NoSuchSeatHoldException e) {
      /*
       * a race condition occurs between the thread that removes the
       * seat hold from the cache and this thread to remove the seat
       * hold.  the side effect of this race condition is this
       * exception occurring and that's ok.  we will simply log the
       * warning and proceed.
       */
      logger.warn(SEAT_HOLD_EXPIRED);
    }

    // the confirmation code isn't used elsewhere so just return a UUID
    return UUID.randomUUID().toString();
  }


  /**
   * Validate that all of the seats provided contain an email address that
   * matches the emailAddressToMatch parameter
   * @param emailAddressToMatch the email address to compare against
   * the email addresses in the seats provided
   * @param seats the seats to search
   * @return true if the customerEmailAddress in each Seat matches the
   * emailAddressToMatch, false otherwise
   */
  protected boolean validateEmailAddressesMatch(String emailAddressToMatch,
                                                List<Seat> seats) {
    // make sure seats actually exist
    if ((seats == null) || (seats.size() == 0)) {
      return false;
    }

    // add all of the email addresses to a set
    Set<String> distinctEmailAddresses = new TreeSet<>();
    for (Seat seat : seats) {
      distinctEmailAddresses.add(seat.getCustomerEmailAddress());
    }

    // if there's more than 1 email address in the set then return false
    if (distinctEmailAddresses.size() > 1) {
      return false;
    }

    // get the email address from the seats
    String emailAddressFromSeats = distinctEmailAddresses.iterator().next();

    // compare the email address from the seats with the search one and return
    return emailAddressFromSeats.equals(emailAddressToMatch);
  }
  

}
