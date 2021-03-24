import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class UserVoucherMap extends ArrayMap<Integer, Collection<Voucher>> {
    boolean addVoucher(Voucher v) {
        Set<Entry<Integer, Collection<Voucher>>> entrySet = super.entrySet();

        for (Entry<Integer, Collection<Voucher>> entry : entrySet) {
            if (entry.getKey().equals(v.getCampaignID())) {
                return entry.getValue().add(v);
            }
        }

        Collection<Voucher> newCollection = new ArrayList<>();
        newCollection.add(v);

        put(v.getCampaignID(), newCollection);
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
