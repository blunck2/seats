package seats.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import seats.services.TicketService;

import seats.model.SeatHold;


/**
 * <p>
 * ReST controller for the TicketService
 * </p>
 */
@RestController
@RequestMapping("/ticketservice")
public class TicketServiceController {
  @Autowired
  private TicketService service;

  /**
   * @see TicketService#numSeatsAvailable
   */
  @RequestMapping(value="/numSeatsAvailable")
  public int numSeatsAvailable() {
    return service.numSeatsAvailable();
  }

  /**
   * @see TicketService#findAndHoldSeats
   */
  @RequestMapping(value="/findAndHoldSeats", method=RequestMethod.POST)
  public SeatHold findAndHoldSeats(@RequestParam("numSeats") int numSeats,
                                   @RequestParam("customerEmail") String customerEmail) {
    return service.findAndHoldSeats(numSeats, customerEmail);
  }

  
  /**
   * @see TicketService#reserveSeats
   */
  @RequestMapping(value="/reserveSeats", method=RequestMethod.POST)
  public String reserveSeats(@RequestParam("seatHoldId") int seatHoldId,
                             @RequestParam("customerEmail") String customerEmail) {
    return service.reserveSeats(seatHoldId, customerEmail);
  }
  
}
