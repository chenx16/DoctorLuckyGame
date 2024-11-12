package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import coordinate.Coordinate;
import item.ItemInterface;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.PlayerInterface;
import room.Room;
import room.RoomInterface;
import target.TargetInterface;

/**
 * Unit tests for the World class. These tests verify that the world loads
 * correctly, generates neighbors, and handles items.
 */
public class WorldInterfaceTest {

  private WorldInterface world;
  private WorldInterface worldSame;
  private WorldInterface worldDiff;
  private final String localDir = "./res/";
  private PlayerInterface playerH;
  private PlayerInterface playerC;
  private RoomInterface room1;
  private RoomInterface room2;

  /**
   * Sets up the test environment by creating a new instance of the world. Load
   * correct file into the world.
   * 
   */
  @Before
  public void setUp() throws IOException {

    world = new World();
    worldSame = new World();
    worldDiff = new World();
    // Assuming the test world file is correctly structured for testing
    File worldFile = new File(localDir + "mansion.txt");
    FileReader fileReader1 = new FileReader(worldFile);
    world.loadFromFile(fileReader1);

    FileReader fileReader2 = new FileReader(worldFile);
    worldSame.loadFromFile(fileReader2);

    playerH = new HumanPlayer("PlayerH", room1, 5);
    playerC = new ComputerPlayer("PlayerC", room2, 5);
  }

  /**
   * Tests loading a valid world from a StringReader (simulated file input).
   * Verifies that rooms, items, and the target character are loaded correctly.
   */
  @Test
  public void testLoadFromString() throws IOException {
    String worldData = "36 30 Doctor Lucky's Mansion\n" + "50 Doctor Lucky\n" + "Fortune the Cat\n"
        + "2\n" + "0 0 2 2 Armory\n" + "3 0 5 2 Billiard Room\n" + "2\n"// 2 rooms
        + "0 5 Revolver\n" + "1 3 Billiard Cue\n"; // 2 items

    StringReader reader = new StringReader(worldData);
    worldDiff.loadFromFile(reader);

    // Assertions to verify the world is loaded correctly
    assertEquals("Doctor Lucky's Mansion", world.getName());
    assertEquals(2, worldDiff.getRooms().size());

    assertEquals("Fortune the Cat", worldDiff.getPet().getName());

    RoomInterface armory = worldDiff.getRooms().get(0);
    assertEquals("Armory", armory.getName());
    assertEquals(1, armory.getItems().size());

    ItemInterface revolver = armory.getItems().get(0);
    assertEquals("Revolver", revolver.getName());
    assertEquals(5, revolver.getDamage());

    // Test target character
    TargetInterface target = worldDiff.getTargetCharacter();
    assertEquals("Doctor Lucky", target.getName());
    assertEquals(50, target.getHealth());
  }

  /**
   * Tests loading invalid world data to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadInvalidWorldData() throws IOException {
    String invalidWorldData = "Invalid data\n50 Doctor Lucky\n";
    StringReader reader = new StringReader(invalidWorldData);
    world.loadFromFile(reader);
  }

  /**
   * Tests loading null data to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadNull() throws IOException {
    world.loadFromFile(null);
  }

  /**
   * Tests loading invalid file to ensure that proper exceptions are thrown.
   */
  @Test(expected = IOException.class)
  public void testLoadInvalidFile() throws IOException {
    File worldFile = new File(localDir + "output.txt");
    FileReader fileReader = new FileReader(worldFile);
    world.loadFromFile(fileReader);
  }

  /**
   * Tests loading a valid world file and checks that rooms, items, and the target
   * are loaded correctly.
   */
  @Test
  public void testLoadFromFileValid() throws IOException {
    assertNotNull(world.getRooms());
    assertNotNull(world.getItems());
    assertNotNull(world.getTargetCharacter());
    assertEquals(36, world.getRowAndCol()[0][0]);
    assertEquals(30, world.getRowAndCol()[0][1]);
    assertEquals("Doctor Lucky's Mansion", world.getName());
    assertEquals("Fortune the Cat", world.getPet().getName());
  }

