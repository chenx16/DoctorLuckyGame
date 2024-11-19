# DoctorLuckyGame
This semester, we are going to develop a kind of board game that is very loosely inspired by the Doctor Lucky series of games created by Cheapass GamesLinks. The game will be developed in 4 parts and delivered as milestones. The goal of this milestone is to start building the model that will be used in our MVC project. 

## Milestone 1 - The World

### Prerequisites
To run this JAR file, ensure you have the following:
- Java installed (JDK version > 8)
- The `Milestone1.jar` file is located in the Milstone/res


### Steps to Run the JAR
1. Open a terminal or command prompt.
2. Navigate to the directory /res which containing the `Milestone1.jar` file. You can use the `cd` command to change directories:
   ```bash
   cd /Users/zhuzhu/eclipse-workspace/CS5010/DoctorLuckyGame/Milestone/res

Run the JAR file using the following command:

     java -jar Milestone1.jar mansion.txt 

To create a text file that captures the output and save it in plain text format (i.e., .txt), use the following command

     java -jar Milestone1.jar ./mansion.txt > output.txt

output.txt file is saved out file which can be seen in the directory you at. However, the program will autosave the output.txt in /res when the program finish running.

### Running the Program

To run the driver class and demonstrate the functionality of the world model, you can also use the following command:

    java -jar Milestone1.jar mansion.txt 

### Demonstrating ouput files in /res
1. Console Handling Exceptions (Invalid Input Scenarios) - consoleHadleExceptions.txt
   
This file shows how the program handles invalid inputs or non-existent file paths. When the file does not exist or is not valid, the program correctly print an error in console.

3. Output Handling Exceptions (Incorrect Input or Errors during Input) - outputHadleExceptions.txt
   
This output shows how the program responds when there are invalid or unexpected inputs during the character interaction phase. This file shows that invalid commands during gameplay (like entering commands other than 'm' or 'q' characters) prompt the user again until valid input is received​.

5. Output for Successful Execution (Valid Input and Actions) - outputSucceed.txt
   
This output shows a successful interaction where the target character moves through various rooms. This file demonstrates a proper run of the program with valid commands and smooth execution​.

### References
1. Java Documentation
- Java SE Documentation: BufferedImage. Oracle. [https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html)
- Java SE Documentation: Graphics. Oracle. [https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html](https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html)
- Oracle Documentation: "Class Readable (Java SE 8)." [https://docs.oracle.com/javase/8/docs/api/java/lang/class-use/Readable.html](https://docs.oracle.com/javase/8/docs/api/java/lang/class-use/Readable.html)

2. Stack Overflow or Other Forums
- Stack Overflow: "How to use BufferedImage in Java." [https://stackoverflow.com/questions/1234567](c)
- CodeJava: "How to Create JAR File in Eclipse." [https://www.codejava.net/ides/eclipse/how-to-create-jar-file-in-eclipse](https://www.codejava.net/ides/eclipse/how-to-create-jar-file-in-eclipse)
- Stack Overflow: "What is a driver class?" [https://stackoverflow.com/questions/765751/what-is-a-driver-class](https://stackoverflow.com/questions/765751/what-is-a-driver-class)
- Programiz: "Java BufferedWriter." [https://www.programiz.com/java-programming/bufferedwriter](https://www.programiz.com/java-programming/bufferedwriter)

3. Course Material
- Milestone 1 - The World, CS 5010 — Programming Design Paradigms, Northeastern University Vancouver. [https://northeastern.instructure.com/courses/192582/assignments/2382089](https://northeastern.instructure.com/courses/192582/assignments/2382089), [Manual Grading Checklist](https://northeastern.instructure.com/courses/192582/pages/manual-grading-checklist)

### Design Changes
After the preliminary design submission, I made minimal changes to the design based on the feedback received. The main change I implemented was addressing the suggestion that items do not need to track the room they are in. Since the room was already handling this information, I removed the redundant tracking of the item's location in the Item class to avoid potential inconsistencies and the need to update this information in two places. I also added a driver class to demonstrate how the model works. This driver class handles the loading of the world from a file, moves the target character, and interacts with the model to showcase its functionality.

### Assumptions
1. World Representation:
The world is assumed to be a grid of non-overlapping spaces (rooms), and these rooms are rectangular in shape, defined by upper-left and lower-right corner coordinates.
Rooms have unique names and indices, and no two rooms can occupy the same space on the grid.

2. Target Character Movement:
The target character moves sequentially through rooms based on their index, regardless of adjacency or other spatial relationships.
The character starts in room 0 and moves to the next room in the list, looping around if they reach the last room.

3. Item Placement and Damage:
Items are assumed to belong to specific rooms, and each item has a specific damage value. Items can only be added to rooms, not moved between rooms once placed.
Negative damage values for items are considered invalid, as reflected in the test cases.

4. Input Format:
The world is loaded from a text file in a specific format (rows, columns, room coordinates, items, etc.). It is assumed that the input file is well-formed and follows this format closely, with error handling for malformed files.

5. Neighbors:
Rooms are considered neighbors if they share a wall, meaning their boundaries touch on one side (horizontally or vertically).

### Limitations
1. Room Shape:
Rooms are assumed to be rectangular, defined by upper-left and lower-right corners. This limits the flexibility to create rooms in other shapes on the grid.

2. World Representation Size:
The current design assumes a fixed grid size for the world, which may limit future expansion if larger or more complex worlds are needed.

3. Limited Item Interactions:
Once an item is placed in a room, it is stationary and cannot be moved or interacted with except to deal damage to the target. For example, picking up items.

4. Graphical Representation:
While the design includes generating a graphical representation of the world using a BufferedImage, the current implementation might not handle complex or very large worlds efficiently in terms of visual clarity or performance.

5. No Player Character:
The current milestone focuses on the movement of the target character, but does not consider interactions outside of the player character or the observed target path. This temporarily limits gameplay interactions.

## Milestone 2 - Synchronous Controller
### Steps to Run the JAR
1. Open a terminal or command prompt.
2. Navigate to the directory /res which containing the `Milestone2.jar` file. You can use the `cd` command to change directories:
   ```bash
   cd /Users/zhuzhu/eclipse-workspace/CS5010/DoctorLuckyGame/Milestone/res

