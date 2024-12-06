package view;

import java.awt.event.ActionListener;

/**
 * Interface for the {@code AboutPanel}, defining the methods necessary for a
 * view component that provides an introduction to the game.
 */
public interface AboutPanelInterface {

  /**
   * Registers an action listener for the "Start Game" button.
   *
   * @param listener the action listener to be registered.
   */
  void registerActionListener(ActionListener listener);
}
