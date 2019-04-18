package BarkPark.Dogs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParkDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setUp() {
        ParkDBManager.dropParkTable();
        ParkDBManager.createParkTable();
    }

    /**
     * Shutdown operations
     */
    @After
    public void teardown() {
        ParkDBManager.dropParkTable();
    }

    /**
     * Tests insertToParkTable()
     */
    @Test
    public void testInsertToParkTable() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Retrieve users in park
        String[] usersInPark = ParkDBManager.getOwnersInPark("schenley");

        // Confirm user is in the park
        assertEquals(1, usersInPark.length);
        assertEquals("adam", usersInPark[0]);
    }

    /**
     * Tests deleteUserFromPark()
     */
    @Test
    public void testDeleteUserFromPark() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");
        ParkDBManager.addOwnerToPark("schenley", "brad");

        // Delete user from park
        ParkDBManager.deleteUserFromPark("schenley", "adam");

        // Confirm adam is not in the park
        assertFalse(Arrays.asList(ParkDBManager.getOwnersInPark("schenley")).contains("adam"));
    }

    /**
     * Tests parkExists() when the park is in the DB
     */
    @Test
    public void testParkExistsTrue() {
        // Insert a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Confirm schenley is in the park table
        assertTrue(ParkDBManager.parkExists("schenley"));
    }

    /**
     * Tests parkExists() when the park is NOT in the DB
     */
    @Test
    public void testParkExistsFalse() {
        // Insert a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Confirm frick is NOT  in the park table
        assertFalse(ParkDBManager.parkExists("frick"));
    }

    /**
     * Tests addOwnerToPark()
     */
    @Test
    public void testAddOwnerToPark() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Add owner
        ParkDBManager.addOwnerToPark("schenley", "brad");

        // Confirm brad is in the park
        assertTrue(Arrays.asList(ParkDBManager.getOwnersInPark("schenley")).contains("brad"));
    }

    /**
     * Tests isOwnerInPark when the owner is in the park
     */
    @Test
    public void testIsOwnerInParkTrue() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Confirm adam is in the park
        assertTrue(ParkDBManager.isOwnerInPark("schenley", "adam"));
    }

    /**
     * Tests isOwnerInPark when the owner is NOT in the park
     */
    @Test
    public void testIsOwnerInParkFalse() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Confirm john is NOT in the park
        assertFalse(ParkDBManager.isOwnerInPark("schenley", "john"));
    }

    /**
     * Tests getOwnersInPark()
     */
    @Test
    public void testGetOwnersInPark() {
        // Insert a user into a park
        ParkDBManager.insertToParkTable("schenley", "adam");

        // Add owner
        ParkDBManager.addOwnerToPark("schenley", "brad");

        // Confirm all owners are in the park
        List<String> owners = Arrays.asList(ParkDBManager.getOwnersInPark("schenley"));
        assertTrue(owners.contains("brad"));
        assertTrue(owners.contains("adam"));
        assertEquals(2, owners.size());
    }
}