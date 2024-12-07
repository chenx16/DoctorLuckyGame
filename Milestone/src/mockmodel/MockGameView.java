package mockmodel;

import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import player.PlayerInterface;
import room.RoomInterface;
import view.GameView;
import view.GameViewInterface;

public class MockGameView extends GameView implements GameViewInterface {
  private StringBuilder log;

  public MockGameView(StringBuilder log) {
    super(null, "", 0);
    this.log = log;
  }

  @Override
  public void showMessage(String message) {
    log.append("showMessage called with: ").append(message).append("\n");
  }

  @Override
  public void showErrorMessage(String errorMessage) {
    log.append("showErrorMessage called with: ").append(errorMessage).append("\n");
  }

  @Override
  public int promptForRoom() {
    log.append("promptForRoom called\n");
    return 0; // Mock response
  }

  @Override
  public String promptForItem() {
    log.append("promptForItem called\n");
    return "Mock Item";
  }

  @Override
  public String promptForInventoryItem() {
    log.append("promptForInventoryItem called\n");
    return "Mock Inventory Item";
  }

  @Override
  public void updateTurnInfo(int turnNum) {
    log.append("updateTurnInfo called with turnNum: ").append(turnNum).append("\n");
  }

  @Override
  public PlayerInterface getPlayerAtLocation(Point point) {
    log.append("getPlayerAtLocation called with point: ").append(point).append("\n");
    return null; // Mock response
  }

  @Override
  public RoomInterface getRoomAtLocation(Point clickPoint) {
    log.append("getRoomAtLocation called with clickPoint: ").append(clickPoint).append("\n");
    return null; // Mock response
  }

  @Override
  public void registerListeners(KeyListener keyListener, MouseAdapter mouseAdapter) {
    log.append("registerListeners called\n");
  }
}
