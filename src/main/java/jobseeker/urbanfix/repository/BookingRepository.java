package jobseeker.urbanfix.repository;

import jobseeker.urbanfix.dto.BookingDto;
import jobseeker.urbanfix.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userId);
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.worker.id = :workerId AND b.scheduledTime = :dateTime AND b.status != 'CANCELLED'")
    boolean isWorkerBookedAtTime(Long workerId, LocalDateTime dateTime);
}
