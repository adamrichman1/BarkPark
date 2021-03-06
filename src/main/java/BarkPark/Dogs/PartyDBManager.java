package BarkPark.Dogs;

import BarkPark.Core.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;

public class PartyDBManager extends DBManager {
    private static Logger logger = LoggerFactory.getLogger(PartyDBManager.class);
    private static String partyTable = "parties";

    /**
     * Creates the party table
     */
    public static void createPartyTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + partyTable +
                " (parkName         TEXT        NOT NULL," +
                " hostName          TEXT        NOT NULL," +
                " partyName         TEXT        NOT NULL," +
                " party             TEXT[]      NOT NULL)");
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
    public static DogParty insertToPartyTable(String parkName, String partyName, String ownerUsername) {
        String sql = "INSERT INTO " + partyTable + " (parkName, hostName, partyName, party) VALUES (?, ?, ?, ?) RETURNING *";
        return populateDogParty(executeUpdate(sql, parkName, ownerUsername, partyName, new String[] {}));
    }

    /**
     * Adds a user to a dog party
     *
     * @param parkName the name of the park
     * @param partyName the name of the party
     * @param ownerUsername the user to add
     */
    public static void addUserToParty(String parkName, String partyName, String ownerUsername) {
        String sql = "UPDATE " + partyTable + " SET party=party || ?::TEXT[] WHERE parkName=? AND partyName=?";
        executeUpdate(sql, new String[] {ownerUsername}, parkName, partyName);
    }

    /**
     * Checks if a user is already in a party
     *
     * @param parkName the name of the park to check
     * @param partyName the name of the party to check
     * @param ownerUsername the username of the user to check
     * @return true if the owner is in a party, false otherwise
     */
    public static boolean isUserInParty(String parkName, String partyName, String ownerUsername) {
        String sql = "SELECT COUNT(*) AS count FROM " + partyTable + " WHERE parkName=? AND partyName=? AND (?=ANY(party) OR ?=hostName)";
        return deserializeResultSetCol(executeQuery(sql, parkName, partyName, ownerUsername, ownerUsername), "count", int.class) == 1;
    }

    /**
     * Checks if a party already exists in a park
     *
     * @param parkName the name of the park to check
     * @param partyName the name of the party to check
     * @return true if a party already exists in the provided park, false otherwise
     */
    public static boolean partyExists(String parkName, String partyName) {
        String sql = "SELECT COUNT(*) AS count FROM " + partyTable + " WHERE parkName=? AND partyName=?";
        return deserializeResultSetCol(executeQuery(sql, parkName, partyName), "count", int.class) == 1;
    }

    /**
     * Deletes a party from the table
     *
     * @param parkName the name of the park
     * @param partyName the name of the party
     */
    public static void endParty(String parkName, String partyName) {
        String sql = "DELETE FROM " + partyTable + " WHERE parkName=? AND partyName=?";
        executeUpdate(sql, parkName, partyName);
    }

    /**
     * Removes a user from a party
     *
     * @param parkName the name of the park
     * @param partyName the name of the party
     * @param ownerUsername the name of the user to remove from the party
     */
    public static void removeUserFromParty(String parkName, String partyName, String ownerUsername) {
        String sql = "UPDATE " + partyTable + " SET party=ARRAY_REMOVE(party, ?::TEXT) WHERE parkName=? AND partyName=?";
        executeUpdate(sql, ownerUsername, parkName, partyName);
    }

    /**
     * Populates a dog party object following a DB query
     *
     * @param rs the ResultSet object
     * @return a dog party object
     */
    private static DogParty populateDogParty(ResultSet rs) {
        try {
            if (rs.next()) {
                return new DogParty(rs.getString("parkName"), rs.getString("partyName"),
                        rs.getString("hostName"), Arrays.asList(deserializeStringArray(rs, "party", true)));
            }
        } catch (SQLException e) {
            logger.error(">>> ERROR: Couldn't populate DogParty from ResultSet", e);
            System.exit(1);
        }
        return null;
    }
}
