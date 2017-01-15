package seats.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


/**
 * <p>
 * Unit tests for the VenueFactory class
 * </p>
 */
public class VenueFactoryTest {
  
  @Test
  public void testCreateVenue() {
    Venue venue = null;
    
    // verify you cannot create a venue with negative rows or seats
    try {
      venue = VenueFactory.createVenue(-1, -1, 0);
      fail("created vanue with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a venue with 0 rows or seats
    try {
      venue = VenueFactory.createVenue(0, 0, 0);
      fail("created vanue with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    // verify you cannot create a venue with rows but no seats
    try {
      venue = VenueFactory.createVenue(10, 0, 0);
      fail("created vanue with invalid configuration");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to happen
    }

    /*
     * verify you can create a venue with rows and seats but none that
     * are considered to be center row
     */
    venue = VenueFactory.createVenue(10, 5, 2);
    assertNotNull("failed to create valid venue", venue);
    assertEquals("invalid row count", 10, venue.getRowCount());
    try {
      Row row = venue.getRow(-1);
      fail("retrieved negative row");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    }
    try {
      Row row = venue.getRow(0);
      fail("retrieved zero row");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    }
    try {
      Row row = venue.getRow(100);
      fail("retrieved non-existent row");
    } catch (IllegalArgumentException e) {
      // do nothing; this is what we expect to have happen
    }
    Row row = venue.getRow(1);
    assertNotNull("row not created", row);

    
  }

}
