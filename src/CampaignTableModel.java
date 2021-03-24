import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CampaignTableModel extends AbstractTableModel {

    private static final int COLUMNS = 6;

    private VMS vms;
    private ArrayList<String> colomnNames;

    public CampaignTableModel(){
        vms = VMS.getInstance();

        colomnNames = new ArrayList<>(COLUMNS);

        colomnNames.add("ID");
        colomnNames.add("Name");
        colomnNames.add("Description");
        colomnNames.add("Start date");
        colomnNames.add("End date");
        colomnNames.add("Status");
    }

    @Override
    public int getRowCount() {
        return vms.getCampaigns().size();
    }

    @Override
    public int getColumnCount() {
        return colomnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Campaign campaign = vms.getCampaign(rowIndex + 1);

        switch (columnIndex + 1) {
            case 1:
                return campaign.getID();
            case 2:
                return campaign.getName();
            case 3:
                return campaign.getDescription();
            case 4:
                return campaign.getStartDate();
            case 5:
                return campaign.getEndDate();
            case 6:
                return campaign.getStatus();
        }

        return null;
    }
    @Override
    public String getColumnName(int column) {
        return colomnNames.get(column);
    }
}
