package seats.services;

import seats.model.SeatHold;


/**
 * <p>
 * Facilitates the holding and reservation of seats in the venue.
 * </p>
 *
 * <p> 
 * Seats are initially considered to be "open" in that they are
 * not held and they are not reserved.  Open seats may must first be
 * held before they may be reserved for purchase.  Seats may only be
 * held for a fixed amount of time.  When held seats are not reserved
 * within the time period in question they are returned to the open
 * state.
 * </p>
 */
public interface TicketService {
  /**
   * The number of seats in the venue that are neither held nor reserved
   *
   * @return the number of tickets available in the venue
   */
  int numSeatsAvailable();

  
  /**
   * Find and hold the best available seats for a customer
   *
   * @param numSeats the number of seats to find and hold
   * @param customerEmail unique identifier for the customer
   * @return a SeatHold object identifying the specific seats and related
   information
  */
  SeatHold findAndHoldSeats(int numSeats, String customerEmail);

  
  /**
   * Commit seats held for a specific customer
   *
   * @param seatHoldId the seat hold identifier
   * @param customerEmail the email address of the customer to which the
   *                      seat hold is assigned
   * @return a reservation confirmation code
   */
  String reserveSeats(int seatHoldId, String customerEmail);
  
}
