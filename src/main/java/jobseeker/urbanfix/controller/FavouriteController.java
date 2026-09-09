package jobseeker.urbanfix.controller;

import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Favourite;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.response.ApiResponse;
import jobseeker.urbanfix.service.favourite.IFavouriteService;
import jobseeker.urbanfix.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/favourites")
public class FavouriteController {
    private final IFavouriteService favouriteService;
    private final IUserService userService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFavourite(@RequestParam Long userId,
                                                    @RequestParam Long workerId){
        try {

            favouriteService.addFavourite(userId,workerId);
            return ResponseEntity.ok(new ApiResponse("Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteFavourite(@PathVariable Long  id){
        favouriteService.deleteFavourite(id);
        return ResponseEntity.ok(new ApiResponse("Success",null));

    }
    @GetMapping("/{userId}/get")
    public ResponseEntity<ApiResponse> getFavouriteByUser(@PathVariable Long userId){
        try {
            User user= userService.getUserById(userId);
            List<Favourite> list=favouriteService.getFavouriteByUser(user);
            return ResponseEntity.ok(new ApiResponse("Success",list));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
