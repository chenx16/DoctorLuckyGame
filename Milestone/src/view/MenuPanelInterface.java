package view;

import java.awt.event.ActionListener;

/**
 * Interface for the MenuPanel, providing methods for menu interaction.
 */
public interface MenuPanelInterface {

  /**
   * Registers an action listener for the menu items.
   *
   * @param listener the action listener to register.
   */
  void registerActionListener(ActionListener listener);
}
