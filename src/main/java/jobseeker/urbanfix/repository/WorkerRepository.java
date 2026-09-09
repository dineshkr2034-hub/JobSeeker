package jobseeker.urbanfix.repository;

import jobseeker.urbanfix.model.Category;
import jobseeker.urbanfix.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Long> {
    List<Worker> findByCategoryName(String category);

    List<Worker> findByName(String name);

    List<Worker> findByCategoryAndName(String category, String name);
}
