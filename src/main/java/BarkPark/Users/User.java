package BarkPark.Users;

import BarkPark.Dogs.Dog;

import java.util.ArrayList;

/**
 * This class represents a User in the BarkPark system.
 */
public class User {
    private String username;
    private String password;
    private String name;
    private Integer age;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<Dog> dogs) {
        this.dogs = dogs;
    }

    public void addDog(Dog d) {
        dogs.add(d);
    }

    public void removeDog(Dog d) {
        dogs.remove(d);
    }
}
