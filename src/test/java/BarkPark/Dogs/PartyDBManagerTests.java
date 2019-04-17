package BarkPark.Dogs;

import org.junit.Before;
import org.junit.Test;

public class PartyDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        PartyDBManager.dropPartyTable();
        PartyDBManager.createPartyTable();
    }

    @Test
    public void test() {

    }
}