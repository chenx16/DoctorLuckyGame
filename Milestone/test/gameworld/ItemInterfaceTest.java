package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import item.Item;
import item.ItemInterface;

/**
 * Unit tests for the Item class. These tests verify item's name and damage are
 * handled correctly.
 */
public class ItemInterfaceTest {

  private ItemInterface item;

  /**
   * Sets up the test environment by creating an item.
   */
  @Before
  public void setUp() {
    item = new Item(10, "Revolver");
  }

  /**
   * Tests the constructor with valid parameters. Verifies that the name and
   * damage are set correctly.
   */
  @Test
  public void testConstructorValid() {
    assertEquals("Revolver", item.getName());
    assertEquals(10, item.getDamage());
  }

  /**
   * Tests the constructor with invalid damage value. Expects
   * IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidDamage() {
    new Item(-5, "Broken Item");
  }

  /**
   * Tests the getName() method to ensure the correct item name is returned.
   */
  @Test
  public void testGetName() {
    assertEquals("Revolver", item.getName());
  }

  /**
   * Tests the getDamage() method to ensure the correct damage value is returned.
   */
  @Test
  public void testGetDamage() {
    assertEquals(10, item.getDamage());
  }

  /**
   * Tests if two items are equal using hash code.
   */
  @Test
  public void testEqualsAndHashCode() {
    Item item1 = new Item(10, "Knife");
    Item item2 = new Item(10, "Knife");
    Item item3 = new Item(5, "Knife");

    // Test equals
    assertTrue(item1.equals(item2));
    assertFalse(item1.equals(item3));
    assertFalse(item1.equals(null));
    assertFalse(item1.equals("Invalid"));

    // Test hashCode
    assertEquals(item1.hashCode(), item2.hashCode());
    assertNotEquals(item1.hashCode(), item3.hashCode());

  }
}
