package BarkPark.Dogs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartyDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        PartyDBManager.dropPartyTable();
        PartyDBManager.createPartyTable();
    }

    /**
     * Tests insertToParkTable()
     */
    @Test
    public void testInsertToParkTable() {
        // Insert a user into a park
        DogParty party = PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Confirm data was inserted
        assertEquals("schenley", party.getParkName());
        assertEquals("party", party.getPartyName());
        assertEquals("adam", party.getPartyHost());
        assertEquals(0, party.getUsersInParty().size());
    }

    /**
     * Tests addUserToDogParty()
     */
    @Test
    public void testAddUserToDogParty() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Add user to party
        PartyDBManager.addUserToParty("schenley", "party", "jeff");

        // Confirm Jeff is in party
        assertTrue(PartyDBManager.isUserInParty("schenley", "party", "jeff"));
    }

    /**
     * Tests isUserInParty() when the user is in the party
     */
    @Test
    public void testIsUserInPartyTrue() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Add user to party
        PartyDBManager.addUserToParty("schenley", "party", "jeff");

        // Confirm Jeff is in party
        assertTrue(PartyDBManager.isUserInParty("schenley", "party", "adam"));
    }

    /**
     * Tests isUserInParty() when the user is NOT in the party
     */
    @Test
    public void testIsUserInPartyFalse() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Add user to party
        PartyDBManager.addUserToParty("schenley", "party", "jeff");

        // Confirm Jeff is in party
        assertFalse(PartyDBManager.isUserInParty("schenley", "party", "joe"));
    }

    /**
     * Tests partyExists() when the party does exist
     */
    @Test
    public void testPartyExistsTrue() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Assert
        assertTrue(PartyDBManager.partyExists("schenley", "party"));
    }

    /**
     * Tests partyExists() when the party does NOT exist
     */
    @Test
    public void testPartyExistsFalse() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Assert
        assertFalse(PartyDBManager.partyExists("frick", "party"));
    }

    /**
     * Tests endParty()
     */
    @Test
    public void testEndParty() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Delete party
        PartyDBManager.endParty("schenley", "party");

        // Assert
        assertFalse(PartyDBManager.partyExists("schenley", "party"));
    }

    /**
     * Tests removeUserFromParty()
     */
    @Test
    public void testRemoveUserFromParty() {
        // Create party
        PartyDBManager.insertToPartyTable("schenley", "party", "adam");

        // Add user to party
        PartyDBManager.addUserToParty("schenley", "party", "joe");

        // Remove user from party
        PartyDBManager.removeUserFromParty("schenley", "party", "joe");

        // Assert
        assertFalse(PartyDBManager.isUserInParty("schenley", "party", "joe"));
    }
}