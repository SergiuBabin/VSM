import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.module.FindException;

public class InputFileFrame extends JFrame implements ActionListener {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JLabel label;
    private JTextField textField;
    private JButton continueButton;
    private JButton exitButton;

    public InputFileFrame() {
        super("Load File");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label = new JLabel("Enter the file number:");
        textField = new JTextField(20);
        continueButton = new JButton("Continue");
        exitButton = new JButton("Exit");

        continueButton.addActionListener(this);
        exitButton.addActionListener(this);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == continueButton) {
            try {
                int testNo = Integer.parseInt(textField.getText());

                if (testNo < 0 || testNo > 9) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid number, number must be in range [0-9]",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                VMS vms = VMS.getInstance();

                Test.runTest(Integer.toString(testNo));

                dispose();

                SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    public void run() {
                        new LoginFrame();
                    }}
                );
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null,
                        "It's not number, please try again",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (clicked == exitButton) {
            int action = JOptionPane.showConfirmDialog(null,
                    "You want to leave the application ?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);

            if (action == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        // First Line
        gridBagConstraints.gridy = 0;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 5);
        add(label, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(textField, gridBagConstraints);

        // Second Line
        gridBagConstraints.gridy = 1;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(continueButton, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(exitButton, gridBagConstraints);
    }
}
