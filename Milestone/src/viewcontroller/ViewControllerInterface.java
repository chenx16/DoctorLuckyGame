package viewcontroller;

import viewcontroller.ViewController.GameKeyListener;
import viewcontroller.ViewController.GameMouseListener;

/**
 * ViewControllerInterface defines the required methods for a controller that
 * bridges the view and model in the game. This interface enforces a clear
 * separation of concerns and makes the controller easier to test.
 */
public interface ViewControllerInterface {

  /**
   * Processes the current player's turn, handling computer automation or waiting
   * for human player input.
   */
  void processTurn();

  /**
   * Updates the game view to reflect the current turn's state.
   */
  void updateTurnInfo();

  /**
   * Handles the logic for ending the game and provides the option to restart or
   * quit.
   */
  void handleGameOver();

  /**
   * Starts a new game by resetting the world and reinitializing the view.
   */
  void startNewGame();

  /**
   * Retrieves the {@code GameMouseListener} for handling mouse events.
   * 
   * @return an instance of {@code GameMouseListener}.
   */
  GameMouseListener getGameMouseListener();

  /**
   * Retrieves the {@code GameKeyListener} for handling keyboard events.
   * 
   * @return an instance of {@code GameKeyListener}.
   */
  GameKeyListener getGameKeyListener();

  /**
   * Enables testing mode for the controller.
   * 
   * <p>
   * This method modifies the controller's behavior to support testing scenarios,
   * such as bypassing user prompts or dialogs.
   * </p>
   */
  void setIsTesting();
}