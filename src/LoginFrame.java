import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JLabel labelUserName;
    private JLabel labelPassword;
    private JTextField textFieldUserName;
    private JTextField textFieldPassword;
    private JButton continueButton;
    private JButton exitButton;
    private VMS vms;

    public LoginFrame() {
        super("Login");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        vms = VMS.getInstance();
        labelUserName = new JLabel("User Name:");
        labelPassword = new JLabel("Password:");

        textFieldUserName = new JTextField(20);
        textFieldPassword = new JTextField(20);

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
            String userName = textFieldUserName.getText();
            String password = textFieldPassword.getText();

            for (User user : vms.users) {
                if (user.getName().equals(userName)) {
                    if (user.getPassword().equals(password)) {
                        dispose();

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new CampaignFrame(user);
                            }
                        });
                        return;
                    }
                    JOptionPane.showMessageDialog(null,
                            "Invalid User name or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,
                    "Invalid User name or password",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (clicked == exitButton){
            int action = JOptionPane.showConfirmDialog(null,
                    "You want to leave the application ?",
                    "Confirm exit", JOptionPane.OK_CANCEL_OPTION);

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
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(labelUserName, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(textFieldUserName, gridBagConstraints);

        // Second Line
        gridBagConstraints.gridy = 1;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(labelPassword, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(textFieldPassword, gridBagConstraints);

        // Third Line
        gridBagConstraints.gridy = 2;

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
