package jobseeker.urbanfix.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String transactionId;
    private String paymentStatus; // e.g., PENDING, SUCCESS, FAILED
    private Long amount;
    private LocalDateTime paymentDate;
}