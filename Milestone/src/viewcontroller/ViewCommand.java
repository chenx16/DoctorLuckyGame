package viewcontroller;

/**
 * The {@code ViewCommand} interface defines the contract for commands that
 * encapsulate user actions in the game. Each command interacts with the game
 * world and performs specific actions, returning a result as a string.
 */
public interface ViewCommand {
  /**
   * Executes the command and returns the result of the action as a string.
   *
   * @return a string representing the result of the command execution.
   */
  String execute();

}
