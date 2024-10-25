# DoctorLuckyGame
This semester, we are going to develop a kind of board game that is very loosely inspired by the Doctor Lucky series of gamesLinks to an external site. created by Cheapass GamesLinks to an external site.. The game will be developed in 4 parts and delivered as milestones. The goal of this milestone is to start building the model that will be used in our MVC project. 

## Milestone 1 - Running the JAR

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

## Running the Program

To run the driver class and demonstrate the functionality of the world model, you can also use the following command:

    java -jar Milestone1.jar mansion.txt 

### demonstrating ouput files in /res
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

## Milestone 2
### Steps to Run the JAR
1. Open a terminal or command prompt.
2. Navigate to the directory /res which containing the `Milestone1.jar` file. You can use the `cd` command to change directories:
   ```bash
   cd /Users/zhuzhu/eclipse-workspace/CS5010/DoctorLuckyGame/Milestone/res

Run the JAR file using the following command:

     java -jar Milestone2.jar mansion.txt 
### demonstrating ouput files in /res
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

