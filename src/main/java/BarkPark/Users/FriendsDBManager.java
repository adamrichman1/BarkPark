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
    static void addFriendRequest(String username, String friendUsername){
        String sql = "INSERT INTO " + friendsTable + " (user1, user2, accepted) VALUES (?, ?, false)";
        executeUpdate(sql, username, friendUsername);
    }

    /**
     * Used to remove a pending friend request between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of user who received request
     */
    static void removeFriend(String username, String friendUsername) {
        String sql = "DELETE FROM " + friendsTable + " WHERE user1=? AND user2=?";
        executeUpdate(sql, username, friendUsername);
    }

    /**
     * Used to add a friendship between two users in the DB
     *
     * @param receivingUsername username of user who received request
     * @param sendingUsername username of user who sent request
     */
    static void acceptFriendRequest(String receivingUsername, String sendingUsername) {
        String sql = "UPDATE " + friendsTable + " SET accepted=TRUE WHERE user1=? AND user2=?";
        executeUpdate(sql, sendingUsername, receivingUsername);
    }

    /**
     * Checks if a friend request has already been sent between two users
     *
     * @param user1 username of user who sent request
     * @param user2 username of new friend
     * @return true if a friend request exists in the friends table, false otherwise
     */
    static boolean friendRequestExists(String user1, String user2) {
        String sql = "SELECT COUNT(*) AS count FROM " + friendsTable + " WHERE user1 IN (?, ?) AND user2 IN (?, ?) AND accepted=FALSE";
        return deserializeResultSetCol(executeQuery(sql, user1, user2, user1, user2),"count", int.class) == 1;
    }

    /**
     * Checks if two users are friends
     *
     * @param user1 the first user being checked
     * @param user2 the second user being checked
     * @return true if the two users are friends, false otherwise
     */
    static boolean areFriends(String user1, String user2) {
        String sql = "SELECT COUNT(*) AS count FROM " + friendsTable + " WHERE user1 IN (?, ?) AND user2 IN (?, ?) AND accepted=TRUE";
        return deserializeResultSetCol(executeQuery(sql, user1, user2, user1, user2),"count", int.class) == 1;
    }

    /**
     * Finds all usernames of friends for a user
     *
     * @param username the name of the username to find friends for
     * @return a list of usernames that are friends of a user
     */
    static List<String> getFriends(String username) {
        List<String> friends = findFriendsOfUser1(username, true);
        friends.addAll(findFriendsOfUser2(username, true));
        return friends;
    }

    /**
     * Finds all usernames of pending friend requests for a user
     *
     * @param username the name of the username to find friend requests for
     * @return a list of usernames that are pending friends of a user
     */
    static List<String> getPendingFriendRequests(String username) {
        return findFriendsOfUser2(username, false);
    }

    /**
     * Finds all usernames of sent pending friend requests
     *
     * @param username the name of the username to find friend requests for
     * @return a list of usernames that a user has sent friend requests to
     */
    static List<String> getSentPendingFriendRequests(String username) {
        return findFriendsOfUser1(username, false);
    }

    /**
     * Finds a list of usernames who are friends with the specified user
     *
     * @param user1 the username of the user stored in the user1 col of the friends table
     * @param accepted true if the users are friends, false otherwise
     * @return a list of usernames
     */
    private static List<String> findFriendsOfUser1(String user1, boolean accepted) {
        String sql = "SELECT user2 AS user FROM " + friendsTable + " WHERE user1=? AND accepted=?";
        return populateUsernameList(executeQuery(sql, user1, accepted));
    }

    /**
     * Finds a list of usernames who are friends with the specified user
     *
     * @param user2 the username of the user stored in the user2 col of the friends table
     * @param accepted true if the users are friends, false otherwise
     * @return a list of usernames
     */
    private static List<String> findFriendsOfUser2(String user2, boolean accepted) {
        String sql = "SELECT user1 AS user FROM " + friendsTable + " WHERE user2=? AND accepted=?";
        return populateUsernameList(executeQuery(sql, user2, accepted));
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
