import javax.swing.*;

public class ExistingVouchersFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private VoucherTableModel voucherTableModel;
    private JTable vouchersTable;

    public ExistingVouchersFrame(User user, Campaign campaign) {
        super("Vouchere Existente");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        voucherTableModel = new VoucherTableModel(user, campaign);
        vouchersTable = new JTable(voucherTableModel);
        add(vouchersTable);

        add(new JScrollPane(vouchersTable));
    }

}
