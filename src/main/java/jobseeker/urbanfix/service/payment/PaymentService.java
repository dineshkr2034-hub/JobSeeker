package jobseeker.urbanfix.service.payment;

import jakarta.transaction.Transactional;
import jobseeker.urbanfix.enums.BookingStatus;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.model.Payment;
import jobseeker.urbanfix.repository.BookingRepository;
import jobseeker.urbanfix.repository.PaymentRepository;
import jobseeker.urbanfix.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(PaymentRequest payload) {
        Booking booking = bookingRepository.findById(payload.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setTransactionId(payload.getTransactionId());
        payment.setAmount(booking.getPrice());
        payment.setPaymentDate(LocalDateTime.now());

        if ("PAYMENT.COMPLETED".equals(payload.getEventStatus())) {
            payment.setPaymentStatus("SUCCESS");
            booking.setStatus(BookingStatus.CONFIRMED);
        } else {
            payment.setPaymentStatus("FAILED");
            booking.setStatus(BookingStatus.CANCELLED);
        }

        paymentRepository.save(payment);
        bookingRepository.save(booking);
    }
}
