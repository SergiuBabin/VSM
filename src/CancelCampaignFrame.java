import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelCampaignFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int COLUMN_WIDTH = 15;

    private JLabel id;
    private JTextField campaignId;
    private JButton cancelButton;
    private JButton exit;
    private VMS vms;

    public CancelCampaignFrame() {
        super("CancelCampaign");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        vms = VMS.getInstance();
        id = new JLabel("ID");
        campaignId = new JTextField(COLUMN_WIDTH);
        cancelButton = new JButton("Cancel");
        exit = new JButton("<- Back");

        cancelButton.addActionListener(this);
        exit.addActionListener(this);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == cancelButton) {
            int id = Integer.parseInt(campaignId.getText());

            if (id < vms.getCampaigns().size() + 1 || id > vms.getCampaigns().size() + 1) {
                JOptionPane.showMessageDialog(null,
                        "Campaign Id exists, please select another one, like " +
                                (vms.getCampaigns().size() + 1),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vms.getCampaign(id).isExpired() || vms.getCampaign(id).isCancelled()) {
                JOptionPane.showMessageDialog(null,
                        "Campaign with id = " + id + " is not started or is cancelled",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            vms.cancelCampaign(id);

            JOptionPane.showMessageDialog(null,
                    "Campaign was canceled with succes!");
        }
    }

    public void layoutComponents() {
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
        add(id, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignId, gridBagConstraints);


        // Second Line
        gridBagConstraints.gridy = 2;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(cancelButton, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(exit, gridBagConstraints);
    }
}
