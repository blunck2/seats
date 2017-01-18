package seats.common;

/**
 * <p>
 * Common messages used in exceptions and logs
 * </p>
 */
public class Messages {
  public static String ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO = "row seat count must be > 0";

  public static String CENTER_ROW_SEAT_COUNT_MUST_BE_GREATER_THAN_ZERO = "center row seat count must be > 0";

  public static String CENTER_ROW_SEAT_COUNT_EXCEEDS_ROW_SIZE = "center row seat count exceeds row size";

  public static String EMPTY_VENUE_NOT_PERMITTED = "unable to create a venue without seats or rows";

  public static String ROW_NUMBERS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE = "row numbers must be >= 1";

  public static String ROW_NUMBER_DOES_NOT_EXIST = "row number does not exist";

  public static String SEAT_IS_NOT_HELD_OR_RESERVED = "seat is not held or reserved";

  public static String SEAT_DOES_NOT_EXIST = "seat does not exist";

  public static String SEAT_IS_NOT_OPEN = "seat is not open";

  public static String EMAIL_ADDRESS_IS_NULL_OR_BLANK = "email address is null or blank";

  public static String EMAIL_ADDRESS_DOES_NOT_MATCH = "email address does not match";

  public static String SEAT_IS_NOT_HELD = "seat is not held";

  public static String SEAT_IS_RESERVED = "seat is reserved";

  public static String INSUFFICIENT_OPEN_SEATS = "insufficient open seats";

  public static String UNABLE_TO_LOCATE_ZERO_OR_NEGATIVE_SEATS = "unable to locate 0 or negative seats in the venue";

  public static String SEAT_IS_OPEN = "seat is open";

  public static String SEAT_HOLD_ID_UNKNOWN = "seat hold id unknown";

  public static String SEAT_STATE_ALTERED = "seat state has been altered";

  public static String INVALID_SEAT_HOLD_ID = "seat hold id unknown";

}
