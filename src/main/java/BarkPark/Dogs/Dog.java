package BarkPark.Dogs;

/**
 * This class represents a Dog object in the BarkPark application.
 */
public class Dog {
    private String name;
    private String owner;
    private String breed;
    private int age;

    public Dog(String name, String owner, String breed, int age) {
        this.name = name;
        this.owner = owner;
        this.breed = breed;
        this.age = age;
    }

    public String getName() { return name; }

    public String getOwner() { return owner; }

    public String getBreed() { return breed; }

    public int getAge() { return age; }
}
