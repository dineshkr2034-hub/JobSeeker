package jobseeker.urbanfix.service.booking;

import jobseeker.urbanfix.dto.BookingDto;
import jobseeker.urbanfix.enums.BookingStatus;
import jobseeker.urbanfix.exception.AlreadyExistException;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.repository.BookingRepository;
import jobseeker.urbanfix.request.BookingRequest;
import jobseeker.urbanfix.service.user.IUserService;
import jobseeker.urbanfix.service.worker.IWorkerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IUserService userService;
    private final IWorkerService workerService;
    private final ModelMapper modelMapper;

    @Override
    public BookingDto bookWorker(BookingRequest request) {
        boolean isBooked = bookingRepository.isWorkerBookedAtTime(
                request.getWorkerId(),
                request.getScheduledTime()
        );

        if (isBooked) {
            throw new AlreadyExistException("Worker is already booked for this time slot.");
        }

        User user = userService.getUserById(request.getUserId());
        Worker worker = workerService.getWorkerById(request.getWorkerId());



        Booking booking = new Booking();
        booking.setUser(user);
        booking.setWorker(worker);
        booking.setCategory(worker.getCategory().getName());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setScheduledTime(request.getScheduledTime());
        booking.setAddress(request.getAddress());
        booking.setPrice(worker.getWages());

        Booking savedBooking = bookingRepository.save(booking);
        return convertToDto(savedBooking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return convertToDto(booking);
    }

    @Override
    public List<BookingDto> getBookingByUser(Long id) {
        List<Booking> list = bookingRepository.findByUserId(id);
        return list.stream().map(this::convertToDto).toList();
    }

    @Override
    public BookingDto convertToDto(Booking booking){
        return modelMapper.map(booking,BookingDto.class);
    }
}
