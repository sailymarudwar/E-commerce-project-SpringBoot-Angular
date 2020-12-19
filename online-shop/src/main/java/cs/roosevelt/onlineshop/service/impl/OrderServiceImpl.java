package cs.roosevelt.onlineshop.service.impl;

import cs.roosevelt.onlineshop.model.Category;
import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.model.OrderDetail;
import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.model.User;
import cs.roosevelt.onlineshop.repository.CategoryRepository;
import cs.roosevelt.onlineshop.repository.OrderDetailsRepository;
import cs.roosevelt.onlineshop.repository.OrderRepository;
import cs.roosevelt.onlineshop.repository.UserRepository;
import cs.roosevelt.onlineshop.service.OrderService;
import cs.roosevelt.onlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * OrderServiceImpl defines the method signatures provided
 * by the OrderService interface for the controller to use.
 * <p>
 * Within each method, it uses methods from
 * the OrderRepository.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailsRepository orderDetailRepository;  

    @Autowired
    private UserRepository userRepository;

	@Override
	public ResponseEntity<List<Order>> getAll(HttpSession session) {
		  // is there an active session?
        if (session != null && session.getAttribute("user") != null) {

            // get the user from the session
            User sessionUser = (User) session.getAttribute("user");

            // is the session user valid?
            if (sessionUser != null && userRepository.existsById(sessionUser.getId())) {

                // yes, the user is valid

                // is the valid user an admin?
                if (sessionUser.getRole().equals("ROLE_MANAGER")) {

                    // yes, the user's an admin; get all users
                    return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);

                } else if (sessionUser.getRole().equals("ROLE_CUSTOMER")) {

                    // no, the user is not an admin; deny the request
                	
                    return new ResponseEntity<>(orderRepository.findAllByUser(sessionUser), HttpStatus.OK);

                }else {
                	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }

            } else {

                // no valid user found
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            }

        } else {

            // no, there's no active session; return denial response
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        }

	}


	@Override
	public ResponseEntity<List<OrderDetail>> getOrderDetails(String orderId) {
		Order order = new Order();
		order.setId(orderId);		
		return new ResponseEntity<>(orderDetailRepository.findAllByOrder(order), HttpStatus.OK);
	}
}