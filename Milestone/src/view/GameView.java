package view;

import gameworld.World;
import gameworld.WorldInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.RoomInterface;

/**
 * GameView class responsible for setting up the main game window, allowing the
 * user to add human or computer players to the game.
 */
public class GameView extends JFrame {

  private GamePanel gamePanel;
  private MenuPanel menuPanel;
  private InfoPanel infoPanel;
  private AboutPanel aboutPanel;
  private WorldInterface world;

  public GameView(WorldInterface world) {
    // Setting up the frame properties
    this.world = world;
    setTitle("Doctor Lucky Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());
    setMinimumSize(new Dimension(300, 300));

    // Initialize and add AboutPanel to the center
    aboutPanel = new AboutPanel();
    add(aboutPanel, BorderLayout.CENTER);

    // Register the action listener for the start game button
    aboutPanel.registerActionListener(e -> switchToGameView());
  }

  // Method to switch to the main game view
  private void switchToGameView() {
    getContentPane().removeAll();
    revalidate();
    repaint();

    // Initialize and add MenuPanel to the top
    menuPanel = new MenuPanel();
    add(menuPanel, BorderLayout.NORTH);

    // Registering the ActionListener for menu items
    menuPanel.registerActionListener(new MenuActionListener());

    // Initialize and add GamePanel to the center for the world map
    gamePanel = new GamePanel(world);
    JScrollPane scrollPane = new JScrollPane(gamePanel);
    scrollPane.setPreferredSize(new Dimension(800, 600));
    add(scrollPane, BorderLayout.CENTER);

    // Initialize and add InfoPanel to the bottom
    infoPanel = new InfoPanel();
    add(infoPanel, BorderLayout.SOUTH);

    revalidate();
    repaint();

    // After setting up the game panels, prompt the user to add players
    addPlayerDialog();
  }

  // Define the ActionListener for menu actions
  private class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "New Game with New World Specification":
          // Logic to start a new game with a new world specification
          System.out.println("Starting a new game with a new world specification...");
          // TODO: Add logic to prompt the user for a new world specification
          addPlayerDialog();
          break;

        case "New Game with Current World Specification":
          // Logic to start a new game with the current world specification
          System.out.println("Starting a new game with the current world specification...");
          world = new World(); // Reset the world
          addPlayerDialog();
          break;

        case "Quit":
          // Logic to quit the game
          System.out.println("Quitting game...");
          System.exit(0);
          break;
      }
    }
  }

  // Method to prompt the user for adding players
  private void addPlayerDialog() {
    Object[] options = { "Human Player", "Computer Player", "Done" };
    while (true) {
      int selection = JOptionPane.showOptionDialog(this, "Add a player to the game:", "Add Player",
          JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

      if (selection == 0) { // Human Player
        String playerName = JOptionPane.showInputDialog(this, "Enter Human Player Name:");
        if (playerName != null && !playerName.trim().isEmpty()) {
          RoomInterface startingRoom = selectRoomDialog();
          if (startingRoom != null) {
            int maxItems = world.getItems().size(); // Set max items to number of items in the world
            PlayerInterface humanPlayer = new HumanPlayer(playerName, startingRoom, maxItems);
            world.addPlayer(humanPlayer, world.getRooms().indexOf(startingRoom));
            System.out.println(
                "Added Human Player: " + humanPlayer.getName() + " in " + startingRoom.getName());
            gamePanel.repaint(); // Update the GamePanel with new player
          }
        }
      } else if (selection == 1) { // Computer Player
        String playerName = "Computer Player " + (world.getPlayers().size() + 1);
        RoomInterface startingRoom = selectRandomRoom();
        int maxItems = world.getItems().size(); // Set max items to number of items in the world
        PlayerInterface computerPlayer = new ComputerPlayer(playerName, startingRoom, maxItems);
        world.addPlayer(computerPlayer, world.getRooms().indexOf(startingRoom));
        System.out.println("Added Computer Player: " + computerPlayer.getName());
        gamePanel.repaint();
        ; // Update the GamePanel with new player
      } else {
        break; // Done adding players
      }
    }
  }

  // Helper method to select a room from the list of rooms
  private RoomInterface selectRoomDialog() {
    String[] roomNames = world.getRooms().stream().map(RoomInterface::getName)
        .toArray(String[]::new);
    String selectedRoomName = (String) JOptionPane.showInputDialog(this,
        "Select a starting room for the player:", "Select Room", JOptionPane.PLAIN_MESSAGE, null,
        roomNames, roomNames[0]);

    return world.getRooms().stream().filter(r -> r.getName().equals(selectedRoomName)).findFirst()
        .orElse(null);
  }

  // Helper method to select a random room
  private RoomInterface selectRandomRoom() {
    Random random = new Random();
    return world.getRooms().get(random.nextInt(world.getRooms().size()));
  }

}
