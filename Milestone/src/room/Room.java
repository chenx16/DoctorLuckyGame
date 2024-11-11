package room;

import coordinate.CoordinateInterface;
import item.ItemInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import player.PlayerInterface;

/**
 * Represents a room in the game world. A room has coordinates, a name, and can
 * contain items. Rooms can also have neighbors, which are other rooms directly
 * adjacent. The room also keeps track of players in it.
 */
public class Room implements RoomInterface {

  private CoordinateInterface upperLeft;
  private CoordinateInterface lowerRight;
  private String name;
  private List<ItemInterface> items;
  private List<RoomInterface> neighbors;
  private List<PlayerInterface> players;
  private int roomInd;
  private boolean sealed;

  /**
   * Creates a new room with specified coordinates and name.
   *
   * @param upperLeft  the upper-left coordinate of the room
   * @param lowerRight the lower-right coordinate of the room
   * @param name       the name of the room
   * @param roomInd    the index of the room
   * @param items      the list of items in the room
   * @param neighbors  the list of neighbors of the room
   */
  public Room(CoordinateInterface upperLeft, CoordinateInterface lowerRight, String name,
      int roomInd, List<ItemInterface> items, List<RoomInterface> neighbors) {
    this.upperLeft = upperLeft;
    this.lowerRight = lowerRight;
    this.name = name;
    this.roomInd = roomInd;
    this.items = new ArrayList<ItemInterface>();
    this.neighbors = new ArrayList<RoomInterface>();
    this.players = new ArrayList<>();
    this.sealed = false;
  }

  @Override
  public void addItem(ItemInterface item) {
    items.add(item);
  }

  @Override
  public void removeItem(ItemInterface item) {
    items.remove(item);
  }

  @Override
  public List<ItemInterface> getItems() {
    return new ArrayList<>(items);
  }

  @Override
  public void addPlayer(PlayerInterface player) {
    players.add(player);
  }

  @Override
  public void removePlayer(PlayerInterface player) {
    players.remove(player);
  }

  @Override
  public List<PlayerInterface> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public int getRoomInd() {
    return roomInd;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void addNeighbor(RoomInterface room) {
    neighbors.add(room);
  }

  @Override
  public List<RoomInterface> getListofNeighbors() {
    return new ArrayList<>(neighbors);
  }

  @Override
  public CoordinateInterface getCoordinateUpperLeft() {
    return upperLeft.copy();
  }

  @Override
  public CoordinateInterface getCoordinateLowerRight() {
    return lowerRight.copy();
  }

  @Override
  public String getRoomDescription() {
    // Collect room information
    StringBuilder description = new StringBuilder("Room: " + name + "\n");

    // Add information about the items in the room
    if (items.isEmpty()) {
      description.append("No items in this room.\n");
    } else {
      description.append("Items in this room:\n");
      for (ItemInterface item : items) {
        description.append("- ").append(item.toString()).append("\n");
      }
    }

    // Add player information to the room info
    description.append("Players in room: ");
    if (players.isEmpty()) {
      description.append("No players in this room.");
    } else {
      for (PlayerInterface player : players) {
        description.append(player.getName()).append(" ");
      }
    }
    description.append("\n");

    // Add information about neighboring rooms
    if (neighbors.isEmpty()) {
      description.append("This room has no neighboring rooms.\n");
    } else {
      description.append("Neighboring rooms:\n");
      for (RoomInterface neighbor : neighbors) {
        description.append("- ").append(neighbor.getRoomInd() + " ").append(neighbor.getName())
            .append("\n");
      }
      description.append("\n");
    }
    return description.toString();
  }

  @Override
  public String getRoomDescriptionVisible() {
    // Collect room information
    StringBuilder description = new StringBuilder("You are in: " + name + ".\n");

    // Add information about the items in the room
    if (items.isEmpty()) {
      description.append("No items in this room.\n");
    } else {
      description.append("Items here:\n");
      for (ItemInterface item : items) {
        description.append("- ").append(item.toString()).append("\n");
      }
    }

    // Add player information to the room info
    description.append("Players here: ");
    if (players.isEmpty()) {
      description.append("No players in this room.");
    } else {
      for (PlayerInterface player : players) {
        description.append(player.getName()).append(" ");
      }
    }
    description.append("\n");

    // Seal the room when a player looks around
    this.setSealed();

    // Display visible neighboring spaces
    description.append("\nVisible neighboring spaces:\n");
    for (RoomInterface neighbor : this.getVisibleNeighbors()) {
      description.append("- ").append(neighbor.getRoomInd() + " ").append(neighbor.getName())
          .append("\n");

      description.append("Players: ");
      if (neighbor.getPlayers().isEmpty()) {
        description.append("No players.\n");
      } else {
        for (PlayerInterface p : neighbor.getPlayers()) {
          description.append(p.getName()).append(" ");
        }
        description.append("\n");
      }

      description.append("Items: ");
      if (neighbor.getItems().isEmpty()) {
        description.append("No items.\n");
      } else {
        for (ItemInterface item : neighbor.getItems()) {
          description.append("- ").append(item.toString()).append("\n");
        }
      }
      description.append("\n");
    }

    return description.toString();
  }

  @Override
  public List<RoomInterface> getVisibleNeighbors() {
    List<RoomInterface> visibleNeighbors = new ArrayList<>();
    for (RoomInterface neighbor : neighbors) {
      if (!neighbor.isSealed()) {
        visibleNeighbors.add(neighbor);
      }
    }
    return visibleNeighbors;
  }

  @Override
  public void setSealed() {
    this.sealed = true;
  }

  @Override
  public void unseal() {
    this.sealed = false;
  }

  @Override
  public boolean isSealed() {
    return sealed;
  }

  @Override
  public String toString() {
    return "Room{name=" + name + ", upperLeft=" + upperLeft + ", lowerRight=" + lowerRight + '}';
  }

  /**
   * Generates a hash code for this room. The hash code is based on the room's
   * coordinates and name.
   *
   * @return the hash code for the room
   */
  @Override
  public int hashCode() {
    return Objects.hash(upperLeft, lowerRight, name, roomInd);
  }

  /**
   * Checks if this room is equal to another room. Two rooms are considered equal
   * if they have the same coordinates and the same name.
   *
   * @param obj the object to compare with
   * @return true if the rooms are having same hashCode, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Room)) {
      return false;
    }
    Room other = (Room) obj;
    return this.hashCode() == other.hashCode();
  }

}
