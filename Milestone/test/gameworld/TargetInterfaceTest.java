package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Target class. These tests verify that the target's health,
 * movement, and state are handled correctly.
 */
public class TargetInterfaceTest {

  private TargetInterface target;
  private RoomInterface room;

  /**
   * Sets up the test environment by creating a new target in a room.
   */
  @Before
  public void setUp() {
    room = new Room(new int[] { 0, 0 }, new int[] { 2, 2 }, "Armory", 0,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    target = new Target(room, 50, "Doctor Lucky");
  }

  /**
   * Tests the Target constructor to verify that the target is initialized with
   * valid health and name.
   */
  @Test
  public void testConstructorValid() {
    assertEquals("Doctor Lucky", target.getName());
    assertEquals(50, target.getHealth());
    assertEquals(room, target.getCurrentRoom());
  }

  /**
   * Tests that an IllegalArgumentException is thrown when the health is set to a
   * negative value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidHealth() {
    new Target(room, -10, "Sick Doctor");
  }

  /**
   * Tests the movement of the target to a new room.
   */
  @Test
  public void testMove() {
    RoomInterface newRoom = new Room(new int[] { 3, 3 }, new int[] { 5, 5 }, "Billiard Room", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    target.move(newRoom);
    assertEquals(newRoom, target.getCurrentRoom());
  }

  /**
   * Tests if the movement of the target check a null input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInValid() {
    target.move(null);
  }

  /**
   * Tests the getHealth() method to verify the correct health is returned.
   */
  @Test
  public void testGetHealth() {
    assertEquals(50, target.getHealth());
  }

  /**
   * Tests the takeDamage() method to ensure health is reduced correctly.
   */
  @Test
  public void testTakeDamage() {
    // Test reducing health by a valid amount
    target.takeDamage(10); // Reducing health by 10
    assertEquals(40, target.getHealth()); // Health should now be 40

    // Test reducing health below 0
    target.takeDamage(100); // Reducing by more than the remaining health
    assertEquals(0, target.getHealth()); // Health should not go below 0
  }

  /**
   * Tests the isAlive() method to ensure it returns true for positive health and
   * false for zero health.
   */
  @Test
  public void testIsAlive() {
    assertTrue(target.isAlive());
    // Test health reduction and alive status
    target.takeDamage(49); // Reducing health, but not enough to kill
    assertTrue(target.isAlive()); // Should still be alive

    // Further damage to make health exactly 0
    target.takeDamage(1); // Reducing health to exactly 0
    assertFalse(target.isAlive()); // Should die now
  }

  /**
   * Tests if two targets are equal using hash code.
   */
  @Test
  public void testEqualsAndHashCode() {
    RoomInterface room1 = new Room(new int[] { 0, 0 }, new int[] { 1, 1 }, "Room1", 0,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());
    RoomInterface room2 = new Room(new int[] { 1, 1 }, new int[] { 2, 2 }, "Room2", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    Target target1 = new Target(room1, 50, "Doctor Lucky");
    Target target2 = new Target(room1, 50, "Doctor Lucky");
    Target target3 = new Target(room2, 50, "Doctor Lucky");

    // Test equals
    assertTrue(target1.equals(target1));
    assertTrue(target1.equals(target2));
    assertFalse(target1.equals(target3));
    assertFalse(target1.equals(null));
    assertFalse(target1.equals("Invalid"));

    // Test hashCode
    assertEquals(target1.hashCode(), target2.hashCode());
    assertNotEquals(target1.hashCode(), target3.hashCode());
  }

  /**
   * Tests if toString valid.
   */
  @Test
  public void testToString() {

    String expectedOutput = "Target character name: Doctor Lucky, health: 50, currentRoom: Armory"
        + "\n";
    assertEquals(expectedOutput, target.toString());
    target.takeDamage(20);
    String expectedOutput1 = "Target character name: Doctor Lucky, health: 30, currentRoom: Armory"
        + "\n";
    assertEquals(expectedOutput1, target.toString());
  }

  /**
   * Tests if toString valid with no room assigned.
   */
  @Test
  public void testToStringNoRoom() {
    // Create a target with no current room
    Target targetNoRoom = new Target(null, 50, "Doctor Lucky");

    // Expected string output when currentRoom is null
    String expectedOutput = "Target character name: Doctor Lucky, health: 50, currentRoom: None\n";
    ;

    // Assert that the actual toString() output matches the expected output
    assertEquals(expectedOutput, targetNoRoom.toString());
  }

}
