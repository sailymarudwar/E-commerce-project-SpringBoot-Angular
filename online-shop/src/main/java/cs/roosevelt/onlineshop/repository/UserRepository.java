package cs.roosevelt.onlineshop.repository;

import cs.roosevelt.onlineshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User deleteByEmail(String email);

}
