package cs.roosevelt.onlineshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cs.roosevelt.onlineshop.dto.LoginForm;
import cs.roosevelt.onlineshop.model.User;
import cs.roosevelt.onlineshop.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {
	
	

    @Autowired
    private UserService userService;
  
    /**
     * fetch-all-users endpoint
     *
     * NOTE: Used only for debugging or admins; this
     * is not used in the angular project
     * @return
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<User>> fetchAllUsers(HttpSession session) {
        return userService.getAll(session);
    }

    /**
     * fetch-one-user endpoint
     * @param email
     * @return
     */
    @GetMapping(value = {"/user/{email}", "/user/{email}/"})
    public ResponseEntity<User> fetchUser(@PathVariable("email") String email, HttpSession session) {
        return userService.getOne(email, session);
    }

    @GetMapping(value = {"/user/activeSession", "/user/activeSession/"})
    public ResponseEntity<User> fetchSessionUser(HttpSession session) {
        return userService.getSessionUser(session);
    }

    /**
     * register-user endpoint
     * @param user
     * @return
     */
    @PostMapping(value = {"/register", "/register/"})
    public ResponseEntity<User> registerUser(@RequestBody User user, HttpSession session) {
        return userService.register(user, session);
    }

    /**
     * update-user endpoint
     * @param user
     * @return
     */
    @PutMapping(value = {"/user/update", "/user/update/"})
    public ResponseEntity<User> updateUser(@RequestBody User user, HttpSession session) {
        return userService.update(user, session);
    }

    @DeleteMapping(value = {"/user/delete/{id}", "/user/delete/{id}/"})
    public ResponseEntity<User> deleteUser(@PathVariable("id") String email, HttpSession session) {
        return userService.delete(email, session);
    }

    /**
     * login-existing-user endpoint
     * @param credentials
     * @param session
     * @return An http status response and user details to verify/deny login attempt.
     */
    @PostMapping(value = {"/user/login", "/user/login/"})
    public ResponseEntity<User> login(@RequestBody final LoginForm credentials, HttpSession session) {
        return userService.login(credentials, session);
    }

    /**
     * logout-existing-session endpoint
     * @param session
     * @return An http status response to verify session termination.
     */
    @GetMapping(value = {"/user/logout", "/user/logout/"})
    public ResponseEntity<String> logout(HttpSession session) {
        return userService.logout(session);
    }

    /**
     * is-logged-in verification endpoint
     * @param session
     * @return
     */
    @GetMapping(value = {"user/loggedIn"})
    public boolean isLoggedIn(HttpSession session) {
        return (session != null) && (session.getAttribute("user") != null);
    }

    /**
     * login-test endpoint; only used for debugging
     * @param request
     * @return A message log.
     */
    @GetMapping(value = {"/user/home", "/user/home/"})
    public String testHome(HttpServletRequest request) {

        // the message to return
        String message = "not logged in";

        // is there an active session with a user?
        if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
            // yes, there is; modify the message to say so
            message = request.getSession().getAttribute("user").toString();
        }

        return message;
    }
      
    @GetMapping("/activateUser/{otp}") 
    public ResponseEntity<String> activateUser(@PathVariable("otp") int otp) {
        
    	String msg = userService.activateUser(otp);
    	if(msg.contains("Success")) {
    		return new ResponseEntity(msg, HttpStatus.OK);
    	}
        else if(msg.contains("Invalid")) {
        	return new ResponseEntity(msg, HttpStatus.CONFLICT);
        }else {
        	return new ResponseEntity(msg, HttpStatus.FOUND);
        }
    	
    }
 // Add an User
 	@PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<String> createUser(@RequestBody @Valid final User s, HttpServletRequest request) {
 		try {
 			String msg = userService.createUser(s,request);
 			if(msg.contains("Success")) {
 				return new ResponseEntity("An email has been sent with activation link, please check", HttpStatus.OK);
 			}
 			else
 				return new ResponseEntity(msg, HttpStatus.CONFLICT);
 		} catch (Exception e) {
 			e.printStackTrace();
 			return new ResponseEntity("Error in creatung User\n" + e.getMessage(),
 					HttpStatus.INTERNAL_SERVER_ERROR);
 		}
 	}	


}
