package viewcontroller;

import gameworld.WorldInterface;
import player.PlayerInterface;
import view.GameView;

public class ViewController {

  private GameView gameView;
  private WorldInterface world;

  public ViewController(GameView gameView, WorldInterface world) {
    this.gameView = gameView;
    this.world = world;
    this.gameView.setController(this); // Set this controller in the GameView
  }

  public void startGame(int maxTurns) {
    // Ensure players are added before starting the game
    if (world.getPlayers().isEmpty()) {
      throw new IllegalStateException("No players added to the world.");
    }

    // Game loop
    int turnCount = 0;

    while (turnCount < maxTurns) {
      PlayerInterface currentPlayer = world.getTurn();

      // Update the GameView with current player's turn info
      gameView.updateTurnInfo(currentPlayer);

      // Rest of your game logic...

      turnCount++;
      world.moveTargetCharacter(); // Automatically move the target character
      world.wanderPet();
    }

    // End of the game, update game view
//    gameView.updateTurnInfo(
//        "Game over! Unfortunately, the maximum number of turns is reached.\nThe target character escapes and runs away to live another day.\nNobody wins...");
  }
}
