package gameworld;

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
}
