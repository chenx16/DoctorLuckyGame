package view;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

public interface ViewInterface {

  /**
   * Displays the game window.
   */
  void display();

  /**
   * Updates the current game panel to show the changes made in the world.
   */
  void updateGamePanel(List<Point> playerPositions, Point targetPosition);

  /**
   * Updates the information panel to display information like whose turn it is.
   * 
   * @param info The information to display.
   */
  void updateInfoPanel(String info);

  /**
   * Updates the menu panel to provide options for starting a new game or
   * quitting.
   */
  void initializeMenu();

  /**
   * Displays an error message in the view.
   * 
   * @param errorMessage The error message to display.
   */
  void showErrorMessage(String errorMessage);

  /**
   * Registers an action listener for buttons or menu interactions.
   * 
   * @param listener An ActionListener to handle button clicks or menu actions.
   */
  void registerActionListener(ActionListener listener);

  /**
   * Registers a mouse listener for interactions in the game panel.
   * 
   * @param listener A MouseListener to handle mouse events like clicks.
   */
  void registerMouseListener(MouseListener listener);

  /**
   * Registers a key listener for keyboard interactions.
   * 
   * @param listener A KeyListener to handle key events like picking up items or
   *                 making moves.
   */
  void registerKeyListener(KeyListener listener);
}