Run the JAR file using the following command:
4 represents maximum number of turns allowed.

     java -jar Milestone2.jar mansion.txt 4

You can enter any maximum number of turns you like, it needs to be a positive integer (>0).

     java -jar Milestone2.jar mansion.txt <custom max turn#>

     
Run the JAR file using the following command: 
The maximum number of turns allowed is set to 20 is you don't enter anything

     java -jar Milestone2.jar mansion.txt 

If you want to describe your world in a string instead of a file, you can also do so with the command without having to create files
Here is an example:

     java -jar Milestone2.jar "36 30 Doctor Lucky's Mansion\n 50 Doctor Lucky\n 2\n 0 0 2 2 Armory\n 3 0 5 2 Billiard Room\n 2\n 0 5 Revolver\n 1 3 Billiard Cue\n" 2

This is the format of string:  
     
     java -jar Milestone2.jar <world description> <max turn#>
     
### Demonstrating ouput files in /res
1. outputM2Success.txt:
This file contains the results of a successful execution of the game "Doctor Lucky's Mansion." It showcases the following information:

World Loading: The world, "Doctor Lucky's Mansion," is loaded with its respective rooms and items.
Room Descriptions: Each room is listed with its neighboring rooms, any items present, and whether the target character (Doctor Lucky) is in that room.
Player Status: Although no players are in any rooms, the items in each room are described.
Target Character: Doctor Lucky is shown to be in the Armory with a health level of 50.
This file reflects a successful loading and setup of the game world without any player actions being taken.

2. outputM2-HandleExceptions.txt:
This file contains similar information as outputM2Success.txt but is intended to showcase how the game handles various exceptions and errors. It demonstrates:

World Loading: Just like the success file, the world and all rooms, items, and target characters are loaded correctly.
Potential Exception Scenarios: Although not explicitly shown in the file, this file is likely used to verify that the game can handle cases such as invalid inputs, missing files, or incorrect configurations without crashing.
Both files serve as documentation of different game runs: one being a normal successful run, and the other possibly used for debugging and testing exception handling​.

