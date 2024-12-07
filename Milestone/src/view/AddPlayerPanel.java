package view;

import gameworld.WorldInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * The {@code AddPlayerPanel} class provides a UI for adding human and computer
 * players to the game with enhanced visuals for buttons and alignment.
 */
public class AddPlayerPanel extends JPanel {
  private WorldInterface world;
  private JButton addHumanButton;
  private JButton addComputerButton;
  private JButton doneButton;
  private JLabel statusLabel;
  private JTextArea infoTextArea;

  /**
   * Constructs an {@code AddPlayerPanel} to manage player addition in the game.
   *
   * @param world the game world model.
   */
  public AddPlayerPanel(WorldInterface world) {
    this.world = world;
    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(20, 20, 20, 20));

    // Create buttons for adding players
    Dimension buttonSize = new Dimension(200, 50);
    addHumanButton = createStyledButton("Add Human Player", buttonSize);
    addComputerButton = createStyledButton("Add Computer Player", buttonSize);
    doneButton = createStyledButton("Done Adding Players", buttonSize);
    doneButton.setBackground(new Color(144, 238, 144)); // Light green
    doneButton.setOpaque(true);
    doneButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Create a status label to show the number of players added
    statusLabel = new JLabel("Players Added: 0");
    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

    // Create a panel for buttons and align them in a column
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(addHumanButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(addComputerButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(doneButton);
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

    // Create an info text area for displaying additional information
    infoTextArea = new JTextArea(5, 20);
    infoTextArea.setEditable(false);
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
    infoTextArea.setText("Use the buttons above to add players to the game.");
    infoTextArea.setBorder(BorderFactory.createTitledBorder("Information"));

    // Add components to the main panel
    add(statusLabel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);
    add(infoTextArea, BorderLayout.SOUTH);

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
   * Creates a styled JButton with a specified size and bold font.
   *
   * @param text       the text to display on the button.
   * @param buttonSize the preferred size of the button.
   * @return the styled JButton.
   */
  private JButton createStyledButton(String text, Dimension buttonSize) {
    JButton button = new JButton(text);
    button.setPreferredSize(buttonSize);
    button.setMaximumSize(buttonSize);
    button.setFont(new Font("SansSerif", Font.BOLD, 16)); // Bold font
    button.setAlignmentX(CENTER_ALIGNMENT);
    return button;
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
        updateInfoText("Human Player " + playerName + " added to the game.");
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
    updateInfoText(playerName + " added to the game.");
  }

  /**
   * Updates the information text area.
   *
   * @param message the message to display.
   */
  private void updateInfoText(String message) {
    infoTextArea.append("\n" + message);
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
