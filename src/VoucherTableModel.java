import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class VoucherTableModel extends AbstractTableModel {
    private static final int COLUMNS = 6;

    private ArrayList<String> columnNames;
    private Campaign campaign;
    private User user;

    public VoucherTableModel(User user, Campaign campaign) {
        columnNames = new ArrayList<>(COLUMNS);

        columnNames.add("ID");
        columnNames.add("Status");
        columnNames.add("E-Mail");
        columnNames.add("Valoare");
        columnNames.add("ID Campanie");
        columnNames.add("Data Utilizarii");

        this.user = user;
        this.campaign = campaign;
    }

    @Override
    public int getRowCount() {
        int result = 0;

        Set<Map.Entry<String, Collection<Voucher>>> entries =
                campaign.getDisturbVoucher().entrySet();

        for (Map.Entry<String, Collection<Voucher>> entry : entries) {
            if (!(user.isAdmin()) && entry.getKey().equals(user.getEmail())) {
                result += entry.getValue().size();
            } else if (user.isAdmin()) {
                result += entry.getValue().size();
            }
        }

        return result;
    }

    @Override
    public int getColumnCount() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Voucher voucher = campaign.getVoucher(rowIndex + 1);

        switch (columnIndex + 1) {
            case 1:
                return voucher.getVoucherID();
            case 2:
                return voucher.getType();
            case 3:
                return voucher.getEmail();
            case 4:
                if (voucher.isGiftVoucher()) {
                    GiftVoucher giftVoucher = (GiftVoucher) voucher;
                    return giftVoucher.getMaxSum();
                } else {
                    LoyalityVoucher loyaltyVoucher = (LoyalityVoucher) voucher;
                    return loyaltyVoucher.getDiscount();
                }
            case 5:
                return voucher.getCampaignID();
            case 6:
                return voucher.getDate();
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }
}
