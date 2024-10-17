package gameworld;

import java.util.Objects;

/**
 * Represents an item in the game world.
 */
public class Item implements ItemInterface {
  private int damage;
  private String name;

  /**
   * Constructs a new Item with the specified damage and name.
   * 
   * @param damage the damage value this item can cause
   * @param name   the name of the item
   */
  public Item(int damage, String name) throws IllegalArgumentException {
    if (damage < 0) {
      throw new IllegalArgumentException();
    }
    this.damage = damage;
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getDamage() {
    return damage;
  }

  @Override
  public String toString() {
    return String.format("Item %s with %s damage.", this.getName(), this.getDamage());
  }

  /**
   * Checks if this item is equal to another item.
   * 
   * @param obj the object to compare with
   * @return true if the items are having same hashCode, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Item)) {
      return false;
    }
    Item other = (Item) obj;
    return this.hashCode() == other.hashCode();
  }

  /**
   * Generates a hash code for this item.
   *
   * @return the hash code for the item
   */
  @Override
  public int hashCode() {
    return Objects.hash(damage, name);
  }
}
