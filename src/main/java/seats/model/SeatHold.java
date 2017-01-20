package seats.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Id;
import static javax.persistence.GenerationType.*;

import org.joda.time.DateTime;


/**
 * <p> 
 * A representation of a request, on behalf of a customer with an
 * email address, to hold a fixed number of seats at a venue.
 * </p>
 */
public class SeatHold {
  // universely unique id of this hold
  @Id
  private int id;
  
  // the number of seats to hold
  private int numberOfSeatsHeld;

  // the number of seats that were requested to be held
  private int numberOfSeatsRequested;

  // the email address of the customer who held the seats
  private String customerEmailAddress;

  // the seats that are being held
  private List<Seat> seatsHeld;

  // the status of the seat hold request
  private SeatHoldRequestStatusEnum status;

  // additional information about the status of the SeatHold
  private String statusDetails;

  // the time when the hold was created
  private DateTime creationTime;
  
  
  /**
   * Creates a SeatHold
   */
  public SeatHold() {
    seatsHeld = new ArrayList<>();
    creationTime = new DateTime();
  }


  /**
   * Return the id of this SeatHold
   */
  public int getId() { return id; }

  /**
   * Sets the id of this SeatHold
   */
  public void setId(int id) { this.id = id; }
  

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
   * Returns the number of seats that were requested to be held
   */
  public int getNumberOfSeatsRequested() { return numberOfSeatsRequested; }

  /**
   * Sets the number of seats that were requested to be held
   */
  public void setNumberOfSeatsRequested(int numberOfSeatsRequested) {
    this.numberOfSeatsRequested = numberOfSeatsRequested;
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

  
  /**
   * Returns the status 
   */
  public SeatHoldRequestStatusEnum getStatus() { return status; }

  /**
   * Sets the status
   */
  public void setStatus(SeatHoldRequestStatusEnum status) {
    this.status = status;
  }

  
  /**
   * Returns additional details about the status
   */
  public String getStatusDetails() { return statusDetails; }

  /**
   * Sets additional details about the status
   */
  public void setStatusDetails(String statusDetails) {
    this.statusDetails = statusDetails;
  }


  /**
   * Returns the time the seat hold was created
   */
  public DateTime getCreationTime() { return creationTime; }

  /**
   * Sets the time the seat was held
   */
  public void setCreationTime(DateTime creationTime) {
    this.creationTime = creationTime;
  }

}
