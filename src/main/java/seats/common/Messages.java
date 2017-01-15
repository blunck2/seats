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

}