  /**
   * Tests retrieving neighbors for a room.
   */
  @Test
  public void testGetNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory
    List<RoomInterface> neighbors = world.getNeighbors(armory);
    assertNotNull(neighbors);
    assertTrue(neighbors.size() > 0); // Should have some neighbors
    assertTrue(neighbors.contains(world.getRooms().get(3))); // Dining Hall
    assertTrue(neighbors.contains(world.getRooms().get(4))); // Drawing Room
  }

  /**
   * Tests that an IllegalArgumentException is thrown when a null room is passed
   * to getNeighbors().
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighborsInValid() {
    world.getNeighbors(null);
  }

  /**
   * Tests getSpaceInfo when the room has items and neighbors.
   */
  @Test
  public void testGetSpaceInfoWithItemsAndNeighbors() {
    RoomInterface armory = world.getRooms().get(0); // Room 0 is Armory

    String expectedOutput = "Room: Armory\n" + "Items in this room:\n"
        + "- Item Revolver with 3 damage.\n" + "Players in room: No players in this room.\n"
        + "Neighboring rooms:\n" + "- 1 Billiard Room\n" + "- 3 Dining Hall\n"
        + "- 4 Drawing Room\n\n" + "Target character is here: Doctor Lucky\n";

    String actualOutput = world.getSpaceInfo(armory);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no items but has neighbors.
   */
  @Test
  public void testGetSpaceInfoNoItems() {
    RoomInterface dining = world.getRooms().get(5); // Room 5 is Foyer without items

    String expectedOutput = "Room: Foyer\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "Neighboring rooms:\n"
        + "- 4 Drawing Room\n" + "- 15 Piazza\n\n";

    String actualOutput = world.getSpaceInfo(dining);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests getSpaceInfo when the room has no items or neighbors.
   */
  @Test
  public void testGetSpaceInfoNoNeighbors() {
    RoomInterface newRoom = new Room(new Coordinate(3, 0), new Coordinate(5, 2), "New Room", 1,
        new ArrayList<ItemInterface>(), new ArrayList<RoomInterface>());

    String expectedOutput = "Room: New Room\n" + "No items in this room.\n"
        + "Players in room: No players in this room.\n" + "This room has no neighboring rooms.\n";

    String actualOutput = world.getSpaceInfo(newRoom);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Tests if the getSpaceInfo check a null input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetSpaceInfoInValid() {
    world.getSpaceInfo(null);
  }

  /**
   * Tests moving the target character.
   */
  @Test
  public void testMoveTargetCharacter() {
    RoomInterface initialRoom = world.getTargetCharacter().getCurrentRoom();
    world.moveTargetCharacter();
    RoomInterface secondRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(initialRoom, secondRoom);
    world.moveTargetCharacter();
    RoomInterface thirdRoom = world.getTargetCharacter().getCurrentRoom();
    assertNotEquals(thirdRoom, secondRoom);
  }

  /**
   * Tests that getGraphics returns not null when a graphics returns correctly.
   */
  @Test
  public void testGetGraphics() throws IOException {
    Graphics graphics = world.getGraphics(localDir);

    // Ensure graphics is not null (meaning the world map was generated correctly)
    assertNotNull(graphics);
  }

  /**
   * Tests that generateWorldMap correctly generates and saves the image file.
   */
  @Test
  public void testGenerateWorldMapValidPath() throws IOException {
    // Generate the world map with a valid path
    BufferedImage image = world.generateWorldMap(localDir);

    // Assert the image is not null and the file is created
    assertNotNull(image);

    // Check if the file is created and exists
    String filePath = localDir + "worldmap.png";
    File file = new File(filePath);
    assertTrue(file.exists());

    // Check if the file is non-empty (i.e., it contains data)
    assertTrue(file.length() > 0);
  }

  /**
   * Tests if two targets are equal using hash code.
   */
  @Test
  public void testEqualsAndHashCode() {

    // Test equals
    assertTrue(world.equals(world));
    assertTrue(world.equals(worldSame));
    assertFalse(world.equals(worldDiff));
    assertFalse(world.equals(null));
    assertFalse(world.equals("Invalid"));

    // Test hashCode
    assertEquals(world.hashCode(), worldSame.hashCode());
  }

  /**
   * Verifies that a player is added to the world and placed in the correct room.
   * It checks that the player is in the world and in the room's list of players.
   */
  @Test
  public void testAddPlayer() {
    world.addPlayer(playerH, 0);
    List<PlayerInterface> players = world.getPlayers();
    PlayerInterface testPlayer = players.get(0);
    List<RoomInterface> rooms = world.getRooms();
    RoomInterface currRoom = rooms.get(0);
    assertEquals(1, players.size());
    assertTrue(players.contains(playerH));
    assertEquals(testPlayer.getCurrentRoom(), currRoom);
    assertTrue(players.contains(playerH));
    assertTrue(currRoom.getPlayers().contains(playerH));
  }

  /**
   * Ensures that adding a player with an invalid room index throws an
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerInvalidRoom() {
    world.addPlayer(playerH, -1); // Invalid room index
  }

  /**
   * Verifies that the getPlayers() method returns the correct list of players
   * currently in the world.
   */
  @Test
  public void testGetPlayers() {
    world.addPlayer(playerH, 0);
    world.addPlayer(playerC, 1);
    List<PlayerInterface> players = world.getPlayers();
    assertEquals(2, players.size());
    assertTrue(players.contains(playerH));
    assertTrue(players.contains(playerC));
  }

  /**
   * gets the player whose turn it is.
   */
  @Test
  public void testGetTurn() {

    world.addPlayer(playerH, 0);
    world.addPlayer(playerC, 1);

    // Player 1 should be the first to take a turn
    assertEquals(playerH, world.getTurn());

    // Move to the next player's turn
    world.turnHumanPlayer("look", 0, "xxx");
    assertEquals(playerC, world.getTurn());

    // Move to the next player's turn
    world.turnComputerPlayer();
    assertEquals(playerH, world.getTurn());
  }

  /**
   * Tests if a human player can move to another room.
   */
  @Test
  public void testTurnHumanPlayerMove() {
    world.addPlayer(playerH, 0);
    world.turnHumanPlayer("move", 1, null);
    assertEquals(world.getRooms().get(1), playerH.getCurrentRoom());
  }

  /**
   * Tests if a human player can look around.
   */
  @Test
  public void testTurnHumanPlayerLook() {
    world.addPlayer(playerH, 0);
    world.turnHumanPlayer("look", -1, null);
    assertEquals(world.getRooms().get(0), playerH.getCurrentRoom());
  }

  /**
   * Tests if a human player can pickup.
   */
  @Test
  public void testTurnHumanPlayerPickUp() {
    world.addPlayer(playerH, 1);

    world.turnHumanPlayer("pickup", -1, "Billiard Cue");
    assertEquals(1, playerH.getInventory().size());
    assertEquals("Billiard Cue", playerH.getInventory().get(0).getName());
  }

  /**
   * Tests if a human player can't pickup.
   */
  @Test
  public void testTurnHumanPlayerPickUpWrong() {
    world.addPlayer(playerH, 1);

    world.turnHumanPlayer("pickup", -1, "Sword");
    assertEquals(0, playerH.getInventory().size());
  }

  /**
   * Tests if the computer player can automatically take its turn.
   */
  @Test
  public void testTurnComputerPlayer() {
    world.addPlayer(playerC, 1);
    world.turnComputerPlayer();
  }

  /**
   * Tests the pet's wandering behavior in the world using wanderPet(). Verifies
   * that the pet moves to a new room each time wanderPet() is called.
   */
  @Test
  public void testWanderPet() {
    RoomInterface initialRoom = world.getPet().getCurrentRoom();
    RoomInterface initialRoomTarget = world.getTargetCharacter().getCurrentRoom();
    assertEquals(initialRoom, initialRoomTarget);
    assertNotNull(initialRoom);
    System.out.println(initialRoom.getName());
    // First move
    world.wanderPet();
    RoomInterface firstMoveRoom = world.getPet().getCurrentRoom();
    assertNotNull(firstMoveRoom);
    System.out.println(firstMoveRoom.getName());
//    assertEquals(initialRoom, firstMoveRoom);
    // Second move
    world.wanderPet();
    RoomInterface secondMoveRoom = world.getPet().getCurrentRoom();
    assertNotNull(secondMoveRoom);
    assertNotEquals(firstMoveRoom, secondMoveRoom);
    // Continue moving until all rooms are visited
    while (world.getPetVisitedRooms().size() < world.getRooms().size()) {
      world.wanderPet();
    }
    assertEquals(world.getRooms().size(), world.getPetVisitedRooms().size());
    // System.out.println();
    // world.wanderPet();
  }

  /**
   * Tests moving the pet to a specific room using movePetTo(). Verifies that the
   * pet is correctly moved and the DFS traversal is reset.
   */
  @Test
  public void testMovePetTo() {
    RoomInterface targetRoom = world.getRooms().get(1); // Assume room 1 exists
    world.movePetTo(1);
    assertEquals(targetRoom, world.getPet().getCurrentRoom());

    // Ensure DFS is reset and starts from the new room
    assertTrue(world.getPetVisitedRooms().isEmpty());
    // Call wanderPet() to verify that DFS starts from the new room
    world.wanderPet();
    assertFalse(world.getPetVisitedRooms().isEmpty());
    assertEquals(targetRoom, world.getPetVisitedRooms().iterator().next());
    world.wanderPet();
    world.wanderPet();
    world.wanderPet();
  }
}
