package jobseeker.urbanfix.repository;

import jobseeker.urbanfix.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
