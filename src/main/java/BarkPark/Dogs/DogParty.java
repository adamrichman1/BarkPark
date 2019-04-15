package BarkPark.Dogs;

import java.util.List;

public class DogParty {

    private String parkName;
    private String partyName;
    private List<String> usersInParty;

    public DogParty() {}

    public DogParty(String parkName, String partyName, List<String> usersInParty) {
        this.parkName = parkName;
        this.partyName = partyName;
        this.usersInParty = usersInParty;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<String> getUsersInParty() {
        return usersInParty;
    }

    public void setUsersInParty(List<String> usersInParty) {
        this.usersInParty = usersInParty;
    }
}
