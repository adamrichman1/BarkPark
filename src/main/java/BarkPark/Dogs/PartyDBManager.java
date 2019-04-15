package BarkPark.Dogs;

import BarkPark.Core.DBManager;

public class PartyDBManager extends DBManager {
    private static String partyTable = "parties";

    public static void createPartyTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + partyTable +
                " (parkName         TEXT        NOT NULL," +
                " partyName         TEXT        NOT NULL," +
                " party             TEXT[]      NOT NULL");
    }

    /**
     * Drops the parks table
     */
    public static void dropPartyTable() {
        dropTable(partyTable);
    }

    /**
     * Adds a new park to the parks table
     *
     * @param parkName the name of the park
     * @param ownerUsername the name of the username who entered the park for the first time
     */
    public static void insertToParkTable(String parkName, String partyName, String ownerUsername) {
        String sql = "INSERT INTO " + partyTable + " (parkName, partyName, party) VALUES (?, ?, ?)";
        executeUpdate(sql, parkName, partyName, new String[] {ownerUsername});
    }


}
