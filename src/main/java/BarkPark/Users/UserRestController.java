package BarkPark.Users;

import BarkPark.Application;
import BarkPark.Dogs.Dog;
import BarkPark.Dogs.DogParty;
import BarkPark.Users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains abstract methods that represent required endpoints to be implemented for Users.
 *
 */
@RestController
public class UserRestController {
    /**
     * Used to request a login to the application
     *
     * @param request the HttpRequest entity containing header information
     * @param user the login form of the user attempting to login
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", headers = "Accept=application/json")
    public @ResponseBody
    ResponseEntity login(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to sign up for the application
     *
     * @param request the HttpRequest entity containing header information
     * @param user the sign-up form of the user attempting to sign-up
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signup", headers = "Accept=application/json")
    public ResponseEntity signUp(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to request a logout from the application
     *
     * @param request the HttpRequest entity containing header information
     * @param user the logout form of the user attempting to logout
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/logout", headers = "Accept=application/json")
    public ResponseEntity logout(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to update user profile (personal information)
     *
     * @param request the HttpRequest entity containing header information
     * @param user the update form of the user attempting to update profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateProfile", headers = "Accept=application/json")
    public ResponseEntity updateUserProfile(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to add a dog to a user's profile
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog to be added to user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addDog", headers = "Accept=application/json")
    public ResponseEntity addDog(HttpServletRequest request, @Payload Dog dog) { return null; }

    /**
     * Used to remove a dog from a user's profile
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog to be removed from the user's profile
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeDog", headers = "Accept=application/json")
    public ResponseEntity removeDog(HttpServletRequest request, @Payload Dog dog) { return null; }

    /**
     * Used to update a dog's information
     *
     * @param request the HttpRequest entity containing header information
     * @param dog the dog profile to be updated
     * @return a RepsonseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateDog", headers = "Accept=application/json")
    public ResponseEntity updateDogProfile(HttpServletRequest request, @Payload Dog dog) { return null; }

    /**
     * Used to enter a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/goToPark", headers = "Accept=application/json")
    public ResponseEntity goToPark(HttpServletRequest request) { return null; }

    /**
     * Used to leave a park
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/leavePark", headers = "Accept=application/json")
    public ResponseEntity leavePark(HttpServletRequest request) { return null; }

    /**
     * Used to initiate a dog party
     *
     * @param request the HttpRequest entity containing header information
     * @param dogParty a DogParty object
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/startDogParty", headers = "Accept=application/json")
    public ResponseEntity startDogParty(HttpServletRequest request, @Payload DogParty dogParty) { return null; }

    /**
     * Used to end a dog party
     *
     * @param request the HttpRequest entity containing header information
     * @param dogParty a DogParty object
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/endDogParty", headers = "Accept=application/json")
    public ResponseEntity endDogParty(HttpServletRequest request, @Payload DogParty dogParty) { return null; }

    /**
     * Used to add a friend
     *
     * @param request the HttpRequest entity containing header information
     * @param user the user to add as a friend
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addFriend", headers = "Accept=application/json")
    public ResponseEntity addFriend(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to remove a friend
     *
     * @param request the HttpRequest entity containing header information
     * @param user the user to be removed from friend list
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeFriend", headers = "Accept=application/json")
    public ResponseEntity removeFriend(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to send a friend request to another user
     *
     * @param request the HttpRequest entity containing header information
     * @param user user to send request to
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sendFriendRequest", headers = "Accept=application/json")
    public ResponseEntity sendFriendRequest(HttpServletRequest request, @Payload User user) { return null; }

    /**
     * Used to accept a friend request from another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/acceptFriendRequest", headers = "Accept=application/json")
    public ResponseEntity acceptFriendRequest(HttpServletRequest request) { return null; }

    /**
     * Used to reject a friend request from another user
     *
     * @param request the HttpRequest entity containing header information
     * @return a ResponseEntity to the user
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/rejectFriendRequest", headers = "Accept=application/json")
    public ResponseEntity rejectFriendRequest(HttpServletRequest request) { return null; }

}
