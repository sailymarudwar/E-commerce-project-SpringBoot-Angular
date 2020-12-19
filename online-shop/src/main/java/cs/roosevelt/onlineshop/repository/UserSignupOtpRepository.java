package cs.roosevelt.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cs.roosevelt.onlineshop.model.UserSignupOtp;

public interface UserSignupOtpRepository extends JpaRepository<UserSignupOtp, Long> {

	UserSignupOtp findByOtp(int otp);
}
