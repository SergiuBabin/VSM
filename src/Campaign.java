import java.time.LocalDateTime;
import java.util.*;


enum CampaignStatusType{
    NEW,
    STARTED,
    EXPIRED,
    CANCELLED
}

public class Campaign {
    private int ID;
    private int totalNoOfVouchers;
    private int availableVouchers;
    private int voucherCounter;

    private String name;
    private String description;
    private String strategyType;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private CampaignStatusType status;
    private CampaignVoucherMap disturbVoucher;
    private Collection<User> users;

    public Campaign() {
        users = new ArrayList<>();
        disturbVoucher = new CampaignVoucherMap();
        voucherCounter = 1;
    }
    public Voucher executeStrategy() {
        Strategy strategy;

        switch (strategyType) {
            case "A":
                strategy = new StrategyA();
                return strategy.execute(this);

            case "B":
                strategy = new StrategyB();
                return strategy.execute(this);

            case "C":
                strategy = new StrategyC();
                return strategy.execute(this);
        }

        return null;
    }
    public Integer getID() {
        return ID;
    }

    public Integer getTotalNoOfVouchers() {
        return totalNoOfVouchers;
    }

    public Integer getAvailableVouchers() {
        return availableVouchers;
    }

    public Integer getVoucherCounter() {
        return voucherCounter;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStrategytype() {
        return strategyType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public CampaignStatusType getStatus() {
        return status;
    }

    public CampaignVoucherMap getDisturbVoucher() {
        return disturbVoucher;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public boolean isNew() {
        return status == CampaignStatusType.NEW;
    }

    public boolean isStarted() {
        return status == CampaignStatusType.STARTED;
    }

    public boolean isExpired() {
        return status == CampaignStatusType.EXPIRED;
    }
    public boolean isCancelled() {
        return status == CampaignStatusType.CANCELLED;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setVoucherNrTotal(int totalNoOfVouchers) {
        this.totalNoOfVouchers = totalNoOfVouchers;
        this.availableVouchers = totalNoOfVouchers;
    }

    public void setStatus(CampaignStatusType status) {
        this.status = status;
    }

    public void setDisturbVoucher(CampaignVoucherMap disturbVoucher) {
        this.disturbVoucher = disturbVoucher;
    }

    public void setStrategytype(String strategyType) {
        this.strategyType = strategyType;
    }

    public boolean hasMoreVouchers() {
        return availableVouchers > 0;
    }

    public Voucher getVoucher(Integer id) {
        Set<Map.Entry<String, Collection<Voucher>>> entries = disturbVoucher.entrySet();

        for (Map.Entry<String, Collection<Voucher>> entry : disturbVoucher.entrySet()) {
            for (Voucher voucher : entry.getValue()) {
                if (voucher.getVoucherID() == id) {
                    return voucher;
                }
            }
        }
        return null;
    }


    public Voucher generateVoucher(String email, String voucherType, float value) {
        if (voucherType.equals("GiftVoucher")) {
            GiftVoucher voucher = new GiftVoucher();

            voucher.setVoucherID(voucherCounter);
            voucher.setCampaignID(ID);
            voucher.setEmail(email);
            voucher.setMaxSum(value);

            voucherCounter++;
            availableVouchers--;

            return voucher;
        } else if (voucherType.equals("LoyaltyVoucher")) {
            LoyalityVoucher voucher = new LoyalityVoucher();

            voucher.setVoucherID(voucherCounter);
            voucher.setCampaignID(ID);
            voucher.setEmail(email);
            voucher.setDiscount(value);

            voucherCounter++;
            availableVouchers--;

            return voucher;
        }

        return null;
    }

    public void redeemVoucher(Integer ID, LocalDateTime date) {
        Voucher voucher = getVoucher(ID);
        if (voucher.isUnused() && Utils.validateDate(date, endDate)) {
            voucher.setType("USED");
            voucher.setDate(date);
        }
    }

    public Collection<User> getObservers() { // intorc toti userii campaniei
        return users;
    }

    public boolean addObserver(User user) { // adaug un user campaniei
        if (!(users.contains(user))) {
            users.add(user);
            return true;
        }

        return false;
    }

    public boolean removeObserver(User user) { //sterg un user al campaniei
        if (users.contains(user)) {
            users.remove(user);
            return true;
        }

        return false;
    }

    public void notifyAllObservers(Notification notification) { //trimit notificari tuturor userilor campaniei
        for (User user : users) {
            user.update(notification);
        }
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "ID=" + ID +
                ", totalNoOfVouchers=" + totalNoOfVouchers +
                ", availableVouchers=" + availableVouchers +
                ", voucherCounter=" + voucherCounter +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", strategytype='" + strategyType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", disturbVoucher=" + disturbVoucher +
                ", users=" + users +
                '}';
    }
}