package viewcontroller;

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

}
