package seats.services;

/**
 * <p>
 * A checked exception that is meant to indicate there are insufficient
 * seats available in the venue.
 * </p>
 */
public class InsufficientAvailableSeatsException extends Exception {

  /**
   * Creates an InsufficientAvailableSeatsException based on a message
   * and cause
   * @param message the exception message
   * @param cause the underlying cause
   */
  public InsufficientAvailableSeatsException(String message, Throwable cause) {
    super(message, cause);
  }

  
  /**
   * Creates an InsufficientAvailableSeatsException based on a message
   * @param message the exception message
   */
  public InsufficientAvailableSeatsException(String message) {
    super(message);
  }
}
