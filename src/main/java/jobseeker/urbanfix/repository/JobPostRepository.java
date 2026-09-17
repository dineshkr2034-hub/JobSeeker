package jobseeker.urbanfix.repository;

import jobseeker.urbanfix.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByCategory(String category);}