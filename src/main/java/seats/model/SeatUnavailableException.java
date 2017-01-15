package seats.model;

/**
 * <p>
 * A checked exception that is meant to indicate a seat is unavailable.
 * </p>
 */
public class SeatUnavailableException extends Exception {

  /**
   * Creates an SeatUnavailableException based on a message
   * and cause
   * @param message the exception message
   * @param cause the underlying cause
   */
  public SeatUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }

  
  /**
   * Creates an SeatUnavailableException based on a message
   * @param message the exception message
   */
  public SeatUnavailableException(String message) {
    super(message);
  }
}
