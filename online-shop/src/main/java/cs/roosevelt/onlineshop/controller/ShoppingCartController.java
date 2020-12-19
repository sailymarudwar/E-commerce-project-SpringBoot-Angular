package cs.roosevelt.onlineshop.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cs.roosevelt.onlineshop.model.CartItem;
import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.service.ShoppingCartService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<CartItem>> fetchCartItems(HttpSession session) {
        return shoppingCartService.getCart(session);
    }

    @PostMapping(value = {"/add-to-cart", "/add-to-cart/"})
    public ResponseEntity<List<CartItem>> addCartItem(@RequestBody CartItem cartItemToSave, HttpSession session) {
        return shoppingCartService.saveItem(cartItemToSave, session);
    }
    
    @PostMapping(value = {"/update-cart-item/{action}/", "/update-cart-item/{action}"})
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItemToSave, HttpSession session,@PathVariable("action") String action) {
        return shoppingCartService.updateCartItem(cartItemToSave,action,session);
    }

    @DeleteMapping(value = {"/remove-from-cart/{id}", "/remove-from-cart/{id}"})
    public ResponseEntity<Boolean> removeCartItem(@PathVariable("id") String productId, HttpSession session) {
        return shoppingCartService.removeItem(productId, session);
    }

    @GetMapping(value = {"/totalQuantity", "/totalQuantity/"})
    public ResponseEntity<Integer> fetchTotalQuantity(HttpSession session) {
        return shoppingCartService.getTotalQuantity(session);
    }
    @GetMapping(value = {"/placeorder", "/placeorder/"})
    public ResponseEntity<Order> placeOrder(HttpSession session) {
        return shoppingCartService.placeOrder(session);
    }

    @GetMapping(value = {"/totalPrice", "/totalPrice/"})
    public ResponseEntity<BigDecimal> fetchTotalPrice(HttpSession session) {
        return shoppingCartService.getTotalPrice(session);
    }
    
}
