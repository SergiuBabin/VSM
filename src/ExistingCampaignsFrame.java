import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExistingCampaignsFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private VMS vms;

    private CampaignTableModel campaignTableModel;
    private JTable campaignsTable;
    private JPopupMenu showDetails;
    private JMenuItem showItems;

    public ExistingCampaignsFrame(User user) {
        super("Existing campaigns");

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        vms = VMS.getInstance();
        campaignTableModel = new CampaignTableModel();
        campaignsTable = new JTable(campaignTableModel);

        showDetails = new JPopupMenu();
        showItems = new JMenuItem("Show details");

        showDetails.add(showItems);

        campaignsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = campaignsTable.rowAtPoint(e.getPoint());

                campaignsTable.getSelectionModel().setSelectionInterval(row, row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    showDetails.show(campaignsTable, e.getX(), e.getY());
                }
            }
        });

        showItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = campaignsTable.getSelectedRow() + 1;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new VoucherFrame(user, vms.getCampaign(id));
                    }
                });
            }
        });

        add(new JScrollPane(campaignsTable));
    }
}
