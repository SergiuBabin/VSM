import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum NotificationType{EDIT, CANCEL}

public class Notification {
    private LocalDateTime notificationDate;
    private NotificationType type;
    private Integer campaignID;
    private List<Integer> vouchersCodes;

    public Notification() {
        vouchersCodes = new ArrayList<>();
    }

    public int getCampaignId() {
        return campaignID;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setCampaignID(Integer campaignID) {
        this.campaignID = campaignID;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public String toString() {
        String dateFormat = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm").format(notificationDate);
        return "[" + dateFormat + "]" + " Campania cu numarul " + campaignID +
                " a fost " + (type == NotificationType.EDIT ? "editata." : "anulata.");
    }
}
