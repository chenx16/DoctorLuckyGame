package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class MenuPanel extends JPanel {
  private JMenuBar menuBar;
  private JMenu gameMenu;
  private JMenuItem newGameItem;
  private JMenuItem currentGameItem;
  private JMenuItem quitItem;

  public MenuPanel() {
    setLayout(new FlowLayout(FlowLayout.LEFT));
    menuBar = new JMenuBar();

    // Create Game Menu
    gameMenu = new JMenu("Game");

    // Menu items for different actions
    newGameItem = new JMenuItem("New Game with New World Specification");
    currentGameItem = new JMenuItem("New Game with Current World Specification");
    quitItem = new JMenuItem("Quit");

    // Add menu items to Game Menu
    gameMenu.add(newGameItem);
    gameMenu.add(currentGameItem);
    gameMenu.addSeparator();
    gameMenu.add(quitItem);

    // Add Game Menu to Menu Bar
    menuBar.add(gameMenu);

    // Add Menu Bar to Panel
    add(menuBar);
  }

  public void registerActionListener(ActionListener listener) {
    newGameItem.addActionListener(listener);
    currentGameItem.addActionListener(listener);
    quitItem.addActionListener(listener);
  }
}
