package BarkPark.Users;

import BarkPark.Core.DBManager;

public class FriendsDBManager extends DBManager {
    private static String friendsTable = "friends";

    /**
     * Creates the friends table
     */
    public static void createFriendsTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + friendsTable +
                " (user1            TEXT        NOT NULL," +
                " user2             TEXT        NOT NULL," +
                " accepted          BOOLEAN     NOT NULL)");
    }

    /**
     * Drops the friends table
     */
    public static void dropFriendsTable() {
        dropTable(friendsTable);
    }

    /**
     * Used to add a pending friend request between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of user receiving request
     */
    public static void addFriendRequest(String username, String friendUsername){
        String sql = "INSERT INTO " + friendsTable + " (user1, user2, accepted) VALUES (?, ?, false)";
        executeUpdate(sql, username, friendUsername);
    }

    /**
     * Used to remove a pending friend request between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of user who received request
     */
    public static void removeFriend(String username, String friendUsername) {
        String sql = "DELETE FROM " + friendsTable + " WHERE user1=? AND user2=?";
        executeUpdate(sql, username, friendUsername);
    }

    /**
     * Used to add a friendship between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of new friend
     */
    public static void addFriend(String username, String friendUsername) {
        String sql = "UPDATE " + friendsTable + " SET accepted=TRUE WHERE user1=? AND user2=?";
        executeUpdate(sql, username, friendUsername);
    }

    /**
     * Checks if a friend request has already been sent between two users
     *
     * @param username username of user who sent request
     * @param friendUsername username of new friend
     * @return true if a friend request exists in the friends table, false otherwise
     */
    public static boolean friendRequestExists(String username, String friendUsername) {
        String sql = "SELECT COUNT(*) AS count FROM " + friendsTable + " WHERE username IN (?, ?) AND friendUsername IN (?, ?)";
        return deserializeResultSetCol(executeQuery(sql, username, friendUsername, username, friendUsername),"count", int.class) == 1;
    }
}
