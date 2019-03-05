package BarkPark.Users;

import BarkPark.DBManager;

/**
 * This class contains query functionality that is general to users of the BarkPark application.
 *
 */
public abstract class UserDBManager extends DBManager {

    public String userTable;

    public UserDBManager(String userTable) {
        this.userTable = userTable;
    }

    /**
     * Used to insert a new user into the database
     *
     * @param user the user to insert into the database
     */
    public void insertUserToDB(User user) {

    }

    /**
     * Used to get information about the user for the user's profile page
     *
     * @param username the username of the user whose profile should be queried for
     * @return a User object containing the user's profile
     */
    public User getUserProfile(String username){
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

