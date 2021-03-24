import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class StrategyC implements Strategy {

    @Override
    public Voucher execute(Campaign campaign) {
        if (campaign.isExpired() || campaign.isCancelled() ||
                !(campaign.hasMoreVouchers())) {
            return null;
        }

        String eMailAddress = "";
        String type = "GiftVoucher";

        int minimumNo = Integer.MAX_VALUE;
        float value = 100f;

        Set<Map.Entry<String, Collection<Voucher>>> entries =
                campaign.getDisturbVoucher().entrySet();

        for (Map.Entry<String, Collection<Voucher>> entry : entries) {
            int size = entry.getValue().size();

            if (size < minimumNo) {
                minimumNo = size;
                eMailAddress = entry.getKey();
            }
        }

        if (minimumNo < Integer.MAX_VALUE) {
            return campaign.generateVoucher(eMailAddress, type, value);
        }

        return null;
    }
}
