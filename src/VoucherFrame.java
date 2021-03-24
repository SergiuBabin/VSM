import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoucherFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JButton showExistingVouchers;
    private JButton generateVoucher;
    private JButton redeemVoucher;
    private JButton exit;
    private Campaign campaign;
    private User user;

    public VoucherFrame(User user, Campaign campaign) {
        super("Campaign " + campaign.getName() + " vouchers");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.user = user;
        this.campaign = campaign;

        showExistingVouchers = new JButton("Show vouchers");

        if (user.isAdmin()) {
            generateVoucher = new JButton("Generate voucher");
            redeemVoucher =  new JButton("Redeem voucher");

            generateVoucher.addActionListener(this);
            redeemVoucher.addActionListener(this);
        }

        showExistingVouchers.addActionListener(this);

        exit = new JButton("<- Back");
        exit.addActionListener(this);

        layoutComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == showExistingVouchers) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ExistingVouchersFrame(user, campaign);
                }
            });
        }

        if (clicked == generateVoucher) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GenerateVoucherFrame(campaign);
                }
            });
        }

        if (clicked == redeemVoucher) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new RedeemVoucherFrame(campaign);
                }
            });
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
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 5);
        add(showExistingVouchers, gridBagConstraints);

        if (user.isAdmin()) {
            // Second Column
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 5, 0, 0);
            add(generateVoucher, gridBagConstraints);


            // Second Line
            gridBagConstraints.gridy = 1;

            // First Column
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.insets = new Insets(0, 0, 0, 5);
            add(redeemVoucher, gridBagConstraints);
        }

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        add(exit, gridBagConstraints);
    }
}
