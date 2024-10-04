package gameworld;

/**
 * Represents an item in the game world. An item can have a name and can cause
 * damage to a target.
 */
public interface ItemInterface {
  /**
   * Returns the name of the item.
   * 
   * @return the name of the item
   */
  String getName();

  /**
   * Returns the damage value of the item.
   * 
   * @return the damage value of the item
   */
  int getDamage();
}
