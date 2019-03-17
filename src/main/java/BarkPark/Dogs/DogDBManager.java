package BarkPark.Dogs;

import BarkPark.Core.DBManager;

/**
 * This class contains database/query functionality specific to FixIT customers
 */
public class DogDBManager<T extends Dog> extends DBManager {


    /**
     * Used to insert a new Dog into the database
     *
     * @param dog the Dog to insert into the database
     */
    public void insertDogToDB(Dog dog) { }

    /**
     * Used to remove a dog from the database and remove ownership from owner in database
     *
     * @param dogName name of dog to be removed from database
     * @param ownerUsername name of user who owns the dog to be removed
     */
    public void removeDogFromDB(String dogName, String ownerUsername) { }

    /**
     * Used to check if a dog already exists before inserting a dog into the database table
     *
     * @param dogName the name of the dog to query for
     * @return true if the dog name exists in the DB, false otherwise
     */
    public boolean dogExists(String dogName) {
        return false;
    }

    /**
     * Used to get information about the dog being queried for
     *
     * @param dogName the name of the dog whose profile should be queried for
     * @return a Dog object
     */
    public Dog getDogProfile(String dogName) {
        return null;
    }

    /**
     * Used to update a dog in the database
     *
     * @param dog a new modified dog object to replace in the database
     */
    public void updateDogProfile(Dog dog) { }

    /**
     * Used to create a Dog database table
     *
     */
    private void createDogTable() { }
}
