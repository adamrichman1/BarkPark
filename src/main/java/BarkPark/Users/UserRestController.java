package BarkPark.Users;

import BarkPark.Dogs.Dog;
import BarkPark.Dogs.DogParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains abstract methods that represent required endpoints to be implemented for Users.
 *
 */
@Controller
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private static UserDBManager dbManager = new UserDBManager();

    /**
     * Used to request a login to the application
     *
     * @param user the login form of the user attempting to login
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Accept=application/json")
    public @ResponseBody ResponseEntity login(@RequestBody User user) {
        logger.info("LOGIN");
        if (!dbManager.userExists(user.getUsername())) {
            logger.warn("Invalid username");
            return new ResponseEntity<>("Invalid username", HttpStatus.BAD_REQUEST);
        }
        else if (!dbManager.passwordValid(user.getUsername(), user.getPassword())){
            logger.warn("Invalid password");
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("SUCCESS");
            // TODO return cookie?
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    /**
     * Called when a user attempts to access the BarkPark home page
     *
     * @return the home template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/home")
    protected static String getCustomerHome() {
        logger.info("accessed");
        return "home";
    }

    /**
     * Called when a user attempts to login to BarkPark
     *
     * @return the login template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/login")
    protected static String getLoginTemplate() {
        return "login";
    }

    /**
     * Called when a user attempts to sign-up with BarkPark
     *
     * @return the sign-up template to the user
     */
    @RequestMapping(method= RequestMethod.GET, value="/signup")
    protected String getSignupTemplate() {
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
        else if (dbManager.userExists(user.getUsername())) {
            logger.info("Username already exists");
            model.addAttribute("errorMsg", "Username already in use");
            return new ResponseEntity<>("Username already in use",  HttpStatus.BAD_REQUEST);
        }
        // Register user and return success
        else {
            logger.info("Success!");
            dbManager.insertUserToDB(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
                    user.getAge());
            // TODO return cookie
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
                user.getAge() == null ||
                user.getDogs() == null;
    }

    /**
     * Used to request a logout from the application
     *
     * @param request the HttpRequest entity containing header information
     * @param user the logout form of the user attempting to logout
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/logout", headers = "Accept=application/json")
    public ResponseEntity logout(HttpServletRequest request, @RequestBody User user) {
        return null;
    }

    /**
     * Used to update user profile (personal information)
     *
     * @param request the HttpRequest entity containing header information
     * @param user the update form of the user attempting to update profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateProfile", headers = "Accept=application/json")
    public ResponseEntity updateUserProfile(HttpServletRequest request, @RequestBody User user) {
        return null;
    }

    /**
     * Used to add a dog to a user's profile
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog to be added to user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addDog", headers = "Accept=application/json")
    public ResponseEntity addDog(HttpServletRequest request, @RequestBody Dog dog) {
        return null;
    }

    /**
     * Used to remove a dog from a user's profile
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog to be removed from the user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeDog", headers = "Accept=application/json")
    public ResponseEntity removeDog(HttpServletRequest request, @RequestBody Dog dog) {
        return null;
    }

    /**
     * Used to update a dog's information
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog profile to be updated
     * @return a RepsonseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateDog", headers = "Accept=application/json")
    public ResponseEntity updateDogProfile(HttpServletRequest request, @RequestBody Dog dog) {
        return null;
    }

    /**
     * Used to enter a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/goToPark", headers = "Accept=application/json")
    public ResponseEntity goToPark(HttpServletRequest request) {
        return null;
    }

    /**
     * Used to leave a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/leavePark", headers = "Accept=application/json")
    public ResponseEntity leavePark(HttpServletRequest request) {
        return null;
    }

    /**
     * Used to initiate a dog party
     *
     * @param request the HttpRequest entity containing header information
     * @param dogParty a DogParty object
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/startDogParty", headers = "Accept=application/json")
    public ResponseEntity startDogParty(HttpServletRequest request, @RequestBody DogParty dogParty) {
        return null;
    }

    /**
     * Used to end a dog party
     *
     * @param request the HttpRequest entity containing header information
     * @param dogParty a DogParty object
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/endDogParty", headers = "Accept=application/json")
    public ResponseEntity endDogParty(HttpServletRequest request, @RequestBody DogParty dogParty) {
        return null;
    }

    /**
     * Used to add a friend
     *
     * @param request the HttpRequest entity containing header information
     * @param user the user to add as a friend
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addFriend", headers = "Accept=application/json")
    public ResponseEntity addFriend(HttpServletRequest request, @RequestBody User user) {
        return null;
    }

    /**
     * Used to remove a friend
     *
     * @param request the HttpRequest entity containing header information
     * @param user the user to be removed from friend list
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeFriend", headers = "Accept=application/json")
    public ResponseEntity removeFriend(HttpServletRequest request, @RequestBody User user) {
        return null;
    }

    /**
     * Used to send a friend request to another user
     *
     * @param request the HttpRequest entity containing header information
     * @param user user to send request to
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendFriendRequest", headers = "Accept=application/json")
    public ResponseEntity sendFriendRequest(HttpServletRequest request, @RequestBody User user) {
        return null;
    }

    /**
     * Used to accept a friend request from another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/acceptFriendRequest", headers = "Accept=application/json")
    public ResponseEntity acceptFriendRequest(HttpServletRequest request) {
        return null;
    }

    /**
     * Used to reject a friend request from another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/rejectFriendRequest", headers = "Accept=application/json")
    public ResponseEntity rejectFriendRequest(HttpServletRequest request) {
        return null;
    }
}
