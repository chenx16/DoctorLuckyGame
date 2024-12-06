package view;

import gameworld.World;
import gameworld.WorldInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import player.PlayerInterface;
import room.RoomInterface;
import viewcontroller.ViewController;

/**
 * GameView class responsible for setting up the main game window, allowing the
 * user to add human or computer players to the game.
 */
public class GameView extends JFrame {

  private GamePanel gamePanel;
  private MenuPanel menuPanel;
  private InfoPanel infoPanel;
  private AboutPanel aboutPanel;
  private AddPlayerPanel addPlayerPanel;
  private WorldInterface world;
  private ViewController controller;
  private String worldFilePath;

  public GameView(WorldInterface world, String worldFilePath) {
    // Setting up the frame properties
    this.world = world;
    this.worldFilePath = worldFilePath;
    setTitle("Doctor Lucky Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());
    setMinimumSize(new Dimension(300, 300));

    // Initialize and add AboutPanel to the center
    aboutPanel = new AboutPanel();
    add(aboutPanel, BorderLayout.CENTER);

    // Register the action listener for the start game button
//    aboutPanel.registerActionListener(e -> switchToGameView());
    // Register the action listener for the start game button
    aboutPanel.registerActionListener(e -> switchToAddPlayerPanel());
  }

  private void switchToAddPlayerPanel() {
    getContentPane().removeAll();
    revalidate();
    repaint();

    addPlayerPanel = new AddPlayerPanel(world);
    add(addPlayerPanel, BorderLayout.CENTER);

    addPlayerPanel.addPropertyChangeListener("doneAddingPlayers", (evt) -> switchToGameView());

    revalidate();
    repaint();
  }

//
//  public void setController(ViewController controller) {
//    this.controller = controller;
//  }

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

    // Register mouse listener AFTER all components are initialized
//    if (controller != null) {
//      controller.registerMouseListener(gamePanel);
//    }

    revalidate();
    repaint();
    controller = new ViewController(this, world, 50);
//    if (controller != null) {
////      controller.registerMouseListener(gamePanel);
//      controller.registerKeyListener(gamePanel);
//    }
    // After setting up the game panels, prompt the user to add players
  }

  public void registerMouseListener(MouseListener listener) {
    gamePanel.addMouseListener(listener);
  }

//  public void registerKeyListener(KeyListener listener) {
//    addKeyListener(listener);
//  }

  public PlayerInterface getPlayerAtLocation(Point point) {
    for (PlayerInterface player : world.getPlayers()) {
      Rectangle playerBounds = gamePanel.getPlayerBounds(player); // You need to implement
                                                                  // getPlayerBounds() in GamePanel
      if (playerBounds.contains(point)) {
        return player;
      }
    }
    return null;
  }

  public RoomInterface getRoomAtLocation(Point clickPoint) {
    for (RoomInterface room : world.getRooms()) {
      Rectangle roomBounds = gamePanel.getRoomBounds(room);
      if (roomBounds.contains(clickPoint)) { // Assuming rooms have a graphical boundary
        return room;
      }
    }
    return null;
  }

  public int promptForRoom() {
    String[] roomNames = world.getRooms().stream().map(RoomInterface::getName)
        .toArray(String[]::new);
    String selectedRoomName = (String) JOptionPane.showInputDialog(this, "Select a room:",
        "Choose Room", JOptionPane.PLAIN_MESSAGE, null, roomNames, roomNames[0]);

    return world.getRooms().stream().filter(r -> r.getName().equals(selectedRoomName)).findFirst()
        .map(RoomInterface::getRoomInd).orElse(-1);
  }

  public String promptForItem() {
    PlayerInterface currentPlayer = world.getTurn();
    RoomInterface currentRoom = currentPlayer.getCurrentRoom();
    // Combine item name and damage
    String[] items = currentRoom.getItems().stream()
        .map(item -> String.format("%s (Damage: %d)", item.getName(), item.getDamage()))
        .toArray(String[]::new);

    if (items.length == 0) {
      JOptionPane.showMessageDialog(this, "No items available in the current room.", "No Items",
          JOptionPane.WARNING_MESSAGE);
      return null;
    }

    String selectedItem = (String) JOptionPane.showInputDialog(this, "Select an item to pick up:",
        "Choose Item", JOptionPane.PLAIN_MESSAGE, null, items, items[0]);

    // Extract the item name (removing the damage part)
    if (selectedItem != null) {
      return selectedItem.split(" \\(Damage:")[0];
    }
    return null;
  }

  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public String promptForInventoryItem() {
    PlayerInterface currentPlayer = world.getTurn();
    // Combine item name and damage
    String[] inventoryItems = currentPlayer.getInventory().stream()
        .map(item -> String.format("%s (Damage: %d)", item.getName(), item.getDamage()))
        .toArray(String[]::new);

    if (inventoryItems.length == 0) {
      JOptionPane.showMessageDialog(this, "Your inventory is empty!", "No Items",
          JOptionPane.WARNING_MESSAGE);
      return null;
    }

    String selectedItem = (String) JOptionPane.showInputDialog(this,
        "Select an item to attack with:", "Inventory", JOptionPane.PLAIN_MESSAGE, null,
        inventoryItems, inventoryItems[0]);

    // Extract the item name (removing the damage part)
    if (selectedItem != null) {
      return selectedItem.split(" \\(Damage:")[0];
    }
    return null;
  }

//Method to show an error message to the user
  public void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

//Method to get the InfoPanel
  public InfoPanel getInfoPanel() {
    return infoPanel;
  }

  // Method to update the turn information
  public void updateTurnInfo(int turnNum) {
    PlayerInterface currentPlayer = world.getTurn();
    String playerName = currentPlayer.getName();
    String roomName = currentPlayer.getCurrentRoom().getName();
    infoPanel.updateTurnInfo(turnNum, playerName, roomName);
    // Repaint the view to reflect updates
    repaint();
  }

  private class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "New Game with New World Specification":
          // Logic to start a new game with a new world specification
          System.out.println("Starting a new game with a new world specification...");
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setDialogTitle("Select a World Specification File");

          int userSelection = fileChooser.showOpenDialog(GameView.this);
          if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
              worldFilePath = selectedFile.getAbsolutePath();
              Readable worldFile = new FileReader(selectedFile);
              world = new World();
              world.loadFromFile(worldFile);
              System.out.println("Loaded world from: " + selectedFile.getName());

              // Show the player adding panel.
              switchToAddPlayerPanel();
            } catch (IOException ioException) {
              JOptionPane.showMessageDialog(GameView.this,
                  "Failed to load the world specification.", "Error", JOptionPane.ERROR_MESSAGE);
            }
          } else {
            JOptionPane.showMessageDialog(GameView.this, "World specification cannot be empty.",
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
          }
          break;

        case "New Game with Current World Specification":
          // Logic to start a new game with the current world specification
          System.out.println("Starting a new game with the current world specification...");
          world = new World(); // Reset the world
          try {
            Readable worldFile = new FileReader(worldFilePath);
            world.loadFromFile(worldFile);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
          switchToAddPlayerPanel();
          break;

        case "Quit":
          // Logic to quit the game
          System.out.println("Quitting game...");
          System.exit(0);
          break;
      }
    }
  }

  public GamePanel getGamePanel() {
    // TODO Auto-generated method stub
    return gamePanel;
  }

}
