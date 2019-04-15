package BarkPark.Dogs;

import BarkPark.Core.DBManager;

public class ParkDBManager extends DBManager {
    private static String parkTable = "park";

    /**
     * Creates the park table
     */
    public static void createParkTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + parkTable +
                " (parkName         TEXT        NOT NULL," +
                " ownerUsernames    TEXT[]      NOT NULL)");
    }

    /**
     * Drops the parks table
     */
    public static void dropParkTable() {
        dropTable(parkTable);
    }

    /**
     * Adds a new park to the parks table
     *
     * @param parkName the name of the park
     * @param ownerUsername the name of the username who entered the park for the first time
     */
    public static void insertToParkTable(String parkName, String ownerUsername) {
        String sql = "INSERT INTO " + parkTable + " (parkName, ownerUsernames) VALUES (?, ?)";
        executeUpdate(sql, parkName, new String[] {ownerUsername});
    }

    /**
     * Removes a user from a park entry
     *
     * @param parkName the name of the park whose entry is being modified
     * @param ownerUsername the username of the owner being removed from the park entry
     */
    public static void deleteUserFromPark(String parkName, String ownerUsername) {
        String sql = "UPDATE " + parkTable + " SET ownerUsernames = ARRAY_REMOVE(ownerUsernames, ?) WHERE parkName=?";
        executeUpdate(sql, ownerUsername, parkName);
    }

    /**
     * Checks if a park exists in the table
     *
     * @param parkName the name of the park
     * @return true if the park exists, false otherwise
     */
    public static boolean parkExists(String parkName) {
        String sql = "SELECT COUNT(*) AS count FROM " + parkTable + " WHERE parkName=?";
        return deserializeResultSetCol(executeQuery(sql, parkName), "count", int.class) == 1;
    }

    /**
     * Adds a dog-owner to a park
     *
     * @param parkName the name of the park being added to
     * @param ownerUsername the name of the owner being added to the park
     */
    public static void addOwnerToPark(String parkName, String ownerUsername) {
        String sql = "UPDATE " + parkTable + " SET ownerUsernames=ownerUsernames || {?} WHERE parkName=?";
        executeUpdate(sql, ownerUsername, parkName);
    }

    /**
     * Checks if an owner is in the park
     *
     * @param parkName the name of the park being checked
     * @param ownerUsername the username of the owner being checked
     * @return true if the owner is in the park, false otherwise
     */
    public static boolean isOwnerInPark(String parkName, String ownerUsername) {
        String sql = "SELECT COUNT(*) AS count FROM " + parkTable + " WHERE parkName=? AND ?=ANY(ownerUsernames)";
        return deserializeResultSetCol(executeQuery(sql, parkName, ownerUsername), "count", int.class) == 1;
    }

    /**
     * Retrieves all users in a park
     *
     * @param parkName the name of the park
     * @return an array of the usernames of owners in the park
     */
    public static String[] getOwnersInPark(String parkName) {
        String sql = "SELECT ownerUsernames FROM " + parkTable + " WHERE parkName=?";
        return deserializeResultSetCol(executeQuery(sql, parkName), "ownerUsernames", String[].class);
    }
}
