package jobseeker.urbanfix.controller;

import jobseeker.urbanfix.dto.BookingDto;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.request.BookingRequest;
import jobseeker.urbanfix.response.ApiResponse;
import jobseeker.urbanfix.service.booking.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/bookings")
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> bookWorker(@RequestBody BookingRequest request) {
        try {
            BookingDto booking = bookingService.bookWorker(request);
            return ResponseEntity.ok(new ApiResponse("Booking Successful", booking));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable Long id) {
        try {
            BookingDto booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(new ApiResponse("Found", booking));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getBookingsByUser(@PathVariable Long userId) {
        List<BookingDto> bookings = bookingService.getBookingByUser(userId);
        return ResponseEntity.ok(new ApiResponse("Found", bookings));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok(new ApiResponse("Booking Cancelled", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
