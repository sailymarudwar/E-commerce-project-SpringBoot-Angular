package cs.roosevelt.onlineshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.model.User;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findById(String id);
    List<Order> findAllByUser(User user);
}
