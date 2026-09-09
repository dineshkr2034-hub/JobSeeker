package jobseeker.urbanfix.service.favourite;

import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Favourite;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.repository.FavouriteRepository;
import jobseeker.urbanfix.service.user.IUserService;
import jobseeker.urbanfix.service.worker.IWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FavouriteService implements IFavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final IUserService userService;
    private final IWorkerService workerService;


    @Override
    public void addFavourite(Long userId, Long workerId) {
        User user =userService.getUserById(userId);
        Worker worker= workerService.getWorkerById(workerId);
        Favourite favourite= new Favourite(user,worker);
        favouriteRepository.save(favourite);


    }

    @Override
    public void deleteFavourite(Long id) {
        favouriteRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Favourite not found"));
    }

    @Override
    public List<Favourite> getFavouriteByUser(User user) {

        List<Favourite> userFavourite=favouriteRepository.findByUser(user);
        return userFavourite;
    }
}
