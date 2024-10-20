package controller;

import java.io.IOException;

/**
 * Represents a Controller for Doctor Lucky: handle user moves by executing them
 * using the model; convey move outcomes to the user in some form.
 */

public interface ControllerInterface {
  /**
   * Execute a single game of Doctor Lucky given a Doctor Lucky Model. When the
   * game is over, the playGame method ends.
   *
   */
  void startGame() throws IOException;

}
