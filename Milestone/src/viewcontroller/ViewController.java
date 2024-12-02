package viewcontroller;

import command.Command;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import player.ComputerPlayer;
import player.HumanPlayer;
import view.GameView;

//ViewController class using the command pattern to handle user interactions
public class ViewController {
  private GameView gameView;
  private List<HumanPlayer> players;
  private HumanPlayer currentPlayer;

  public ViewController(GameView gameView) {
    this.gameView = gameView;
    this.players = new ArrayList<>();
    initializePlayers();
    this.currentPlayer = players.get(0); // Start with the first player

    // Registering listeners
    gameView.registerActionListener(new MenuActionListener());
    gameView.registerMouseListener(new GameMouseListener());
    gameView.registerKeyListener(new GameKeyListener());
  }

  // Initialize players (both human and computer players)
  private void initializePlayers() {
    // Here we add some sample players, you can modify to prompt user input
    players.add(new HumanPlayer("Player 1"));
    players.add(new HumanPlayer("Player 2"));
    players.add(new ComputerPlayer("Computer 1"));
  }

  // ActionListener for menu actions
  private class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      switch (command) {
        case "New Game with New World Specification":
          // Logic to start a new game with a new world specification
          System.out.println("Starting a new game with a new world specification...");
          JOptionPane.showMessageDialog(gameView, "Please provide new world specification.");
          // TODO: Implement world specification input
          break;

        case "New Game with Current World Specification":
          // Logic to start a new game with the current world specification
          System.out.println("Starting a new game with the current world specification...");
          resetGame();
          break;

        case "Quit":
          // Logic to quit the game
          System.out.println("Quitting game...");
          System.exit(0);
          break;
      }
    }
  }

  // MouseListener for game interactions (e.g., moving players)
  private class GameMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
      // Get the coordinates of the mouse click and decide the action
      int x = e.getX();
      int y = e.getY();
      System.out.println("Mouse clicked at: (" + x + ", " + y + ")");

      // Example: Move the player based on where they clicked
      Command moveCommand = new MoveCommand(currentPlayer, "NORTH"); // Direction can be dynamic
      moveCommand.execute();
      gameView.updateGamePanel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
  }

  // KeyListener for keyboard interactions (e.g., picking items, attacking)
  private class GameKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
          // Player attempts to attack
          System.out.println(currentPlayer.getName() + " attempts to attack!");
          Command attackCommand = new AttackCommand(currentPlayer);
          attackCommand.execute();
          gameView.updateInfoPanel(currentPlayer.getName() + " made an attack!");
          break;

        case KeyEvent.VK_P:
          // Player picks up an item
          System.out.println(currentPlayer.getName() + " picks up an item.");
          currentPlayer.pickUpItem();
          gameView.updateInfoPanel(currentPlayer.getName() + " picked up an item.");
          break;

        case KeyEvent.VK_L:
          // Player looks around the world
          System.out.println(currentPlayer.getName() + " looks around.");
          currentPlayer.lookAround();
          gameView.updateInfoPanel(currentPlayer.getName() + " is looking around.");
          break;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
  }

  // Method to reset the game for a new session
  private void resetGame() {
    System.out.println("Resetting game...");
    // Reset all players and game state
    for (HumanPlayer player : players) {
      player.reset();
    }
    currentPlayer = players.get(0); // Start with the first player again
    gameView.updateGamePanel();
    gameView.updateInfoPanel("Game has been reset.");
  }
}
