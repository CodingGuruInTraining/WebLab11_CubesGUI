import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by hl4350hb on 4/19/2017.
 */
public class CubesGUI extends JFrame {
    private JPanel rootPanel;
    private JButton addButton;
    private JButton updateButton;
    private JTextField botTextField;
    private JTextField timeTextField;
    private JList botList;
    private JButton deleteButton;
    private JButton confirmButton;
    private DefaultListModel<Bot> listModel;

    private byte option;

    CubesGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        confirmButton.setVisible(false);

// TODO add that list model stuff.
        listModel = new DefaultListModel<Bot>();
        botList.setModel(listModel);
        botList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CubesDB dbManager = new CubesDB();

        for (Bot b : dbManager.allBots) {
// TODO add bot object to list model thing.
            listModel.addElement(b);
        }





        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = botTextField.getText();
                if (newName == "" || newName == null) {
// TODO display a message somehow.
                    JOptionPane.showConfirmDialog(rootPanel, "Please enter a name.",
                            "Need Name", JOptionPane.OK_OPTION);
                    return;
                }

                double newTime = Input.checkDoubleInput(timeTextField.getText());
                if (newTime == 0) {
// TODO display a message somehow.
                    JOptionPane.showConfirmDialog(rootPanel, "Please enter a time",
                            "Need Time", JOptionPane.OK_OPTION);
                    return;
                }

                dbManager.setTempName(newName);
                dbManager.setTempTime(newTime);

                option = 1;
                confirmButton.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                confirmButton.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                confirmButton.setVisible(true);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (option) {
                    case 1:     // Add
                        String newName = dbManager.getTempName();
                        double newTime = dbManager.getTempTime();

                        try {
                            dbManager.psInsert.setString(1, newName);
                            dbManager.psInsert.setDouble(2, newTime);
                            dbManager.psInsert.executeUpdate();
                        }
                        catch (SQLException err) {
                            System.out.println("There was an error adding to the database.");
                            err.printStackTrace();
                        }



                        break;
                    case 2:     // Update

                        break;
                    case 3:     // Delete

                        break;
                }

                botTextField.setText("");
                timeTextField.setText("");
                option = 0;
                confirmButton.setVisible(false);
            }
        });
    }
}