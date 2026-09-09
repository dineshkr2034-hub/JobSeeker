package jobseeker.urbanfix.service.user;

import jobseeker.urbanfix.dto.UserDto;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.request.CreateUserRequest;
import jobseeker.urbanfix.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long id);
    void deleteUser(Long id);

    UserDto convertToDto(User user);
}
