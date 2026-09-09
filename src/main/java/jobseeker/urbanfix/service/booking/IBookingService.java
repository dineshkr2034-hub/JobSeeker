package jobseeker.urbanfix.service.booking;

import jobseeker.urbanfix.dto.BookingDto;
import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.request.BookingRequest;

import java.util.List;

public interface IBookingService {
    BookingDto bookWorker(BookingRequest request);
    void cancelBooking(Long id);
    BookingDto getBookingById(Long id);
    List<BookingDto> getBookingByUser(Long userId);
    BookingDto convertToDto(Booking booking);
}
