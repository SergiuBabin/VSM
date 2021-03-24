import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class StrategyB implements Strategy{
    @Override
    public Voucher execute(Campaign campaign) {
        if (campaign.isCancelled() || campaign.isExpired() || !campaign.hasMoreVouchers()) {
            return null;
        }
        String eMailAddress = "";
        int maximumNo = Integer.MIN_VALUE;

        Set<Map.Entry<String, Collection<Voucher>>> entries =
                campaign.getDisturbVoucher().entrySet();

        for (Map.Entry<String, Collection<Voucher>> entry : entries) {
            int voucherCounter = 0;

            for (Voucher voucher : entry.getValue()) {
                if (!(voucher.isUnused())) {
                    voucherCounter++;
                }
            }

            if (voucherCounter > maximumNo) {
                maximumNo = voucherCounter;
                eMailAddress = entry.getKey();
            }
        }

        if (maximumNo > 0) {
            return campaign.generateVoucher(eMailAddress, "LoyaltyVoucher", 50f);
        }

        return null;
    }
}
