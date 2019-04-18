package BarkPark.Users;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FriendsDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        FriendsDBManager.dropFriendsTable();
        FriendsDBManager.createFriendsTable();
    }

    /**
     * Tests addFriendRequest()
     */
    @Test
    public void testAddFriendRequest() {
        // Add friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Assert
        assertTrue(FriendsDBManager.friendRequestExists("adam", "josh"));
    }

    /**
     * Tests removeFriend()
     */
    @Test
    public void testRemoveFriend() {
        // Add friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Remove friend request
        FriendsDBManager.removeFriend("adam", "josh");

        // Assert
        assertFalse(FriendsDBManager.friendRequestExists("adam", "josh"));
    }

    /**
     * Tests acceptFriendRequest()
     */
    @Test
    public void testAcceptFriendRequest() {
        // Add friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Accept friend request
        FriendsDBManager.acceptFriendRequest("josh", "adam");

        // Assert
        assertTrue(FriendsDBManager.areFriends("adam", "josh"));
    }

    /**
     * Tests friendRequestExists() when the friend request does exist
     */
    @Test
    public void testFriendRequestExistsTrue() {
        // Add friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Assert
        assertTrue(FriendsDBManager.friendRequestExists("adam", "josh"));
    }

    /**
     * Tests friendRequestExists() when the friend request does NOT exist
     */
    @Test
    public void testFriendRequestExistsFalse() {
        // Add friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Assert
        assertFalse(FriendsDBManager.friendRequestExists("joe", "josh"));
    }

    /**
     * Tests getFriends()
     */
    @Test
    public void testGetFriends() {
        List<String> adamsFriends = Arrays.asList("josh", "joe", "chris", "sohel");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "josh");
        FriendsDBManager.acceptFriendRequest("josh", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "joe");
        FriendsDBManager.acceptFriendRequest("joe", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "chris");
        FriendsDBManager.acceptFriendRequest("chris", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "sohel");
        FriendsDBManager.acceptFriendRequest("sohel", "adam");

        // Add and accept friend request for a different user
        FriendsDBManager.addFriendRequest("joe", "ian");
        FriendsDBManager.acceptFriendRequest("ian", "joe");

        // Find friends
        List<String> friends = FriendsDBManager.getFriends("adam");
        adamsFriends.forEach(friend -> assertTrue(friends.contains(friend)));
        assertFalse(friends.contains("ian"));
    }

    /**
     * Tests getPendingFriendRequests()
     */
    @Test
    public void testGetPendingFriendRequests() {
        List<String> adamsFriends = Arrays.asList("josh", "joe", "chris");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("josh", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("joe", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("chris", "adam");

        // Friend request but flipped
        FriendsDBManager.addFriendRequest("adam", "jon");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "sohel");
        FriendsDBManager.acceptFriendRequest("sohel", "adam");

        // Find friends
        List<String> friends = FriendsDBManager.getPendingFriendRequests("adam");
        adamsFriends.forEach(friend -> assertTrue(friends.contains(friend)));
        assertFalse(friends.contains("sohel"));
        assertFalse(friends.contains("jon"));
    }

    /**
     * Tests getSentPendingFriendRequests()
     */
    @Test
    public void testGetSentPendingFriendRequests() {
        List<String> adamsFriends = Arrays.asList("josh", "joe", "chris");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "joe");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "chris");

        // Friend request but flipped
        FriendsDBManager.addFriendRequest("jon", "adam");

        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "sohel");
        FriendsDBManager.acceptFriendRequest("sohel", "adam");

        // Find friends
        List<String> friends = FriendsDBManager.getSentPendingFriendRequests("adam");
        adamsFriends.forEach(friend -> assertTrue(friends.contains(friend)));
        assertFalse(friends.contains("sohel"));
        assertFalse(friends.contains("jon"));
    }

    /**
     * Tests areFriends() when the users are friends
     */
    @Test
    public void testAreFriendsTrue() {
        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "josh");
        FriendsDBManager.acceptFriendRequest("josh", "adam");

        // Assert
        assertTrue(FriendsDBManager.areFriends("adam", "josh"));
    }

    /**
     * Tests areFriends() when the users are NOT friends
     */
    @Test
    public void testAreFriendsFalse() {
        // Add and accept friend request
        FriendsDBManager.addFriendRequest("adam", "josh");

        // Assert
        assertFalse(FriendsDBManager.areFriends("adam", "josh"));
    }
}