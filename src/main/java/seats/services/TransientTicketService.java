package seats.services;


/**
 * <p>
 * A TicketService that is backed by an in-memory representation that does not
 * survive across restarts of the JVM.
 * </p>
 */
public interface TransientTicketService extends TicketService {

}
