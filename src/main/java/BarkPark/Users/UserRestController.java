package BarkPark.Users;

import BarkPark.Dogs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains abstract methods that represent required endpoints to be implemented for Users.
 *
 */
@Controller
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private static DogDBManager dogDBManager = DogDBManager.getInstance();

    /**
     * Used to request a login to the application
     *
     * @param user the login form of the user attempting to login
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Accept=application/json")
    public @ResponseBody ResponseEntity login(@RequestBody User user) {
        logger.info("LOGIN");
        if (!UserDBManager.userExists(user.getUsername())) {
            logger.warn("Invalid username");
            return new ResponseEntity<>("Invalid username", HttpStatus.BAD_REQUEST);
        }
        else if (!UserDBManager.passwordValid(user.getUsername(), user.getPassword())){
            logger.warn("Invalid password");
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("SUCCESS");
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    /**
     * Called when a user attempts to access the BarkPark home page
     *
     * @return the home template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/home")
    public static String getCustomerHome() {
        logger.info("accessed");
        return "home";
    }

    /**
     * Called when a user attempts to login to BarkPark
     *
     * @return the login template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/login")
    public static String getLoginTemplate() {
        return "login";
    }

    /**
     * Called when a user attempts to sign-up with BarkPark
     *
     * @return the sign-up template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/signup")
    public String getSignupTemplate() {
        return "signup";
    }

    /**
     * Used to sign up for the application
     *
     * @param model the model to customize before returning an HTML template to the user
     * @param user the sign-up form of the user attempting to sign-up
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signup", headers = "Accept=application/json")
    public ResponseEntity signUp(Model model, @RequestBody User user) {
        // Check validity of sign-up form
        if (signUpFormInvalid(user)) {
            logger.info("Invalid signup form");
            return new ResponseEntity<>("Invalid registration form", HttpStatus.BAD_REQUEST);
        }
        // Check if user already exists
        else if (UserDBManager.userExists(user.getUsername())) {
            logger.info("Username already exists");
            model.addAttribute("errorMsg", "Username already in use");
            return new ResponseEntity<>("Username already in use",  HttpStatus.BAD_REQUEST);
        }
        // Register user and return success
        else {
            logger.info("Success!");
            UserDBManager.insertUserToDB(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
                    user.getAge());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Used to determine if a sign-up form is invalid
     *
     * @param user the user object passed up via the sign-up endpoint
     * @return true if the form contains an invalid field, false otherwise
     */
    private boolean signUpFormInvalid(User user) {
        return (user.getUsername() == null || user.getUsername().equals("")) ||
                user.getPassword() == null || user.getPassword().equals("") ||
                user.getEmail() == null || user.getEmail().equals("") ||
                user.getName() == null || user.getName().equals("") ||
                user.getAge() == 0;
    }

    /**
     * Used to add a dog to a user's profile
     *
     * @param dog the dog to be added to user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addDog", headers = "Accept=application/json")
    public ResponseEntity addDog(@RequestBody Dog dog) {
        if (dogDBManager.dogExists(dog.getName(), dog.getOwnerUsername())) {
            return new ResponseEntity<>("Dog already exists: " + dog.getName(), HttpStatus.BAD_REQUEST);
        }
        dogDBManager.insertDogToDB(dog.getName(), dog.getOwnerUsername(), dog.getBreed(), dog.getAge());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to remove a dog from a user's profile
     *
     * @param dog the dog to be removed from the user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeDog", headers = "Accept=application/json")
    public ResponseEntity removeDog(@RequestBody Dog dog) {
        if (!dogDBManager.dogExists(dog.getName(), dog.getOwnerUsername())) {
            return new ResponseEntity<>("Dog not found: " + dog.getName(), HttpStatus.BAD_REQUEST);
        }
        dogDBManager.removeDogFromDB(dog.getName(), dog.getOwnerUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to enter a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/goToPark", headers = "Accept=application/json")
    public ResponseEntity goToPark(HttpServletRequest request) {
        if (ParkDBManager.isOwnerInPark(request.getHeader("parkName"), request.getHeader("username"))) {
            return new ResponseEntity<>("User already in park", HttpStatus.BAD_REQUEST);
        }
        ParkDBManager.addOwnerToPark(request.getHeader("parkName"), request.getHeader("username"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to leave a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/leavePark", headers = "Accept=application/json")
    public ResponseEntity leavePark(HttpServletRequest request) {
        if (!ParkDBManager.isOwnerInPark(request.getHeader("parkName"), request.getHeader("username"))) {
            return new ResponseEntity<>("User not in park", HttpStatus.BAD_REQUEST);
        }
        ParkDBManager.deleteUserFromPark(request.getHeader("parkName"), request.getHeader("username"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to initiate a dog party
     *
     * @param dogParty a DogParty object
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/startDogParty", headers = "Accept=application/json")
    public ResponseEntity startDogParty(@RequestBody DogParty dogParty) {
        if (PartyDBManager.partyExists(dogParty.getParkName(), dogParty.getPartyName())) {
            return new ResponseEntity<>("Party with the specified park and name already exist", HttpStatus.BAD_REQUEST);
        }
        PartyDBManager.insertToPartyTable(dogParty.getParkName(), dogParty.getPartyName(), dogParty.getPartyHost());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to end a dog party
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/endDogParty", headers = "Accept=application/json")
    public ResponseEntity endDogParty(HttpServletRequest request) {
        if (!PartyDBManager.partyExists(request.getHeader("parkName"), request.getHeader("partyName"))) {
            return new ResponseEntity<>("Party does not exist", HttpStatus.BAD_REQUEST);
        }
        PartyDBManager.endParty(request.getHeader("parkName"), request.getHeader("partyName"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to accept a friend request
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/acceptFriendRequest", headers = "Accept=application/json")
    public ResponseEntity acceptFriendRequest(HttpServletRequest request) {
        if (!FriendsDBManager.friendRequestExists(request.getHeader("username"), request.getHeader("friendUsername"))) {
            return new ResponseEntity<>("No pending friend request exists", HttpStatus.BAD_REQUEST);
        }
        FriendsDBManager.acceptFriendRequest(request.getHeader("username"), request.getHeader("friendUsername"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to remove a friend
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeFriend", headers = "Accept=application/json")
    public ResponseEntity removeFriend(HttpServletRequest request) {
        if (!FriendsDBManager.areFriends(request.getHeader("username"), request.getHeader("friendUsername"))) {
            return new ResponseEntity<>("Users aren't friends", HttpStatus.BAD_REQUEST);
        }
        FriendsDBManager.removeFriend(request.getHeader("username"), request.getHeader("friendUsername"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to send a friend request to another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendFriendRequest", headers = "Accept=application/json")
    public ResponseEntity sendFriendRequest(HttpServletRequest request) {
        if (FriendsDBManager.friendRequestExists(request.getHeader("username"), request.getHeader("friendUsername"))) {
            return new ResponseEntity<>("Friend request already exists between the two users", HttpStatus.BAD_REQUEST);
        }
        FriendsDBManager.addFriendRequest(request.getHeader("username"), request.getHeader("friendUsername"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to reject a friend request from another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/rejectFriendRequest", headers = "Accept=application/json")
    public ResponseEntity rejectFriendRequest(HttpServletRequest request) {
        if (!FriendsDBManager.friendRequestExists(request.getHeader("username"), request.getHeader("friendUsername"))) {
            return new ResponseEntity<>("No pending friend request exists", HttpStatus.BAD_REQUEST);
        }
        FriendsDBManager.removeFriend(request.getHeader("username"), request.getHeader("friendUsername"));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Used to get all friends of a user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getFriends", headers = "Accept=application/json")
    public ResponseEntity getFriends(HttpServletRequest request) {
        List<String> usernames = FriendsDBManager.getFriends(request.getHeader("username"));
        return new ResponseEntity<>(usernames.stream()
                .map(UserDBManager::getUserProfile)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Used to get all friends of a user
     *
     * @param username the username of the user
     * @param model the model to populate the template with
     * @return the friends template to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/friends", headers = "Accept=application/json")
    public String getFriends(@RequestParam("username")String username, Model model) {
        model.addAttribute("friends", new UserList(new ArrayList<>(FriendsDBManager.getFriends(username).stream()
                .map(UserDBManager::getUserProfile).collect(Collectors.toList()))));
        return "friends";
    }

    /**
     * Used to get all friend requests of a user
     *
     * @param username the username of the user
     * @param model the model to populate the template with
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/friendRequests", headers = "Accept=application/json")
    public String getPendingFriendRequests(@RequestParam("username") String username, Model model) {
        model.addAttribute("userList", new UserList(new ArrayList<>(FriendsDBManager.getPendingFriendRequests(username).stream()
                .map(UserDBManager::getUserProfile)
                .collect(Collectors.toList()))));
        return "friend-requests";
    }

    /**
     * Used to get all sent friend requests of a user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSentFriendRequests", headers = "Accept=application/json")
    public ResponseEntity getSentPendingFriendRequests(HttpServletRequest request) {
        List<String> usernames = FriendsDBManager.getSentPendingFriendRequests(request.getHeader("username"));
        return new ResponseEntity<>(usernames.stream()
                .map(UserDBManager::getUserProfile)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Used to find users by name
     *
     * @param name the name of the user to search by
     * @param model the model to populate the template with
     * @return the user-list template to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findFriendsByName", headers = "Accept=application/json")
    public String findFriendsByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("users", new UserList(new ArrayList<>(UserDBManager.findFriendsByName(name).stream()
                .map(UserDBManager::getUserProfile)
                .collect(Collectors.toList()))));
        return "search-results";
    }

    /**
     * Used to get the edit-profile template
     *
     * @param model the model to populate the template with
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/editProfile", headers = "Accept=application/json")
    public String getEditProfileTemplate(@RequestParam("username") String username, Model model) {
        model.addAttribute("user", UserDBManager.getUserProfile(username));
        return "edit-profile";
    }

    /**
     * Used to get another user's profile page
     *
     * @param model the model to populate the template with
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/userProfile", headers = "Accept=application/json")
    public String getProfilePage(@RequestParam("username") String username, Model model) {
        model.addAttribute("user", UserDBManager.getUserProfile(username));
        return "user-profile";
    }
}
