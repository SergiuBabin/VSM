import java.util.ArrayList;
import java.util.Collection;

public class VMS {
    ArrayList<Campaign> campaigns;
    Collection<User> users;

    private static VMS vms = null;
    private VMS(){
        campaigns = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static VMS getInstance() {
        if (vms == null)
            vms = new VMS();
        return vms;
    }

    public Collection<Campaign> getCampaigns() {
        return campaigns;
    }

    public Campaign getCampaign(Integer id) {
        for (Campaign campaign : campaigns) {
            if (campaign.getID().equals(id)) {
                return campaign;
            }
        }

        return null;
    }

    public boolean addCampaign(Campaign campaign) {
        return campaigns.add(campaign);
    }

    public void updateCampaign(Integer id, Campaign campaign){
        int campaignID = campaigns.indexOf(getCampaign(id));
        campaigns.remove(getCampaign(id));
        campaigns.add(campaignID, campaign);
    }

    public void cancelCampaign(Integer id) {
        for (Campaign campaign : campaigns) {
            if(campaign.getID().equals(id)) {
                campaign.setStatus(CampaignStatusType.CANCELLED);
            }
        }
    }

    public Collection<User> getUsers() {
        return users;
    }

    public User getUser(Integer ID) {
        for (User user : users) {
            if (user.getID().equals(ID)) {
                return user;
            }
        }
        return null;
    }

    public User getUsermail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public String toString() {
        return users.toString() + campaigns.toString();
    }
}
