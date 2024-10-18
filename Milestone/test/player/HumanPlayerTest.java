package player;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import gameworld.Coordinate;
import gameworld.Room;
import gameworld.RoomInterface;

/**
 * Unit tests for the HumanPlayer class.
 */
public class HumanPlayerTest {

  private HumanPlayer humanPlayer;
  private RoomInterface startingRoom;

  /**
   * Sets up the test environment by creating players, rooms and items for
   * testing.
   */
  @Before
  public void setUp() {
    startingRoom = new Room(new Coordinate(0, 0), new Coordinate(1, 1), "Starting Room", 0,
        new ArrayList<>(), new ArrayList<>());
    humanPlayer = new HumanPlayer("Human", startingRoom, 2);
  }

  @Test
  public void testGetName() {
    assertEquals("Human", humanPlayer.getName());
  }

  @Test
  public void testMoveTo() {
    RoomInterface newRoom = new Room(new Coordinate(2, 2), new Coordinate(3, 3), "New Room", 1,
        new ArrayList<>(), new ArrayList<>());
    humanPlayer.moveTo(newRoom);
    assertEquals("New Room", humanPlayer.getCurrentRoom().getName());
  }
}
