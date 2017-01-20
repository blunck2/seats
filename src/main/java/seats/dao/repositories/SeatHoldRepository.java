package seats.dao.repositories;

import seats.model.SeatHold;

import org.springframework.data.repository.CrudRepository;


/**
 * <p>
 * Spring data repository for SeatHold
 * </p>
 */
public interface SeatHoldRepository extends CrudRepository<SeatHold, Integer> {

  /**
   * Locates the SeatHold with the id provided
   */
  public SeatHold findById(Integer id);

}
