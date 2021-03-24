import java.time.LocalDateTime;

enum VoucherStatusType {
    USED,
    UNUSED
}

abstract class Voucher {
    protected int voucherID;
    protected int campaignID;

    protected String voucherCode;
    protected String email;

    protected VoucherStatusType statusType;
    protected LocalDateTime date;

    public Voucher() {
        statusType = VoucherStatusType.UNUSED;
    }

    public abstract boolean isUnused();
    public abstract int getVoucherID();

    public abstract void setVoucherID(int voucherID);

    public abstract String getVoucherCode();

    public abstract void setVoucherCode(String voucherCode);

    public abstract VoucherStatusType getType();

    public abstract void setType(String type);

    public abstract LocalDateTime getDate();

    public abstract void setDate(LocalDateTime date);

    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract int getCampaignID();

    public abstract void setCampaignID(int campaignID);

    public abstract String toString();

    public abstract boolean isGiftVoucher();
}