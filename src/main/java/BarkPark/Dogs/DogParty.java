package BarkPark.Dogs;

import BarkPark.Application;

import java.util.List;

public class DogParty {

    private String parkName;
    private String partyName;
    private String partyHost;
    private List<String> usersInParty;

    public DogParty() {}

    public DogParty(String parkName, String partyName, String partyHost, List<String> usersInParty) {
        this.parkName = parkName;
        this.partyName = partyName;
        this.partyHost = partyHost;
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

    public String getPartyHost() {
        return partyHost;
    }

    public void setPartyHost(String partyHost) {
        this.partyHost = partyHost;
    }

    public List<String> getUsersInParty() {
        return usersInParty;
    }

    public void setUsersInParty(List<String> usersInParty) {
        this.usersInParty = usersInParty;
    }

    public String toString() {
        return Application.toString(this);
    }
}
