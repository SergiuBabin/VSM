import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CampaignFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JButton showExistingCampaigns;
    private JButton addCampaign;
    private JButton editCampaign;
    private JButton cancelCampaign;
    private JButton showNotification;
    private JButton exit;
    private User user;

    public CampaignFrame(User user) {
        super("Voucher Management Service");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.user = user;
        showExistingCampaigns = new JButton("Show all campaigns");
        exit = new JButton("Exit");

        if (user.isAdmin()) {
            addCampaign = new JButton("Add Campaign");
            editCampaign = new JButton("Edit Campaign");
            cancelCampaign = new JButton("Cancel Campaign");

            addCampaign.addActionListener(this);
            editCampaign.addActionListener(this);
            cancelCampaign.addActionListener(this);
        } else {
            showNotification = new JButton("Show notification");

            showNotification.addActionListener(this);
        }

        showExistingCampaigns.addActionListener(this);
        exit.addActionListener(this);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == showExistingCampaigns) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ExistingCampaignsFrame(user);
                }
            });
        }

        if (clicked == addCampaign) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new NewCampaignFrame();
                }
            });
        }

        if (clicked == editCampaign) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new EditCampaignFrame();
                }
            });
        }

        if (clicked == cancelCampaign) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new CancelCampaignFrame();
                }
            });
        }

        if (clicked == showNotification) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new NotificationFrame(user);
                }
            });
        }

        if (clicked == exit) {
            int action = JOptionPane.showConfirmDialog(null,
                    "Vrei sa parasesti aplicatia?", "Confirma iesirea",
                    JOptionPane.OK_CANCEL_OPTION);

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
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(showExistingCampaigns, gridBagConstraints);

        if (user.isAdmin()) {
            // Second Column
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(addCampaign, gridBagConstraints);

            // Second Line
            gridBagConstraints.gridy = 1;

            // First Column
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(editCampaign, gridBagConstraints);

            // Second Column
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(cancelCampaign, gridBagConstraints);

            // Third Line
            gridBagConstraints.gridy = 2;

            // Second Column
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(exit, gridBagConstraints);
        } else {

            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(showNotification, gridBagConstraints);

            // Second Line
            gridBagConstraints.gridy = 2;

            // Second Column
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            add(exit, gridBagConstraints);

        }
    }
}
