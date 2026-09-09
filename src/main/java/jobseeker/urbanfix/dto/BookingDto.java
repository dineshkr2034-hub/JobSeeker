package jobseeker.urbanfix.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jobseeker.urbanfix.enums.BookingStatus;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.model.Worker;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private User user;
    private Worker worker;
    private String category;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledTime;
    private String address;
    private Long price;
}
