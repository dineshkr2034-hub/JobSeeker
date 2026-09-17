package jobseeker.urbanfix.controller;

import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.model.JobPost;
import jobseeker.urbanfix.request.JobPostRequest;
import jobseeker.urbanfix.response.ApiResponse;
import jobseeker.urbanfix.service.jobpost.IJobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/job-posts")
public class JobPostController {

    private final IJobPostService jobPostService;

    // 1. User posts a new job request
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createJobPost(@RequestBody JobPostRequest jobPost) {
        try {
            JobPost createdPost = jobPostService.createJobPost(jobPost);
            return ResponseEntity.ok(new ApiResponse("Job posted successfully", createdPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // 2. Worker views open jobs in their category
    @GetMapping("/open")
    public ResponseEntity<ApiResponse> getOpenJobs(@RequestParam String category) {
        try {
            List<JobPost> openJobs = jobPostService.getOpenJobsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("Found open jobs", openJobs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    // 3. Worker accepts a job, converting it into a Booking
    @PostMapping("/{id}/accept")
    public ResponseEntity<ApiResponse> acceptJobPost(@PathVariable Long id, @RequestParam Long workerId) {
        try {
            Booking newBooking = jobPostService.acceptJobPost(id, workerId);
            return ResponseEntity.ok(new ApiResponse("Job accepted successfully!", newBooking));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}