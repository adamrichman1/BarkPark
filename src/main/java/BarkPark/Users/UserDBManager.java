package BarkPark.Users;

import BarkPark.Core.DBManager;
import BarkPark.Dogs.DogDBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains query functionality that is general to users of the BarkPark application.
 *
 */
public class UserDBManager extends DBManager {

    private static String userTable = "users";
    private static Logger logger = LoggerFactory.getLogger(UserDBManager.class);

    public UserDBManager() {
        createUserTable(userTable);
    }

    private void createUserTable(String userTable) {
        String sql = "CREATE TABLE IF NOT EXISTS " + userTable +
                " (username         TEXT        NOT NULL, " +
                "password           TEXT        NOT NULL, " +
                "name               TEXT        NOT NULL, " +
                "age                INT         NOT NULL)";
        executeUpdate(sql);
    }

    /**
     * Used to insert a new user into the database
     *
     * @param username the user's username
     * @param password the user's password
     * @param name the user's name
     * @param age the user's age
     */
    public void insertUserToDB(String username, String password, String name, int age) {
        String sql = "INSERT INTO " + userTable + " (username, password, name, age) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, username, password, name, age);
    }

    /**
     * Used to get information about the user for the user's profile page
     *
     * @param username the username of the user whose profile should be queried for
     * @return a User object containing the user's profile
     */
    public User getUserProfile(String username){
        String sql = "SELECT * FROM " + userTable + " WHERE username=?";
        return populateUser(executeUpdate(sql, username));
    }

    /**
     * Used to populate a User object following a DB query
     *
     * @param rs the ResultSet object containing data for a user from a DB query
     * @return a User object
     */
    private User populateUser(ResultSet rs) {
        try {
            if (rs.next()) {
                User u = new User(rs.getString("username"));
                u.setName(rs.getString("name"));
                u.setAge(rs.getInt("age"));
                u.setEmail(rs.getString("email"));
                u.setDogs(DogDBManager.getUserDogs(u.getUsername()));
                return u;
            }
        } catch (SQLException e) {
            logger.error(">>> ERROR: Couldn't populate User object", e);
            System.exit(1);
        }
        return null;
    }

    /**
     * Used to add a pending friend request between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of user receiving request
     */
    public void addFriendRequest(String username, String friendUsername){

    }

    /**
     * Used to remove a pending friend request between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of user who received request
     */
    public void removeFriendRequest(String username, String friendUsername) {

    }

    /**
     * Used to add a friendship between two users in the DB
     *
     * @param username username of user who sent request
     * @param friendUsername username of new friend
     */
    public void addFriend(String username, String friendUsername) {

    }

    /**
     * Used to remove a friendship between two users in the DB
     *
     * @param username username of user who unfriended
     * @param friendUsername username of user being unfriended
     */
    public void removeFriend(String username, String friendUsername) {

    }

}

