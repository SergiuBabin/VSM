import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoyalityVoucher extends Voucher {
    float discount;

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public boolean isUnused() {
        return super.statusType == VoucherStatusType.UNUSED;
    }

    @Override
    public int getVoucherID() {
        return super.voucherID;
    }

    @Override
    public void setVoucherID(int voucherID) {
        super.voucherID = voucherID;
    }

    @Override
    public String getVoucherCode() {
        return super.voucherCode;
    }

    @Override
    public void setVoucherCode(String voucherCode) {
        super.voucherCode = voucherCode;
    }

    @Override
    public VoucherStatusType getType() {
        return super.statusType;
    }

    @Override
    public void setType(String type) {
        super.statusType = VoucherStatusType.valueOf(type);
    }

    @Override
    public LocalDateTime getDate() {
        return super.date;
    }

    @Override
    public void setDate(LocalDateTime date) {
        super.date = date;
    }

    @Override
    public String getEmail() {
        return super.email;
    }

    @Override
    public void setEmail(String email) {
        super.email = email;
    }

    @Override
    public int getCampaignID() {
        return super.campaignID;
    }

    @Override
    public void setCampaignID(int campaignID) {
        super.campaignID = campaignID;
    }

    public String toString() {
        String dateFormat = (date != null) ?
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(date) :
                "null";

        return "[" + voucherID + ";" + statusType + ";" + email
                + ";" + discount + ";" + campaignID + ";" + dateFormat + "]";
    }

    @Override
    public boolean isGiftVoucher() {
        return false;
    }
}