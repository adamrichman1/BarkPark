package BarkPark.Users;

import BarkPark.Application;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    UserList(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String toString() {
        return Application.toString(this);
    }

}
