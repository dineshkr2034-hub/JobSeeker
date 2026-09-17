package jobseeker.urbanfix.service.jobpost;

import jobseeker.urbanfix.model.Booking;
import jobseeker.urbanfix.model.JobPost;
import jobseeker.urbanfix.request.JobPostRequest;

import java.util.List;

public interface IJobPostService {
    JobPost createJobPost(JobPostRequest jobPost);
    List<JobPost> getOpenJobsByCategory(String category);
    Booking acceptJobPost(Long jobPostId, Long workerId);
}
