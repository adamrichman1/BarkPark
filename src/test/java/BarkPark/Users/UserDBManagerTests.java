package BarkPark.Users;

import org.junit.Before;
import org.junit.Test;

public class UserDBManagerTests {

    /**
     * Setup operations
     */
    @Before
    public void setup() {
        UserDBManager.dropUserTable();
        UserDBManager.createUserTable();
    }

    @Test
    public void test() {

    }
}