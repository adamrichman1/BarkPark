package BarkPark.Users;

import BarkPark.Dogs.Dog;

import java.util.ArrayList;

/**
 * This class represents a User in the BarkPark system.
 */
public abstract class User {
    private String username;
    private String name;
    private int age;
    private String email;
    private ArrayList<Dog> dogs;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addDog(Dog d) {
        dogs.add(d);
    }

    public boolean removeDog(Dog d) {
        if (dogs.contains(d)) {
            dogs.remove(d);
            return true;
        } else {
            return false;
        }
    }
}
