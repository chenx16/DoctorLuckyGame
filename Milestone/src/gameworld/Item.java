package gameworld;

public class Item implements ItemInterface {
  private int damage;
  private String name;

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
    return "The item " + name + " has " + damage + " damage.";
  }
}
