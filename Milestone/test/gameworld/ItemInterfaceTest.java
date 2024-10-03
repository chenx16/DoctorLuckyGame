package gameworld;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ItemInterfaceTest {

  private ItemInterface item;

  @Before
  public void setUp() {
    item = new Item(10, "Revolver"); // Use ItemInterface
  }

  @Test
  public void testConstructorValid() {
    assertEquals("Revolver", item.getName());
    assertEquals(10, item.getDamage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidDamage() {
    new Item(-5, "Broken Item");
  }

  @Test
  public void testGetName() {
    assertEquals("Revolver", item.getName());
  }

  @Test
  public void testGetDamage() {
    assertEquals(10, item.getDamage());
  }
}
