package cs.roosevelt.onlineshop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import cs.roosevelt.onlineshop.dto.LoginForm;
import cs.roosevelt.onlineshop.model.User;

/**
 * The methods that must be implemented. Typically,
 * by a class of the same name with an 'Impl' suffix.
 *
 * Used for abstraction.
 */
public interface UserService {

    ResponseEntity<List<User>> getAll(HttpSession session);

    ResponseEntity<User> getOne(String email, HttpSession session);

    ResponseEntity<User> getSessionUser(HttpSession session);

    ResponseEntity<User> register(User userToRegister, HttpSession session);

    ResponseEntity<User> update(User userToUpdate, HttpSession session);

    ResponseEntity<User> delete(String email, HttpSession session);

    ResponseEntity<User> login(LoginForm credentials, HttpSession session);

    ResponseEntity<String> logout(HttpSession session);
    
    
    String createUser(User user,HttpServletRequest  request);

	String activateUser(int otp);

}
