package controller;

import gameworld.WorldInterface;

/**
 * Represents a Controller for Doctor Lucky: handle user moves by executing them
 * using the model; convey move outcomes to the user in some form.
 */

public interface ControllerInterface {
  /**
   * Execute a single game of Doctor Lucky given a Doctor Lucky Model. When the
   * game is over, the playGame method ends.
   *
   * @param world a non-null Doctor Lucky world Model
   */
  void startGame(WorldInterface world);
}
