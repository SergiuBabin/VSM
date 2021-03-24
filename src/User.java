import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

enum UserType {
    ADMIN,
    GUEST
}

public class User {
    private Integer ID;
    private String name;
    private String email;
    private String password;
    private UserType type;
    private UserVoucherMap receivedVouchers;
    private Collection<Notification> notifications;

    public User() {
        receivedVouchers = new UserVoucherMap();
        notifications = new ArrayList<>();
    }

    public boolean update(Notification notification) {
        if (!(notifications.contains(notification))) {
            return notifications.add(notification);
        }
        return false;
    }

    public boolean isAdmin() {
        return type == UserType.ADMIN ? true : false;
    }

    public void setReceivedVouchers(Voucher voucher) {
        receivedVouchers.addVoucher(voucher);
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = UserType.valueOf(type);
    }

    public UserVoucherMap getReceivedVouchers() {
        return receivedVouchers;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }
    public void listNotifications(JTextArea textArea) {
        if (notifications.isEmpty()) {
            textArea.setText("[]");
            return;
        }

        for (Notification notification : notifications) {
            textArea.setText(notification + " ");

            int campaignId = notification.getCampaignId();

            Set<Map.Entry<Integer, Collection<Voucher>>> entries =
                    receivedVouchers.entrySet();

            for (Map.Entry<Integer, Collection<Voucher>> entry : entries) {
                if (entry.getKey().equals(campaignId)) {
                    textArea.append("[ ");

                    for (Voucher voucher : entry.getValue()) {
                        textArea.append(voucher.getVoucherID() + " ");
                    }

                    textArea.append("]\n");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[" + ID + ";" + name + ";" + email + ";" + type + "]";
    }
}
