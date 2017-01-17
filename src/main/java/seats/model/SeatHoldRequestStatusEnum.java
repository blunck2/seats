package seats.model;

/**
 * <p>
 * Captures the state of a seat hold request
 * </p>
 * 
 * <p>
 * A seat may be held successfully or it may not be held due to a variety
 * of different reasons.  This enumerated type captures those failure pathways
 * so they may be logged appropriately or returned to the caller.
 * </p>
 */
public enum SeatHoldRequestStatusEnum {
  SUCCESS,
  FAILURE_DUE_TO_INVALID_PARAMETERS,
  FAILURE_DUE_TO_INSUFFICIENT_OPEN_SEATS
}
