import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateVoucherFrame extends JFrame implements ActionListener {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int COLUMN_WIDTH = 20;

    private JLabel id;
    private JLabel campaignId;
    private JLabel mail;
    private JLabel voucherType;
    private JLabel value;
    private TextField mailField;
    private TextField voucherTypeField;
    private TextField valueField;
    private JButton generate;
    private JButton exit;

    private Campaign campaign;
    private VMS vms;

    public GenerateVoucherFrame(Campaign campaign) {
        super("Generate voucher");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        vms = VMS.getInstance();
        this.campaign = campaign;

        id = new JLabel("Campaign ID:");
        campaignId = new JLabel(campaign.getID().toString());
        mail = new JLabel("E-mail:");
        voucherType = new JLabel("Voucher type (GiftVoucher / LoyaltyVoucher):");
        value = new JLabel("Value:");

        mailField = new TextField(COLUMN_WIDTH);
        voucherTypeField = new TextField(COLUMN_WIDTH);
        valueField = new TextField(COLUMN_WIDTH);

        generate = new JButton("Generate");
        exit = new JButton("<- Back");

        generate.addActionListener(this);
        exit.addActionListener(this);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == generate) {
            if (campaign.hasMoreVouchers()) {
                String eMail = mailField.getText();
                String type = voucherTypeField.getText();

                if (!(type.equals("GiftVoucher") && !(type.equals("LoyaltyVoucher")))) {
                    JOptionPane.showMessageDialog(null,
                            "Incorect type of voucher",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float value = Float.parseFloat(valueField.getText());

                Voucher voucher = vms.getCampaign(campaign.getID())
                        .generateVoucher(eMail, type, value);

                vms.getUsermail(eMail).getReceivedVouchers().addVoucher(voucher);
                vms.getCampaign(campaign.getID()).getDisturbVoucher()
                        .addVoucher(voucher);
                vms.getCampaign(campaign.getID()).addObserver(vms.getUsermail(eMail));

                JOptionPane.showMessageDialog(null,
                        "Voucher was added with succes!");
            }
        }

        if (clicked == exit) {
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
        add(campaignId, gridBagConstraints);

        // Second Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(mail, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(mailField, gridBagConstraints);

        // Third Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(voucherType, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(voucherTypeField, gridBagConstraints);

        // Fourth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(value, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(valueField, gridBagConstraints);

        // Fifth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(generate, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(exit, gridBagConstraints);
    }

}
