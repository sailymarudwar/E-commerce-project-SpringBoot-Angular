package cs.roosevelt.onlineshop.service;

import cs.roosevelt.onlineshop.model.CartItem;
import cs.roosevelt.onlineshop.model.Order;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {

    ResponseEntity<List<CartItem>> getCart(HttpSession session);

    ResponseEntity<List<CartItem>> saveItem(CartItem cartItemToSave, HttpSession session);

    ResponseEntity<Boolean> removeItem(String productId, HttpSession session);

    ResponseEntity<Integer> getTotalQuantity(HttpSession session);

    ResponseEntity<BigDecimal> getTotalPrice(HttpSession session);

	ResponseEntity<Order> placeOrder(HttpSession session);

	ResponseEntity<CartItem> updateCartItem(CartItem cartItemToSave, String action, HttpSession session);

}
