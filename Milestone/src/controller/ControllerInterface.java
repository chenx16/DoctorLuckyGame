package controller;

import gameworld.WorldInterface;
import java.io.IOException;
import java.util.Random;

/**
 * Represents a Controller for Doctor Lucky: handle user moves by executing them
 * using the model; convey move outcomes to the user in some form.
 */

public interface ControllerInterface {
  /**
   * Execute a single game of Doctor Lucky given a Doctor Lucky Model. When the
   * game is over, the playGame method ends.
   *
   * @param world    the game world to control
   * @param maxTurns the maximum number of turns allowed in the game
   * @param random   the Random instance used for generating random values
   * @throws IOException if an input or output error occurs
   */
  void start(WorldInterface world, int maxTurns, Random random) throws IOException;

}
