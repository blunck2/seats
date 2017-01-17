package seats.model;

/**
 * <p>
 * A checked exception that is meant to indicate that the seat 
 * in question is not held.
 * </p>
 */
public class SeatNotHeldException extends Exception {

  /**
   * Creates an SeatNotHeldException based on a message
   * and cause
   * @param message the exception message
   * @param cause the underlying cause
   */
  public SeatNotHeldException(String message, Throwable cause) {
    super(message, cause);
  }

  
  /**
   * Creates an SeatNotHeldException based on a message
   * @param message the exception message
   */
  public SeatNotHeldException(String message) {
    super(message);
  }
}
