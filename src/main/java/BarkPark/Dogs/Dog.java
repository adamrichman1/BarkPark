package BarkPark.Dogs;

/**
 * This class represents a Dog object in the BarkPark application.
 */
public class Dog {
    private String name;
    private String ownerUsername;
    private String breed;
    private int age;

    Dog(String name, String ownerUsername, String breed, int age) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.breed = breed;
        this.age = age;
    }

    public String getName() { return name; }

    public String getOwnerUsername() { return ownerUsername; }

    public String getBreed() { return breed; }

    public int getAge() { return age; }
}
