package cs.roosevelt.onlineshop.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.model.OrderDetail;

/**
 * The methods that must be implemented. Typically,
 * by a class of the same name with an 'Impl' suffix.
 *
 * Used for abstraction.
 */
public interface OrderService {

    ResponseEntity<List<Order>> getAll(HttpSession session);
  

	ResponseEntity<List<OrderDetail>> getOrderDetails(String orderId);

}
