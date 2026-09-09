package jobseeker.urbanfix.repository;

import jobseeker.urbanfix.model.Favourite;
import jobseeker.urbanfix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    List<Favourite> findByUser(User user);
}
