package BarkPark.Dogs;

import BarkPark.Core.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class contains database/query functionality specific to FixIT customers
 */
public class DogDBManager extends DBManager {

    private static String dogTable = "dogs";
    private static Logger logger = LoggerFactory.getLogger(DogDBManager.class);

    /**
     * Used to create a Dog database table
     */
    public static void createDogTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + dogTable +
                " (name         TEXT        NOT NULL, " +
                "ownerUsername  TEXT        NOT NULL, " +
                "breed          TEXT        NOT NULL, " +
                "age            INT         NOT NULL)";
        executeUpdate(sql);
    }

    /**
     * Drops the dog table
     */
    public static void dropDogTable() {
        dropTable(dogTable);
    }

    /**
     * Used to insert a new Dog into the database
     *
     * @param name the name of the dog
     * @param ownerUsername the username of the dog's owner
     * @param breed the dog's breed
     * @param age the dog's age
     */
    public static void insertDogToDB(String name, String ownerUsername, String breed, int age) {
        String sql = "INSERT INTO " + dogTable + " (name, ownerUsername, breed, age) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, name, ownerUsername, breed, age);
    }

    /**
     * Used to remove a dog from the database and remove ownership from owner in database
     *
     * @param name name of dog to be removed from database
     * @param ownerUsername username of user who owns the dog being removed
     */
    public static void removeDogFromDB(String name, String ownerUsername) {
        String sql = "DELETE FROM " + dogTable + " WHERE name=? AND ownerUsername=?";
        executeUpdate(sql, name, ownerUsername);
    }

    /**
     * Used to check if a dog already exists before inserting a dog into the database table
     *
     * @param name the name of the dog to query for
     * @param ownerUsername the username of the user whose dog is being checked
     * @return true if the dog name exists in the DB, false otherwise
     */
    public static boolean dogExists(String name, String ownerUsername) {
        String sql = "SELECT COUNT(*) AS count FROM " + dogTable + " WHERE name=? AND ownerUsername=?";
        return deserializeResultSetCol(executeUpdate(sql, name, ownerUsername), "count", int.class) == 1;
    }

    /**
     * Used to get information about the dog being queried for
     *
     * @param name the name of the dog whose profile should be queried for
     * @return a Dog object
     */
    public static Dog getDogProfile(String name, String ownerUsername) {
        String sql = "SELECT * FROM " + dogTable + " WHERE name=? AND ownerUsername=?";
        return populateDog(executeQuery(sql, name, ownerUsername));
    }

    /**
     * Used to query for a user's dogs
     *
     * @param ownerUsername the username of the owner to query for
     * @return an ArrayList of the user's dogs
     */
    public static ArrayList<Dog> getUserDogs(String ownerUsername) {
        String sql = "SELECT * FROM " + dogTable + " WHERE ownerUsername=?";
        return populateDogList(executeQuery(sql, ownerUsername));
    }

    /**
     * Used to populate a list of dogs for a user following a DB query
     *
     * @param rs the ResultSet containing data from the database to populate a Dog object with
     * @return a list of dogs from a ResultSet
     */
    private static ArrayList<Dog> populateDogList(ResultSet rs) {
        try {
            ArrayList<Dog> dogs = new ArrayList<>();
            while (rs.next()) {
                dogs.add(new Dog(rs.getString("name"), rs.getString("ownerUsername"),
                        rs.getString("breed"), rs.getInt("age")));
            }
            return dogs;
        } catch (SQLException e) {
            logger.error(">>> ERROR: Couldn't populate dog list from ResultSet", e);
            System.exit(1);
        }
        return null;
    }

    /**
     * Used to populate a dog object following a DB query
     *
     * @param rs the ResultSet containing data from the database to populate a Dog object with
     * @return a Dog object with data from the ResultSet
     */
    private static Dog populateDog(ResultSet rs) {
        try {
            if (rs.next()) {
                return new Dog(rs.getString("name"), rs.getString("ownerUsername"),
                        rs.getString("breed"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            logger.error(">>> ERROR: Couldn't populate dog from ResultSet", e);
            System.exit(1);
        }
        return null;
    }

    /**
     * Used to update a dog in the database
     *
     * @param name the name of the dog
     * @param ownerUsername the username of the dog's owner
     * @param breed the breed of the dog
     * @param age the age of the dog
     */
    public static void updateDogProfile(String name, String ownerUsername, String breed, int age) {
        String sql = "UPDATE " + dogTable + " SET breed=?, age=? WHERE name=? AND ownerUsername=?";
        executeUpdate(sql, breed, age, name, ownerUsername);
    }
}
