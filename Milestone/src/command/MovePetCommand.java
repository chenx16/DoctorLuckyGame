package command;

import gameworld.WorldInterface;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import room.RoomInterface;

/**
 * Represents a command to move the pet to a specific room in the game world.
 */
public class MovePetCommand implements Command {
  private WorldInterface world;
  private Appendable out;
  private Scanner scanner;

  /**
   * Constructs a MovePetCommand with the specified world, output, and scanner for
   * user input.
   *
   * @param world   the game world where the pet will be moved
   * @param out     the output to append messages to
   * @param scanner the scanner for user input
   */
  public MovePetCommand(WorldInterface world, Appendable out, Scanner scanner) {
    this.world = world;
    this.out = out;
    this.scanner = scanner;
  }

  /**
   * Executes the command to move the pet to the specified room.
   */
  @Override
  public void execute() {
    try {
      List<RoomInterface> rooms = world.getRooms();
      out.append("Select a room to move the pet to:\n");
      for (int i = 0; i < rooms.size(); i++) {
        out.append(i + ": " + rooms.get(i).getName() + "\n");
      }

      int roomIndex = -1;
      while (roomIndex < 0 || roomIndex >= rooms.size()) {
        out.append("Enter the room number: ");
        try {
          roomIndex = Integer.parseInt(scanner.nextLine().trim());
          if (roomIndex < 0 || roomIndex >= rooms.size()) {
            out.append("Invalid room number. Please try again.\n");
          }
        } catch (NumberFormatException e) {
          out.append("Invalid input. Please enter a valid room number.\n");
        }
      }
      String result = world.turnHumanPlayer("movepet", roomIndex, null);
      out.append(result).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while appending output.", e);
    }
  }
}