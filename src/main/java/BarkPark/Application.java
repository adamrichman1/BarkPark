package BarkPark;

import BarkPark.Dogs.DogAgeTracker;
import BarkPark.Dogs.DogDBManager;
import BarkPark.Dogs.ParkDBManager;
import BarkPark.Dogs.PartyDBManager;
import BarkPark.Users.FriendsDBManager;
import BarkPark.Users.UserDBManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * This class contains the main method of the program and manages teardown operations, which are performed upon system
 * shutdown
 */
@SpringBootApplication
@EnableScheduling
@Controller
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static Thread dogAgeTracker;

    /**
     * This method is used on startup and is called implicitly by Spring Boot
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Add shutdown hook so tearDown() is called upon system shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(Application::tearDown));

        // Launch BarkPark
        logger.info("------------ BarkPark Launching ------------");
        initializeResources();
        SpringApplication.run(Application.class, args);
        logger.info("------------ BarkPark Launched Successfully ------------");
    }

    /**
     * This method is used for tear-down operations and is called implicitly by Spring Boot upon shutdown
     */
    @PreDestroy
    public static void tearDown() {
        DogAgeTracker.kill();
        dogAgeTracker.interrupt();
        logger.info("------------ BarkPark Shutdown Successfully ------------");
    }

    /**
     * Initializes resources on startup
     */
    private static void initializeResources() {
        initializeDogDBManager();
        initializeFriendsDBManager();
        initializeParkDBManager();
        initializePartyDBManager();
        initializeUserDBManager();
        initializeDogAgeTracker();
    }

    /**
     * Initializes DogDBManager
     */
    private static void initializeDogDBManager() {
        DogDBManager.dropDogTable();
        DogDBManager.createDogTable();
    }

    /**
     * Initializes UserDBManager
     */
    private static void initializeUserDBManager() {
        UserDBManager.dropUserTable();
        UserDBManager.createUserTable();
    }

    /**
     * Initializes ParkDBManager
     */
    private static void initializeParkDBManager() {
        ParkDBManager.dropParkTable();
        ParkDBManager.createParkTable();
    }

    /**
     * Initializes FriendsDBManager
     */
    private static void initializeFriendsDBManager() {
        FriendsDBManager.dropFriendsTable();
        FriendsDBManager.createFriendsTable();
    }

    /**
     * Initializes PartyDBManager
     */
    private static void initializePartyDBManager() {
        PartyDBManager.dropPartyTable();
        PartyDBManager.createPartyTable();
    }

    /**
     * Initializes a DogAgeTracker
     */
    private static void initializeDogAgeTracker() {
        (dogAgeTracker = new DogAgeTracker()).start();
    }

    /**
     * Used to convert an object into a String
     *
     * @param o the object to convert to a String
     * @return the Stringified version of hte object in JSON format
     */
    public static String toString(Object o) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            return ow.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
