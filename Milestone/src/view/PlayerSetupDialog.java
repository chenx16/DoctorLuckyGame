package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PlayerSetupDialog extends JDialog {
  private JComboBox<String> playerTypeComboBox;
  private JTextField roomInputField;
  private JButton addButton;
  private String selectedPlayerType;
  private String selectedRoom;

  public PlayerSetupDialog(JFrame parent) {
    super(parent, "Add Player", true);
    setLayout(new GridLayout(3, 2));
    setSize(300, 150);

    // Player type dropdown
    add(new JLabel("Select Player Type:"));
    playerTypeComboBox = new JComboBox<>(new String[] { "Human", "Computer" });
    add(playerTypeComboBox);

    // Room input for human players
    add(new JLabel("Enter Room (if Human):"));
    roomInputField = new JTextField();
    add(roomInputField);

    // Add button
    addButton = new JButton("Add Player");
    add(addButton);

    // Handle add button click
    addButton.addActionListener(e -> {
      selectedPlayerType = (String) playerTypeComboBox.getSelectedItem();
      selectedRoom = roomInputField.getText();
      dispose();
    });
  }

  public String getSelectedPlayerType() {
    return selectedPlayerType;
  }

  public String getSelectedRoom() {
    return selectedRoom;
  }
}
