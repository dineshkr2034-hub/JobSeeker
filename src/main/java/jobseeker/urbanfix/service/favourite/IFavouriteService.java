package jobseeker.urbanfix.service.favourite;

import jobseeker.urbanfix.model.Favourite;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.model.Worker;

import java.util.List;

public interface IFavouriteService {
    void addFavourite(Long userId,Long workerId);
    void deleteFavourite(Long id);
    List<Favourite> getFavouriteByUser(User user);
}
