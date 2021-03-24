import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class CampaignVoucherMap extends ArrayMap<String, Collection<Voucher>> {

    boolean addVoucher(Voucher v) {
        String userEmailAdress = v.getEmail();

        Set<Entry<String, Collection<Voucher>>> entrySet = super.entrySet();

        for (Entry<String, Collection<Voucher>> entry : entrySet) {
            if (entry.getKey().equals(userEmailAdress)) {
                return entry.getValue().add(v);
            }
        }

        Collection<Voucher> newCollection = new ArrayList<>();
        newCollection.add(v);
        put(userEmailAdress, newCollection);
        return true;
    }
}