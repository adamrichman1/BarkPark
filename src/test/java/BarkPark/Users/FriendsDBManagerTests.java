package BarkPark.Users;

import org.junit.Before;
import org.junit.Test;

public class FriendsDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        FriendsDBManager.dropFriendsTable();
        FriendsDBManager.createFriendsTable();
    }

    @Test
    public void test() {

    }
}