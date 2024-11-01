package command;

import gameworld.WorldInterface;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import room.RoomInterface;

/**
 * A command that allows the player to move to a neighboring room. This command
 * prompts the player to enter a neighboring room index and moves them to the
 * selected room if the index is valid.
 */
public class MoveCommand implements Command {
  private WorldInterface world;
  private Appendable out;
  private Scanner scanner;

  /**
   * Constructs a MoveCommand with the specified world, player, output stream, and
   * scanner.
   *
   * @param world   the game world in which the player is interacting.
   * @param out     the output stream to which move command results will be
   *                appended.
   * @param scanner the input source from which the player's room selection is
   *                read.
   */
  public MoveCommand(WorldInterface world, Appendable out, Scanner scanner) {
    this.world = world;
    this.out = out;
    this.scanner = scanner;
  }

  @Override
  public void execute() throws IOException {
    List<RoomInterface> neighbors = this.world.getTurn().getCurrentRoom().getListofNeighbors();

    // Display neighboring rooms
    out.append("Neighboring rooms:\n");
    for (RoomInterface neighbor : neighbors) {
      out.append(neighbor.getRoomInd() + ": " + neighbor.getName() + "\n");
    }

    boolean validMove = false;
    while (!validMove) {
      out.append("Enter the room index to move to: ");
      int roomIndex;
      try {
        roomIndex = Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        out.append("Invalid input. Please enter a valid room index.\n");
        continue; // Skip to next iteration
      }

      RoomInterface targetRoom = neighbors.stream().filter(room -> room.getRoomInd() == roomIndex)
          .findFirst().orElse(null);

      if (targetRoom != null) {
        String result = world.turnHumanPlayer("move", targetRoom.getRoomInd(), null);
        out.append(result).append("\n");
        validMove = true; // Valid move ends the loop
      } else {
        out.append("Invalid move. Please select a neighboring room in list.\n");
      }
    }
  }

}
