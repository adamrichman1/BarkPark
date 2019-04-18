package BarkPark.Users;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        UserDBManager.dropUserTable();
        UserDBManager.createUserTable();
    }

    /**
     * Tests insertUserToDB()
     */
    @Test
    public void testInsertUserToDB() {
        // Insert user
        User u = UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Asserts
        assertEquals("adam", u.getUsername());
        assertEquals("adam", u.getName());
        assertEquals("adam@gmail.com", u.getEmail());
        assertEquals(5, u.getAge());
    }

    /**
     * Tests userExists() when the user does exist
     */
    @Test
    public void testUserExistsTrue() {
        // Insert user
        UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Assert
        assertTrue(UserDBManager.userExists("adam"));
    }

    /**
     * Tests userExists() when the user does NOT exist
     */
    @Test
    public void testUserExistsFalse() {
        // Insert user
        UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Assert
        assertFalse(UserDBManager.userExists("josh"));
    }

    /**
     * Tests passwordValid() when the password is valid
     */
    @Test
    public void testPasswordValidTrue() {
        // Insert user
        UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Assert
        assertTrue(UserDBManager.passwordValid("adam", "pass"));
    }

    /**
     * Tests passwordValid() when the password is NOT valid
     */
    @Test
    public void testPasswordValidFalse() {
        // Insert user
        UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Assert
        assertFalse(UserDBManager.passwordValid("adam", "notValid"));
    }

    /**
     * Tests getUserProfile()
     */
    @Test
    public void testGetUserProfile() {
        // Insert user
        UserDBManager.insertUserToDB("adam", "pass", "adam", "adam@gmail.com", 5);

        // Get user profile
        User u = UserDBManager.getUserProfile("adam");

        // Asserts
        assertEquals("adam", u.getUsername());
        assertEquals("adam", u.getName());
        assertEquals("adam@gmail.com", u.getEmail());
        assertEquals(5, u.getAge());
    }
}