package seats.dao;

import seats.model.SeatHold;

import org.springframework.data.repository.Repository;


/**
 * <p>
 * Spring data repository for SeatHold
 * </p>
 */
public interface SeatHoldRepository extends Repository<SeatHold, Integer> {

  /**
   * Finds the SeatHold with the id provided
   */
  public SeatHold findOne(Integer id);
  
}