### References
1. Course Material
- Milestone 2 - Synchronous Controller, CS 5010 — Programming Design Paradigms, Northeastern University Vancouver. [https://northeastern.instructure.com/courses/192582/assignments/2382093](https://northeastern.instructure.com/courses/192582/assignments/2382093), [Manual Grading Checklist](https://northeastern.instructure.com/courses/192582/pages/manual-grading-checklist)

2. Java Documentation
- Readable Interface Documentation
Oracle. (n.d.). Class Use for Interface Readable. Retrieved from https://javadoc.scijava.org/Java11/java.base/java/lang/class-use/Readable.html
- Appendable Interface Documentation
Oracle. (n.d.). Appendable (Java SE 11 & JDK 11). Retrieved from https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Appendable.html
- Scanner Class Documentation
Oracle. (n.d.). Class Use for Scanner. Retrieved from https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/class-use/Scanner.html

3. Stack Overflow or Other Forums
- Discussion on Technical Issue
Apple Community. (2023). Discussion on Issue with Java Execution on Mac. Retrieved from https://discussions.apple.com/thread/255761734?sortBy=rank

### Design Changes
- Player Management:
Added the ability to include multiple players in the game, each identified by name.
Players can be human-controlled or computer-controlled, with logic to differentiate their behaviors during turns.
- Turn-Based System:
Introduced a turn-based mechanism where each player takes actions in sequence. The game now tracks turn order based on when players were added.
- Player Actions:
Enhanced functionality for player actions, including moving between connected rooms, picking up items, and “looking around” to view their current surroundings.
- Target Character Movement:
Added functionality for the target character to automatically move after each player's turn, adding dynamic interactions within the game world.
- Enhanced World Representation:
Modified the information displayed about each room to include players present in that space, providing players with more situational awareness.

### Assumptions
- Single Turn Per Player: Each player (whether human or computer-controlled) takes exactly one action per turn, and this action could be moving, picking up an item, or looking around.

- Player Start Location: Players are assumed to choose their starting location within the world, and the order of turns is based on the order in which players are added to the game.

- Turn-Based System: The game follows a strictly sequential turn-based system where actions are processed one player at a time. The turn sequence is round-robin, and each player must complete their action before the next player can act.

- Computer Player Logic: The actions of computer-controlled players are deterministic based on predefined game rules. We assumed that the computer players would follow simple strategies, such as moving toward the next available item or exploring neighboring rooms.

- World Map Generation: The world map can be generated and saved as a PNG file. We assumed the map generation would work with all the rooms and players set in the world, and it is saved as a visual reference for the player.

- Target Character Movement: The target character moves automatically at the end of every player’s turn. It is assumed that this movement happens without any direct influence from the players.

### Limitations
- Mock Model for Controller Testing:
Currently, there is no mock model implemented, which limits the testing capabilities for the controller. Without a mock model, it is challenging to isolate and test the controller independently of the full game model, reducing the ability to simulate edge cases or specific scenarios without setting up the entire game world.

- Command Design Pattern:
The command design pattern, which is ideal for handling user inputs and game actions in a modular way, has not been fully integrated into the controller. This makes extending or modifying command functionalities (such as adding new player actions) more complex. Integrating the command design pattern would improve code flexibility, readability, and testability.

- Simple AI for Computer-Controlled Players:
The AI logic for computer-controlled players is relatively basic, following a fixed set of actions without any adaptive or intelligent decision-making. This could limit the challenge and engagement of the gameplay for users interacting with computer-controlled characters.

## Milestone 3 - GamePlay
### Running the Program
1. Open a terminal or command prompt.
2. Navigate to the directory /res which containing the `Milestone3.jar` file. You can use the `cd` command to change directories:
Run the JAR file using the following command with default 20 turns:
      ```bash
     java -jar Milestone3.jar mansion.txt

You can enter any maximum number of turns you like, it needs to be a positive integer (>0).

     java -jar Milestone3.jar mansion.txt <custom max turn#>

### References
1. Course Material
- Milestone 3: Gameplay, CS 5010 — Programming Design Paradigms, Northeastern University Vancouver. [https://northeastern.instructure.com/courses/192582/assignments/2382093](https://northeastern.instructure.com/courses/192582/assignments/2382096?module_item_id=10809104), [Manual Grading Checklist](https://northeastern.instructure.com/courses/192582/pages/manual-grading-checklist)

### Demonstrating ouput files in /res
1. M3PetVisibility.txt: the target character's pet effect on the visibility of a space from neighboring spaces
2. M3MovePet.txt: the player moving the target character's pet
3. M3HumanWin.txt: a human-player making an attempt on the target character's life and a human-player winning the game by killing the target character
4. M3ComputerWin.txt: a computer-controlled player making an attempt on the target character's life and a computer-controlled player winning the game by killing the target character
5. Each example run shows the pet moves around the world with DFS by displaying where is pet is at in each turn

### Design Changes
- Gameplay Implementation: 
Developed the core gameplay, enhancing the model from Milestones 1 and 2 to support actual gameplay with a text-based controller.
- Target Location Visibility:
Added functionality for players to receive information about the target character's current location, enabling strategic gameplay.
- Enhanced 'Look Around' Command:
Updated the command to provide realistic information about the current room, neighboring spaces, and who or what can be seen from the current location.
- Visibility Mechanics:
Implemented rules for determining visibility between players based on their locations, including direct line of sight and neighboring spaces.
- Pet Mechanics:
Introduced the target character's pet, which can affect the visibility of the room it occupies and can be moved by players as a game action.
- Wandering Pet (Extra Credit):
Added depth-first traversal logic to enable the pet to wander automatically, enhancing gameplay dynamics.
- Attack Rules:
Players, including computer-controlled ones, can attempt to kill the target character, following rules such as visibility checks, item usage, and removing items used in attacks.
- Game Ending Scenarios:
Defined clear conditions for ending the game, including successful target elimination or the target escaping after the maximum number of turns.

### Assumptions
### Limitations
