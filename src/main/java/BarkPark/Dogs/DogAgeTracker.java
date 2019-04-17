package BarkPark.Dogs;

/**
 * This class spawns a thread that sleeps for a year and then updates all dog ages
 */
public class DogAgeTracker extends Thread {

    private static volatile boolean alive = true;

    /**
     * Thread body
     */
    public void run() {
        while (alive) {
            // Sleep for a year
            try {
                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(31557600);
                }
            } catch (InterruptedException ignored) {
            }

            // Get sole instance of DogDBManager
            DogDBManager dbManager = DogDBManager.getInstance();

            // Update dogs' ages
            dbManager.getAllDogs().forEach(dog ->
                    dbManager.updateDogProfile(dog.getName(), dog.getOwnerUsername(), dog.getBreed(), dog.getAge() + 1));
        }
    }

    /**
     * Sets alive to false so the while loop can break
     */
    public static void kill() {
        alive = false;
    }
}
