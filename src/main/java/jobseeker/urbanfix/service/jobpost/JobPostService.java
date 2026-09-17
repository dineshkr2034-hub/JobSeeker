package jobseeker.urbanfix.service.jobpost;

import jakarta.transaction.Transactional;
import jobseeker.urbanfix.enums.BookingStatus;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.model.JobPost;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.repository.BookingRepository;
import jobseeker.urbanfix.repository.JobPostRepository;
import jobseeker.urbanfix.request.JobPostRequest;
import jobseeker.urbanfix.service.user.IUserService;
import jobseeker.urbanfix.service.worker.IWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService implements IJobPostService {

    private final JobPostRepository jobPostRepository;
    private final BookingRepository bookingRepository;
    private final IWorkerService workerService;
    private final IUserService userService;

    @Override
    public JobPost createJobPost(JobPostRequest request) {
        User user = userService.getUserById(request.getUserId());
        JobPost jobPost = new JobPost();
        jobPost.setUser(user);
        jobPost.setCategory(request.getCategory());
        jobPost.setDescription(request.getDescription());
        jobPost.setAddress(request.getAddress());
        jobPost.setScheduledTime(request.getScheduledTime());
        return jobPostRepository.save(jobPost);
    }

    @Override
    public List<JobPost> getOpenJobsByCategory(String category) {
        return jobPostRepository.findByCategory(category);
    }

    @Override
    @Transactional
    public Booking acceptJobPost(Long jobPostId, Long workerId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new ResourceNotFoundException("This job is no longer available."));

        Worker worker = workerService.getWorkerById(workerId);

        Booking booking = new Booking();
        booking.setUser(jobPost.getUser());
        booking.setWorker(worker);
        booking.setCategory(jobPost.getCategory());
        booking.setStatus(BookingStatus.PENDING);
        booking.setScheduledTime(jobPost.getScheduledTime());
        booking.setAddress(jobPost.getAddress());
        booking.setPrice(worker.getWages());

        Booking savedBooking = bookingRepository.save(booking);

        jobPostRepository.delete(jobPost);

        return savedBooking;
    }
}