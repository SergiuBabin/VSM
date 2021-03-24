import java.util.Random;

public class StrategyA implements Strategy {

    @Override
    public Voucher execute(Campaign campaign) {
        if (campaign.isCancelled() || campaign.isExpired() || !campaign.hasMoreVouchers()) {
            return null;
        }

        Random random = new Random();

        int winner = random.nextInt(campaign.getObservers().size());
        User user = (User) campaign.getObservers().toArray()[winner];
        return campaign.generateVoucher(user.getEmail(), "GiftVoucher", 100f);
    }
}
