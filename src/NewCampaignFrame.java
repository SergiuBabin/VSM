import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewCampaignFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int COLUMN_WIDTH = 20;

    private JLabel id;
    private JLabel name;
    private JLabel description;
    private JLabel startDate;
    private JLabel endDate;
    private JLabel NoOfVouchers;
    private JLabel strategy;

    private JTextField campaignId;
    private JTextField campaignName;
    private JTextField campaignDescription;
    private JTextField campaignStartDate;
    private JTextField campaignEndDate;
    private JTextField campaignNoOfVouchers;
    private JTextField campaignStrategy;

    private JButton addButton;
    private JButton exit;
    private VMS vms;

    public NewCampaignFrame() {
        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        vms = VMS.getInstance();

        id = new JLabel("ID");
        name = new JLabel("Nume");
        description = new JLabel("Descriere");
        startDate = new JLabel("Data start");
        endDate = new JLabel("Data final");
        NoOfVouchers = new JLabel("Vouchere totale");
        strategy = new JLabel("Strategie");

        campaignId = new JTextField(COLUMN_WIDTH);
        campaignName = new JTextField(COLUMN_WIDTH);
        campaignDescription = new JTextField(COLUMN_WIDTH);
        campaignStartDate = new JTextField(COLUMN_WIDTH);
        campaignEndDate = new JTextField(COLUMN_WIDTH);
        campaignNoOfVouchers = new JTextField(COLUMN_WIDTH);
        campaignStrategy = new JTextField(COLUMN_WIDTH);

        addButton = new JButton("Add campaign");
        exit = new JButton("<- Back");

        addButton.addActionListener(this);
        exit.addActionListener(this);

        layoutComponents();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (clicked == addButton) {
            LocalDateTime localDateTime = LocalDateTime.now();

            Campaign newCampaign = new Campaign();

            int id = Integer.parseInt(campaignId.getText());

            if (id < vms.getCampaigns().size() + 1 || id > vms.getCampaigns().size() + 1) {
                JOptionPane.showMessageDialog(null,
                        "Campaign Id exists, please select another one, like " +
                                vms.getCampaigns().size() + 1,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            newCampaign.setID(id);

            newCampaign.setName(campaignName.getText());
            newCampaign.setDescription(campaignDescription.getText());
            newCampaign.setStartDate(LocalDateTime.parse(campaignStartDate.getText(), formatter));
            newCampaign.setEndDate(LocalDateTime.parse(campaignEndDate.getText(), formatter));

            if (Utils.validateDate(localDateTime, LocalDateTime.parse(campaignStartDate.getText(), formatter))) {
                newCampaign.setStatus(CampaignStatusType.NEW);
            } else if (Utils.validateDate(localDateTime, LocalDateTime.parse(campaignEndDate.getText(), formatter))){
                newCampaign.setStatus(CampaignStatusType.STARTED);
            } else {
                newCampaign.setStatus(CampaignStatusType.EXPIRED);
            }

            vms.addCampaign(newCampaign);

            JOptionPane.showMessageDialog(null,
                    "Campaign was added with succes!");

        }

        if (clicked == exit) {
            dispose();
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
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(name, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignName, gridBagConstraints);


        // Third Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(description, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignDescription, gridBagConstraints);


        // Fourth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(startDate, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignStartDate, gridBagConstraints);


        // Fifth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(endDate, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignEndDate, gridBagConstraints);


        // Sixth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(NoOfVouchers, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(campaignNoOfVouchers, gridBagConstraints);


        // Seventh Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 0, 0, 5);
        add(strategy, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        add(campaignStrategy, gridBagConstraints);


        // Eighth Line
        gridBagConstraints.gridy++;

        // First Column
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(addButton, gridBagConstraints);

        // Second Column
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(exit, gridBagConstraints);
    }
}
