package BarkPark.Dogs;

import org.junit.Before;
import org.junit.Test;

public class DogDBManagerTest {

    @Before
    public void setup() {
        DogDBManager.dropDogTable();
        DogDBManager.createDogTable();
    }

    @Test
    public void testInsertDogToDB() {
    }

    @Test
    public void testDogExists() {
    }

    @Test
    public void testGetDogEntry() {
    }
}