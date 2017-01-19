package seats.services;


/**
 * <p>
 * A SeatHoldingServicef that is backed by a durable store.  Invocations against
 * this service will be persistent to storage and state will be preserved
 * between restarts of the service.
 * </p>
 */
public interface DurableSeatHoldingService extends SeatHoldingService {

}
