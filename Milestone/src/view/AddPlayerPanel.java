package view;

import gameworld.WorldInterface;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * The {@code AddPlayerPanel} class provides a UI for adding human and computer
 * players to the game.
 */
public class AddPlayerPanel extends JPanel {
  private WorldInterface world;
  private JButton addHumanButton;
  private JButton addComputerButton;
  private JButton doneButton;
  private JLabel statusLabel;

  /**
   * Constructs an {@code AddPlayerPanel} to manage player addition in the game.
   *
   * @param world the game world model.
   */
  public AddPlayerPanel(WorldInterface world) {
    this.world = world;
    setLayout(new BorderLayout());

    // Create buttons for adding players
    addHumanButton = new JButton("Add Human Player");
    addComputerButton = new JButton("Add Computer Player");
    doneButton = new JButton("Done Adding Players");

    // Create a status label to show the number of players added
    statusLabel = new JLabel("Players Added: 0");
    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // Create a panel for buttons
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(addHumanButton);
    buttonPanel.add(addComputerButton);
    buttonPanel.add(doneButton);

    // Add components to the main panel
    add(statusLabel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);

    // Set up button actions
    addHumanButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addHumanPlayer();
      }
    });

    addComputerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addComputerPlayer();
      }
    });

    doneButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (world.getPlayers().size() > 0) {
          firePropertyChange("doneAddingPlayers", false, true);
        } else {
          JOptionPane.showMessageDialog(AddPlayerPanel.this,
              "You need to add at least one player to start the game.", "No Players Added",
              JOptionPane.WARNING_MESSAGE);
        }
      }
    });
  }

  /**
   * Adds a human player to the game.
   */
  public void addHumanPlayer() {
    if (world.getPlayers().size() >= 10) {
      JOptionPane.showMessageDialog(this, "Cannot add more than 10 players to the game.",
          "Player Limit Reached", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String playerName = JOptionPane.showInputDialog(this, "Enter Human Player Name:");
    if (playerName != null && !playerName.trim().isEmpty()) {
      RoomInterface startingRoom = selectRoomDialog();
      if (startingRoom != null) {
        int maxItems = world.getItems().size();
        PlayerInterface humanPlayer = new HumanPlayer(playerName, startingRoom, maxItems);
        world.addPlayer(humanPlayer, world.getRooms().indexOf(startingRoom));
        updateStatus();
      }
    }
  }

  /**
   * Adds a computer-controlled player to the game.
   */
  public void addComputerPlayer() {
    if (world.getPlayers().size() >= 10) {
      JOptionPane.showMessageDialog(this, "Cannot add more than 10 players to the game.",
          "Player Limit Reached", JOptionPane.WARNING_MESSAGE);
      return;
    }

    String playerName = "Computer Player " + (world.getPlayers().size() + 1);
    RoomInterface startingRoom = selectRandomRoom();
    int maxItems = world.getItems().size();
    PlayerInterface computerPlayer = new ComputerPlayer(playerName, startingRoom, maxItems);
    world.addPlayer(computerPlayer, world.getRooms().indexOf(startingRoom));
    updateStatus();
  }

  /**
   * Opens a dialog to select a starting room for a player.
   *
   * @return the selected {@code RoomInterface}, or {@code null} if no selection
   *         was made.
   */
  public RoomInterface selectRoomDialog() {
    String[] roomNames = world.getRooms().stream().map(RoomInterface::getName)
        .toArray(String[]::new);
    String selectedRoomName = (String) JOptionPane.showInputDialog(this,
        "Select a starting room for the player:", "Select Room", JOptionPane.PLAIN_MESSAGE, null,
        roomNames, roomNames[0]);

    return world.getRooms().stream().filter(r -> r.getName().equals(selectedRoomName)).findFirst()
        .orElse(null);
  }

  /**
   * Selects a random starting room for a player.
   *
   * @return the randomly selected {@code RoomInterface}.
   */
  public RoomInterface selectRandomRoom() {
    Random random = new Random();
    return world.getRooms().get(random.nextInt(world.getRooms().size()));
  }

  /**
   * Updates the status label to reflect the number of players added.
   */
  public void updateStatus() {
    statusLabel.setText("Players Added: " + world.getPlayers().size());
  }
}
