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

    /**
     * Creates the user table
     */
    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + userTable +
                " (username         TEXT        NOT NULL, " +
                "password           TEXT        NOT NULL, " +
                "name               TEXT        NOT NULL, " +
                "email              TEXT        NOT NULL, " +
                "age                INT         NOT NULL)";
        executeUpdate(sql);
    }

    /**
     * Drops the user table
     */
    public static void dropUserTable() {
        dropTable(userTable);
    }

    /**
     * Used to insert a new user into the database
     *
     * @param username the user's username
     * @param password the user's password
     * @param name the user's name
     * @param age the user's age
     */
    void insertUserToDB(String username, String password, String name, String email, int age) {
        String sql = "INSERT INTO " + userTable + " (username, password, name, email, age) VALUES (?, ?, ?, ?, ?)";
        executeUpdate(sql, username, password, name, email, age);
    }

    /**
     * Used to check if a user already exists before inserting a user into the database table during registration
     *
     * @param username the username to query for
     * @return true if the username exists in the DB, false otherwise
     */
    boolean userExists(String username) {
        String sql = "SELECT COUNT(*) AS count FROM " + userTable + " WHERE username=?";
        return deserializeResultSetCol(executeQuery(sql, username), "count", int.class) == 1;
    }

    /**
     * Used to determine if a user's username and password are valid upon sign-up
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if the password is valid for the given username, false otherwise
     */
    boolean passwordValid(String username, String password) {
        String sql = "SELECT COUNT(*) AS count FROM " + userTable + " WHERE username=? AND password=?";
        return deserializeResultSetCol(executeQuery(sql, username, password), "count", int.class) == 1;
    }

    /**
     * Used to get information about the user for the user's profile page
     *
     * @param username the username of the user whose profile should be queried for
     * @return a User object containing the user's profile
     */
    static User getUserProfile(String username){
        String sql = "SELECT * FROM " + userTable + " WHERE username=?";
        return populateUser(executeQuery(sql, username));
    }

    /**
     * Used to populate a User object following a DB query
     *
     * @param rs the ResultSet object containing data for a user from a DB query
     * @return a User object
     */
    private static User populateUser(ResultSet rs) {
        try {
            if (rs.next()) {
                User u = new User(rs.getString("username"));
                u.setName(rs.getString("name"));
                u.setAge(rs.getInt("age"));
                u.setEmail(rs.getString("email"));
                u.setDogs(DogDBManager.getInstance().getUserDogs(u.getUsername()));
                return u;
            }
        } catch (SQLException e) {
            logger.error(">>> ERROR: Couldn't populate User object", e);
            System.exit(1);
        }
        return null;
    }
}

