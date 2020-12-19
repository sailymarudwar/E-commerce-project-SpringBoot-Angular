package cs.roosevelt.onlineshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import cs.roosevelt.onlineshop.model.Order;
import cs.roosevelt.onlineshop.model.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, String> {
    Optional<OrderDetail> findById(String id);

    List<OrderDetail> findAllByOrder(Order order);
}
