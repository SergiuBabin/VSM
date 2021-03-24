import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class RedeemVoucherFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int COLUMN_WIDTH = 15;

    private JLabel id;
    private JTextField idField;
    private JButton redeemButton;
    private JButton exitButton;
    private Campaign campaign;
    private VMS vms;

    public RedeemVoucherFrame(Campaign campaign) {
        super("Voucher marking");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        vms = VMS.getInstance();
        this.campaign = campaign;

        id = new JLabel("Voucher ID");
        idField = new JTextField(COLUMN_WIDTH);

        redeemButton = new JButton("Mark");
        exitButton = new JButton("<- Back");

        redeemButton.addActionListener(this);
        exitButton.addActionListener(this);

        redeemButton.setMnemonic(KeyEvent.VK_M);
        exitButton.setMnemonic(KeyEvent.VK_I);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == redeemButton) {
            if (campaign.isExpired() || campaign.isCancelled()) {
                JOptionPane.showMessageDialog(null,
                        "Campaign is not disponible!", "Error", JOptionPane.OK_OPTION);
                return;
            }

            int id = Integer.parseInt(idField.getText());

            if (campaign.getVoucher(id) == null) {
                JOptionPane.showMessageDialog(null,
                        "Voucher does not exist!",
                        "Error", JOptionPane.OK_OPTION);
                return;
            }

            if (!(Utils.validateDate(LocalDateTime.now(),
                    campaign.getEndDate()))) {
                JOptionPane.showMessageDialog(null,
                        "Campaign is not disponible!",
                        "Error", JOptionPane.OK_OPTION);
                vms.getCampaign(campaign.getID())
                        .setStatus(CampaignStatusType.EXPIRED);
                return;
            }

            vms.getCampaign(campaign.getID()).redeemVoucher(id, LocalDateTime.now());

            JOptionPane.showMessageDialog(null,
                    "Voucher was marked with succes!");
        }

        if (clicked == exitButton) {
            dispose();
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
        add(id, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(idField, gridBagConstraints);


        // Second Line
        gridBagConstraints.gridy = 1;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(redeemButton, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(exitButton, gridBagConstraints);
    }
}
