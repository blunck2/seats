package seats.services;

/**
 * <p>
 * A checked exception that is meant to indicate that the seat hold
 * in question does not exist.
 * </p>
 */
public class NoSuchSeatHoldException extends Exception {

  /**
   * Creates an NoSuchSeatHoldException based on a message
   * and cause
   * @param message the exception message
   * @param cause the underlying cause
   */
  public NoSuchSeatHoldException(String message, Throwable cause) {
    super(message, cause);
  }

  
  /**
   * Creates an NoSuchSeatHoldException based on a message
   * @param message the exception message
   */
  public NoSuchSeatHoldException(String message) {
    super(message);
  }
}
