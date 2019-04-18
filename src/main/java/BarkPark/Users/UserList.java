package BarkPark.Users;

import BarkPark.Application;

import java.util.List;

public class UserList {
    private List<User> users;

    UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String toString() {
        return Application.toString(this);
    }

}
