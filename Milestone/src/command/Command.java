package command;

import java.io.IOException;

/**
 * Represents a command that can be executed within the game. This interface is
 * used to implement the Command pattern for player actions, enabling
 * encapsulation of action logic for easier management and execution.
 */
public interface Command {
  /**
   * Executes the command. Each implementing class will define specific behavior
   * for this method based on the action it represents.
   *
   * @throws IOException if an input or output exception occurs during execution.
   */
  void execute() throws IOException;
}
