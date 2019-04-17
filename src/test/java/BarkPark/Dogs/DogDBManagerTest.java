package BarkPark.Dogs;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DogDBManagerTest {

    private DogDBManager dogDBManager;

    @Before
    public void setup() {
        DogDBManager.dropDogTable();
        DogDBManager.createDogTable();
        dogDBManager = DogDBManager.getInstance();
    }

    /**
     * Tests insertDogToDB()
     */
    @Test
    public void testInsertDogToDB() {
        String name = "pup";
        String ownerUsername = "adam";
        String breed = "poodle";
        int age = 5;

        // Test insert
        Dog inserted = dogDBManager.insertDogToDB(name, ownerUsername, breed, age);

        // Asserts
        assertEquals(name, inserted.getName());
        assertEquals(ownerUsername, inserted.getOwnerUsername());
        assertEquals(breed, inserted.getBreed());
        assertEquals(age, inserted.getAge());
    }

    /**
     * Tests removeDogFromDB()
     */
    @Test
    public void testRemoveDogFromDB() {
        // Insert mock dog
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);

        // Delete dog
        dogDBManager.removeDogFromDB("pup", "adam");

        // Assert dog isn't in DB
        assertFalse(dogDBManager.dogExists("pup", "adam"));
    }

    /**
     * Tests dogExists() when a dog is present in the DB
     */
    @Test
    public void testDogExistsTrue() {
        // Insert mock dog
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);

        // Assert dog is in DB
        assertTrue(dogDBManager.dogExists("pup", "adam"));
    }

    /**
     * Tests dogExists() when a dog is NOT present in the DB
     */
    @Test
    public void testDogExistsFalse() {
        // Assert dog is NOT in DB
        assertFalse(dogDBManager.dogExists("pup", "adam"));
    }

    /**
     * Tests getDogProfile()
     */
    @Test
    public void testGetDogProfile() {
        // Insert mock dog
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);

        // Retrieve dog
        Dog dog = dogDBManager.getDogProfile("pup", "adam");

        // Asserts
        assertEquals("pup", dog.getName());
        assertEquals("adam", dog.getOwnerUsername());
        assertEquals("poodle", dog.getBreed());
        assertEquals(5, dog.getAge());
    }

    /**
     * Tests getUserDogs()
     */
    @Test
    public void testGetUserDogs() {
        // Insert mock dogs for user="adam"
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);
        dogDBManager.insertDogToDB("doggo", "adam", "greyhound", 4);
        dogDBManager.insertDogToDB("kitty", "adam", "golden", 3);

        // Insert mock dog for user="jeff"
        dogDBManager.insertDogToDB("kitty", "jeff", "golden", 3);

        // Test retrieval query
        List<Dog> dogs = dogDBManager.getUserDogs("adam");

        // Asserts
        assertEquals(3, dogs.size());
        assertEquals("kitty", dogs.get(0).getName());
        assertEquals("doggo", dogs.get(1).getName());
        assertEquals("pup", dogs.get(2).getName());
    }

    /**
     * Tests updateDogProfile()
     */
    @Test
    public void testUpdateDogProfile() {
        // Insert mock dog
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);

        // Update dog
        dogDBManager.updateDogProfile("pup", "adam", "poodle", 7);

        // Retrieve dog
        Dog d = dogDBManager.getDogProfile("pup", "adam");

        // Assert dog's age has been modified
        assertEquals(7, d.getAge());
    }

    /**
     * Tests getUserDogs()
     */
    @Test
    public void testGetAllDogs() {
        // Insert mock dogs for user="adam"
        dogDBManager.insertDogToDB("pup", "adam", "poodle", 5);
        dogDBManager.insertDogToDB("doggo", "adam", "greyhound", 4);
        dogDBManager.insertDogToDB("kitty", "adam", "golden", 3);

        // Insert mock dog for user="jeff"
        dogDBManager.insertDogToDB("jordan", "jeff", "golden", 2);

        // Test retrieval query
        List<Dog> dogs = dogDBManager.getAllDogs();

        // Asserts
        assertEquals(4, dogs.size());
        assertEquals("jordan", dogs.get(0).getName());
        assertEquals("kitty", dogs.get(1).getName());
        assertEquals("doggo", dogs.get(2).getName());
        assertEquals("pup", dogs.get(3).getName());
    }
}