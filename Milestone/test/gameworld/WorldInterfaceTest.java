package gameworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import coordinate.Coordinate;
import item.Item;
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
        + "- Revolver with 32 damage.\n" + "Players in room: No players in this room.\n"
        + "Neighboring rooms:\n" + "- 1 Billiard Room\n" + "- 3 Dining Hall\n"
        + "- 4 Drawing Room\n" + "\n" + "Target character is here: Doctor Lucky\n";

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
   * that the pet moves to a new room each time wanderPet() is called. //
   */
  @Test
  public void testWanderPet() {
    RoomInterface initialRoom = world.getPet().getCurrentRoom();
    RoomInterface initialRoomTarget = world.getTargetCharacter().getCurrentRoom();
    assertEquals(initialRoom, initialRoomTarget);
    assertNotNull(initialRoom);
    // System.out.println(initialRoom.getName());
    // First move
    world.wanderPet();
    RoomInterface firstMoveRoom = world.getPet().getCurrentRoom();
    assertNotNull(firstMoveRoom);
    assertEquals(initialRoom, firstMoveRoom);
    // System.out.println(firstMoveRoom.getName());
    // Second move
    world.wanderPet();
    RoomInterface secondMoveRoom = world.getPet().getCurrentRoom();
    assertNotNull(secondMoveRoom);
    assertNotEquals(firstMoveRoom, secondMoveRoom);
    // Continue moving until all rooms are visited
    while (world.getPetVisitedRooms().size() < world.getRooms().size() - 1) {
      world.wanderPet();
    }
    assertEquals(world.getRooms().size() - 1, world.getPetVisitedRooms().size());
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
    assertEquals((Integer) targetRoom.getRoomInd(), world.getPetVisitedRooms().iterator().next());
    world.wanderPet();
    world.wanderPet();
    world.wanderPet();
  }

  /**
   * Tests the attemptOnTarget method for a human player without specifying an
   * item. Verifies that the method automatically selects the best item or
   * defaults to a "poke" action.
   */
  @Test
  public void testAttemptOnTargetHumanPlayerNoItem() {
    world.addPlayer(playerH, 0);
    String result = world.attemptOnTarget(playerH, "");
    assertNotNull(result);
    assertTrue(
        result.contains("automatic attack activated") || result.contains("poked the target"));
  }

  /**
   * Tests the attemptOnTarget method for a human player who specifies an item.
   * Verifies that the correct item is used for the attack and the method behaves
   * as expected.
   */
  @Test
  public void testAttemptOnTargetHumanPlayerWithItem() {
    world.addPlayer(playerH, 0);
    world.getRooms().get(0).addItem(new Item(10, "Dagger"));
    playerH.pickUpItem(new Item(10, "Dagger"));

    String result = world.attemptOnTarget(playerH, "Dagger");
    assertNotNull(result);
    assertEquals(result, "PlayerH attacked the target with Dagger. Remaining target health: 40");
  }

  /**
   * Tests the attemptOnTarget method for a computer-controlled player. Verifies
   * that the method automatically selects the best item or defaults to a "poke"
   * action.
   */
  @Test
  public void testAttemptOnTargetComputerPlayer() {
    world.addPlayer(playerC, 0);
    String result = world.attemptOnTarget(playerC, null);
    assertNotNull(result);
    assertTrue(result.contains("poked the target"));
    playerC.pickUpItem(new Item(15, "Crossbow"));

    result = world.attemptOnTarget(playerC, null);
    assertNotNull(result);
    assertEquals(result,
        "PlayerC (AI) attacked the target with Crossbow.The item is now removed from play.\n"
            + "The target survived the attack. Remaining health: 34");
  }

  /**
   * Tests the setGameEnd and isGameEnd methods to ensure that the game end state
   * can be set and retrieved correctly.
   */
  @Test
  public void testSetAndGetGameEnd() {
    assertFalse(world.isGameEnd());
    world.setGameEnd(true);
    assertTrue(world.isGameEnd());
  }

  /**
   * Tests the getTargetLocationHint method when the target's last known room is
   * unknown. Verifies that the hint indicates no information is available.
   */
  @Test
  public void testGetTargetLocationHintNoLastRoom() {
    String hint = world.getTargetLocationHint();
    assertNotNull(hint);
    assertTrue(hint.contains("No information on the target’s last known location"));
  }

  /**
   * Tests the getTargetLocationHint method when the target is in a known room.
   * Verifies that the hint includes the current room information of the target.
   */
  @Test
  public void testGetTargetLocationHintKnownRoom() {
    String hint = world.getTargetLocationHint();
    // System.out.println(hint);
    assertTrue(hint.contains("No information on the target’s last known location."));
    world.addPlayer(playerC, 1);
    world.turnComputerPlayer();
    world.moveTargetCharacter();
    RoomInterface currentRoom = world.getTargetCharacter().getCurrentRoom();
    String hintAfter = world.getTargetLocationHint();
    // System.out.println(hintAfter);
    assertTrue(hintAfter.contains("\nThe target was last seen in Armory with index 0."));
    assertTrue(hintAfter.contains("\nATTENTION: Target character Doctor Lucky is here!!!"));
  }

  /**
   * Tests the room sealing logic by setting a room as sealed and unsealing it.
   * Verifies that the room's seal state is correctly set and retrieved.
   */
  @Test
  public void testRoomSealLogic() {
    RoomInterface room = world.getRooms().get(0);
    room.setSealed();
    assertTrue(room.isSealed());

    room.unseal();
    assertFalse(room.isSealed());
  }

  /**
   * Tests the behavior of the pet moving to a room when some rooms are sealed.
   * Verifies that the pet's movement and room seal status behave as expected.
   */
  @Test
  public void testPetMovementWithSealedRooms() {
    RoomInterface room = world.getRooms().get(1);
    room.setSealed();

    world.movePetTo(1);
    assertEquals(room, world.getPet().getCurrentRoom());
    assertTrue(room.isSealed());
  }

  /**
   * Tests the target location hint after multiple moves. Ensures that the target
   * location hint gets updated correctly after each move.
   */
  @Test
  public void testTargetLocationHintAfterMultipleMoves() {
    world.addPlayer(playerC, 0);
    world.moveTargetCharacter(); // Move target to next room
    String hint = world.getTargetLocationHint();
    assertTrue(hint.contains("The target was last seen in Armory with index 0"));

    world.moveTargetCharacter(); // Move again
    String secondHint = world.getTargetLocationHint();
    assertTrue(secondHint.contains("The target was last seen in Billiard Room with index 1"));
  }

  /**
   * Tests updating player turns after each action to ensure the correct player
   * gets the next turn.
   */
  @Test
  public void testTurnUpdateLogic() {
    world.addPlayer(playerH, 0);
    world.addPlayer(playerC, 1);

    // Human Player's turn
    PlayerInterface initialTurnPlayer = world.getTurn();
    assertEquals(playerH, initialTurnPlayer);

    // Human player looks around and ends turn
    world.turnHumanPlayer("look", -1, null);
    PlayerInterface nextTurnPlayer = world.getTurn();
    assertEquals(playerC, nextTurnPlayer);
  }

  /**
   * Tests an attempt on the target where the attack is seen by another player.
   */
  @Test
  public void testAttemptOnTargetSeenByOtherPlayers() {
    world.addPlayer(playerH, 0);
    world.addPlayer(playerC, 0); // Place both players in the same room

    // Attempt to attack the target
    String result = world.attemptOnTarget(playerH, "Dagger");
    assertNotNull(result);
    assertTrue(result.contains("attack was seen by another player and stopped"));
  }

  /**
   * Tests the game end condition when the target's health reaches zero.
   */
  @Test
  public void testGameEndWhenTargetIsKilled() {
    world.addPlayer(playerH, 0);
    ItemInterface dagger = new Item(50, "Dagger"); // High damage item to kill target
    world.getRooms().get(0).addItem(dagger);
    playerH.pickUpItem(dagger);

    String result = world.attemptOnTarget(playerH, "Dagger");
    // System.out.println(result);
    assertNotNull(result);
    assertEquals(result,
        "CONGRATULATIONS! " + "Game is over! PlayerH successfully killed the target with Dagger! "
            + "PlayerH wins the game!");
    assertTrue(world.isGameEnd());
  }

  /**
   * Tests neighbor calculation for a room located at the boundary of the world.
   */
  @Test
  public void testBoundaryRoomNeighborCalculation() {
    RoomInterface boundaryRoom = world.getRooms().get(0);
    List<RoomInterface> neighbors = world.getNeighbors(boundaryRoom);

    assertNotNull(neighbors);
    assertTrue(neighbors.size() > 0); // Should have at least one neighbor
  }

  /**
   * Test to ensure that the {@code getPixel()} method returns the correct default
   * pixel value.
   */
  @Test
  public void testGetPixelDefault() {
    int expectedPixel = 26; // Default value as defined in the World constructor
    int actualPixel = world.getPixel();
    assertEquals("The pixel value should match the default value.", expectedPixel, actualPixel);
  }

  /**
   * Test to verify that the pixel value remains constant after performing various
   * operations on the {@code World} object.
   * 
   * @throws IOException if cannot load the world
   */
  @Test
  public void testGetPixelAfterOperations() throws IOException {
    // Load a mock world file
    Readable mockWorldFile = new java.io.StringReader("5 5 TestWorld\n" + "100 Target\n"
        + "PetName\n" + "3\n" + "0 0 2 2 Room1\n" + "3 0 5 2 Room2\n" + "0 3 2 5 Room3\n" + "3\n"
        + "0 10 Sword\n" + "1 20 Shield\n" + "2 15 Potion\n");
    world.loadFromFile(mockWorldFile);

    // Perform various operations
    world.moveTargetCharacter();
    world.getRooms();
    world.getItems();
    world.getTargetCharacter();
    world.getPixel(); // Access the pixel value multiple times

    // Ensure the pixel value is still correct
    int expectedPixel = 26;
    int actualPixel = world.getPixel();
    assertEquals("The pixel value should remain constant after operations.", expectedPixel,
        actualPixel);
  }
}
