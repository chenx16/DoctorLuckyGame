package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import coordinate.Coordinate;
import item.ItemInterface;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import pet.Pet;
import pet.PetInterface;
import room.Room;
import room.RoomInterface;

/**
 * Test class for the Pet class.
 *
 * This class provides unit tests for the Pet class, ensuring that the pet's
 * behavior, such as moving between rooms and checking room occupancy, functions
 * as expected.
 */
public class PetInterfaceTest {

  private PetInterface pet;
  private RoomInterface room1;
  private RoomInterface room2;

  /**
   * Sets up the test environment by initializing rooms and the pet. The pet
   * starts in the "Living Room".
   */
  @Before
  public void setUp() {
    // Set up rooms

    room1 = new Room(new Coordinate(0, 0), new Coordinate(2, 2), "Living Room", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    room2 = new Room(new Coordinate(3, 3), new Coordinate(5, 5), "Bedroom", 3,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    // Set up pet in room1
    pet = new Pet("Fortune the Cat", room1);
  }

  /**
   * Tests the pet's initialization, including its name and starting room.
   */
  @Test
  public void testPetInitialization() {
    assertEquals("Fortune the Cat", pet.getName());
    assertEquals(room1, pet.getCurrentRoom());
  }

  /**
   * Tests the pet's ability to move to a different room.
   */
  @Test
  public void testMoveTo() {
    pet.moveTo(room2);
    assertEquals(room2, pet.getCurrentRoom());
  }

  /**
   * Tests the pet's behavior when moved to a null room.
   */
  @Test
  public void testMoveToNullRoom() {
    pet.moveTo(null);
    assertNull(pet.getCurrentRoom());
  }

  /**
   * Tests the pet's ability to return its name.
   */
  @Test
  public void testGetName() {
    assertEquals("Fortune the Cat", pet.getName());
  }
}
