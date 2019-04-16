package BarkPark.Users;

import BarkPark.Core.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsDBManager extends DBManager {
    private static Logger logger = LoggerFactory.getLogger(FriendsDBManager.class);
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

    /**
     * Finds all usernames of friends for a user
     *
     * @param username the name of the username to find friends for
     * @return a list of usernames that are friends of a user
     */
    static List<String> getFriends(String username) {
        List<String> friends = findFriendsOfUser1(username);
        friends.addAll(findFriendsOfUser2(username));
        return friends;
    }

    /**
     * Finds a list of usernames who are friends with the specified user
     *
     * @param user1 the username of the user stored in the user1 col of the friends table
     * @return a list of usernames
     */
    private static List<String> findFriendsOfUser1(String user1) {
        String sql = "SELECT user2 AS user FROM " + friendsTable + " WHERE user1=? AND accepted=TRUE";
        return populateUsernameList(executeQuery(sql, user1));
    }

    /**
     * Finds a list of usernames who are friends with the specified user
     *
     * @param user2 the username of the user stored in the user2 col of the friends table
     * @return a list of usernames
     */
    private static List<String> findFriendsOfUser2(String user2) {
        String sql = "SELECT user1 AS user FROM " + friendsTable + " WHERE user2=? AND accepted=TRUE";
        return populateUsernameList(executeQuery(sql, user2));
    }

    /**
     * Populates a list of usernames from a ResultSet object
     *
     * @param rs the ResultSet from the DB query
     * @return a list of usernames
     */
    private static List<String> populateUsernameList(ResultSet rs) {
        try {
            List<String> usernames = new ArrayList<>();
            while (rs.next()) {
                usernames.add(rs.getString("user"));
            }
            return usernames;
        } catch(SQLException e) {
            logger.error(">>> ERROR: Couldn't populate username list", e);
            System.exit(1);
        }
        return null;
    }
}
