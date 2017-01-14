package seats.model;

import java.util.List;
import java.util.ArrayList;


/**
 * <p> 
 * A representation of a request, on behalf of a customer with an
 * email address, to hold a fixed number of seats at a venue.
 * </p>
 */
public class SeatHold {
  // the number of seats to hold
  private int numberOfSeatsHeld;

  // the email address of the customer who held the seats
  private String customerEmailAddress;

  // the seats that are being held
  private List<Seat> seatsHeld;
  
  
  /**
   * Creates a SeatHold
   */
  public SeatHold() {
    seatsHeld = new ArrayList<>();
  }


  /**
   * Returns the number of seats that are held
   */
  public int getNumberOfSeatsHeld() { return numberOfSeatsHeld; }

  /**
   * Sets the number of seats to hold
   */
  public void setNumberOfSeatsHeld(int numberOfSeatsHeld) {
    this.numberOfSeatsHeld = numberOfSeatsHeld;
  }


  /**
   * Returns the customer's email address
   */
  public String getCustomerEmailAddress() { return customerEmailAddress; }

  
  /**
   * Sets the customer's email address
   */
  public void setCustomerEmailAddress(String customerEmailAddress) {
    this.customerEmailAddress = customerEmailAddress;
  }

  
  /**
   * Returns the Seat instances that are held
   */
  public List<Seat> getSeatsHeld() { return seatsHeld; }

  
  /**
   * Sets the Seat instances that are held
   */
  public void setSeatsHeld(List<Seat> seatsHeld) { this.seatsHeld = seatsHeld; }

  
  

}
