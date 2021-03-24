import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Test {

     static void runTest(String arg) {
        String accesTest = "VMStests/test0" + arg + "/input/";
        String debug = "";
        LocalDateTime aplicationTime;
        try {
            BufferedReader br = new BufferedReader(new FileReader(accesTest + "/campaigns.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
            VMS vms = VMS.getInstance();
            String line;
            line = br.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            aplicationTime = LocalDateTime.parse(br.readLine(), formatter);
            while ((line = br.readLine()) != null) {
                debug = line;
                Campaign campaign = new Campaign();
                String[] lin = line.split(";");
                campaign.setID(Integer.parseInt(lin[0]));
                campaign.setName(lin[1]);
                campaign.setDescription(lin[2]);
                campaign.setStartDate(LocalDateTime.parse(lin[3], formatter));
                campaign.setEndDate(LocalDateTime.parse(lin[4], formatter));
                campaign.setVoucherNrTotal(Integer.parseInt(lin[5]));
                campaign.setStrategytype(lin[6]);

                if (Utils.validateDate(aplicationTime, LocalDateTime.parse(lin[3], formatter))){
                    campaign.setStatus(CampaignStatusType.NEW);
                } else if (Utils.validateDate(aplicationTime, LocalDateTime.parse(lin[4], formatter))) {
                    campaign.setStatus(CampaignStatusType.STARTED);
                } else {
                    campaign.setStatus(CampaignStatusType.EXPIRED);
                }

                vms.addCampaign(campaign);
            }

            br = new BufferedReader(new FileReader(accesTest + "/users.txt"));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                debug = line;
                User user = new User();
                String[] lin = line.split(";");
                user.setID(Integer.parseInt(lin[0]));
                user.setName(lin[1]);
                user.setPassword(lin[2]);
                user.setEmail(lin[3]);
                user.setType(lin[4]);
                vms.addUser(user);
            }

            br = new BufferedReader(new FileReader(accesTest + "/events.txt"));
            line = br.readLine();
            aplicationTime = LocalDateTime.parse(line, formatter);
            br.readLine();

            while ((line = br.readLine()) != null) {
                debug = line;
                String[] lin = line.split(";");
                Integer userID = Integer.parseInt(lin[0]);
                String action = lin[1];
                switch (action) {
                    case "addCampaign":
                        if (vms.getUser(userID).isAdmin()) {
                            Campaign campaign = new Campaign();

                            campaign.setID(Integer.parseInt(lin[2]));
                            campaign.setName(lin[3]);
                            campaign.setDescription(lin[4]);
                            campaign.setStartDate(LocalDateTime.parse(lin[5], formatter));
                            campaign.setEndDate(LocalDateTime.parse(lin[6], formatter));
                            campaign.setVoucherNrTotal(Integer.parseInt(lin[7]));
                            campaign.setStrategytype(lin[8]);
                            campaign.setStatus(CampaignStatusType.NEW);
                            vms.addCampaign(campaign);
                        }
                        break;
                    case "editCampaign":
                        if (vms.getUser(userID).isAdmin()) {
                            int campaignID = Integer.parseInt(lin[2]);
                            if (vms.getCampaign(campaignID).isNew()) {
                                Campaign campaign = vms.getCampaign(campaignID);

                                campaign.setName(lin[3]);
                                campaign.setDescription(lin[4]);
                                campaign.setStartDate(LocalDateTime.parse(lin[5], formatter));
                                campaign.setEndDate(LocalDateTime.parse(lin[6], formatter));
                                campaign.setVoucherNrTotal(Integer.parseInt(lin[7]));

                                vms.updateCampaign(Integer.parseInt(lin[2]), campaign);

                                Notification notification = new Notification();
                                notification.setCampaignID(campaignID);
                                notification.setNotificationDate(aplicationTime);
                                notification.setType(NotificationType.EDIT);

                                vms.getCampaign(campaignID).notifyAllObservers(notification);
                            } else if (vms.getCampaign(campaignID).isStarted()) {
                                Campaign campaign = vms.getCampaign(campaignID);

                                campaign.setEndDate(LocalDateTime.parse(lin[6], formatter));
                                campaign.setVoucherNrTotal(Integer.parseInt(lin[7]));

                                vms.updateCampaign(Integer.parseInt(lin[2]), campaign);

                                Notification notification = new Notification();
                                notification.setCampaignID(campaignID);
                                notification.setNotificationDate(aplicationTime);
                                notification.setType(NotificationType.EDIT);

                                vms.getCampaign(campaignID).notifyAllObservers(notification);
                            }
                        }
                        break;
                    case "cancelCampaign":
                        if (vms.getUser(userID).isAdmin()) {
                            int campaignID = Integer.parseInt(lin[2]);
                            if (vms.getCampaign(campaignID).isStarted() || vms.getCampaign(campaignID).isNew()) {
                                vms.cancelCampaign(campaignID);

                                Notification notification = new Notification();
                                notification.setCampaignID(campaignID);
                                notification.setNotificationDate(aplicationTime);
                                notification.setType(NotificationType.CANCEL);

                                vms.getCampaign(campaignID).notifyAllObservers(notification);
                            }
                        }
                        break;
                    case "generateVoucher":

                        if (vms.getUser(userID).isAdmin()) {
                            int campaignID = Integer.parseInt(lin[2]);
                            if (vms.getCampaign(campaignID).hasMoreVouchers()) {
                                Voucher voucher = vms.getCampaign(campaignID)
                                        .generateVoucher(lin[3], lin[4], Float.parseFloat(lin[5]));

                                vms.getUsermail(lin[3]).getReceivedVouchers()
                                        .addVoucher(voucher);
                                vms.getCampaign(campaignID).getDisturbVoucher()
                                        .addVoucher(voucher);
                                vms.getCampaign(campaignID)
                                        .addObserver(vms.getUsermail(lin[3]));
                            }
                        }
                        break;
                    case "redeemVoucher":
                        if (vms.getUser(userID).isAdmin()) {
                            int campaigID = Integer.parseInt(lin[2]);
                            if (vms.getCampaign(campaigID).isNew()
                                    || vms.getCampaign(campaigID).isStarted()) {
                                vms.getCampaign(campaigID)
                                        .redeemVoucher(Integer.parseInt(lin[3]), LocalDateTime.parse(lin[4], formatter));
                            }
                        }
                        break;
                    case "getVouchers":
                        if (!vms.getUser(userID).isAdmin()) {
                            Set<Map.Entry<Integer, Collection<Voucher>>> entries =
                                    vms.getUser(userID).getReceivedVouchers().entrySet();

                            if (entries.isEmpty()) {
                                bw.write("[]");
                                bw.newLine();
                            } else {
                                for (Map.Entry<Integer, Collection<Voucher>> entry :
                                        entries) {
                                    bw.write(entry.getValue() + ", ");
                                }
                                bw.newLine();
                            }
                        }
                        break;
                    case "getObservers":
                        if (vms.getUser(userID).isAdmin()) {
                            Campaign campaign = vms.getCampaign(Integer.parseInt(lin[2]));

                            bw.write(String.valueOf(campaign.getObservers()));
                            bw.newLine();
                        }
                        break;
                    case "getNotifications":
                        if (!vms.getUser(userID).isAdmin()) {
                            if (vms.getUser(userID).getNotifications().isEmpty()) {
                                bw.write("[]");
                                bw.newLine();
                            } else {
                                for (Notification notification : vms.getUser(userID).getNotifications()) {
                                    bw.write(notification + " ");

                                    int campaignId = notification.getCampaignId();

                                    Set<Map.Entry<Integer, Collection<Voucher>>> entries =
                                            vms.getUser(userID).getReceivedVouchers().entrySet();

                                    for (Map.Entry<Integer, Collection<Voucher>> entry : entries) {
                                        if (entry.getKey().equals(campaignId)) {
                                            bw.write("[ ");

                                            for (Voucher voucher : entry.getValue()) {
                                                bw.write(voucher.getVoucherID() + " ");
                                            }

                                            bw.write("]");
                                            bw.newLine();
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case "getVoucher":
                        if (vms.getUser(userID).isAdmin()) {
                            int campaignId = Integer.parseInt(lin[2]);
                            Voucher voucher =
                                    vms.getCampaign(campaignId).executeStrategy();

                            if (voucher != null) {
                                vms.getUsermail(voucher.getEmail())
                                        .getReceivedVouchers().addVoucher(voucher);
                                vms.getCampaign(campaignId).getDisturbVoucher()
                                        .addVoucher(voucher);
                                bw.write(voucher.toString());
                                bw.newLine();
                            }
                        }

                        break;
                }
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(debug);
            System.out.println(e.getStackTrace());
        }
    }
    public static void main(String[] args) {
        runTest(args[0]);
    }
}