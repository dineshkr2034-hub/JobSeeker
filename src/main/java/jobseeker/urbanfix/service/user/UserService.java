package jobseeker.urbanfix.service.user;

import jobseeker.urbanfix.dto.UserDto;
import jobseeker.urbanfix.exception.AlreadyExistException;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.User;
import jobseeker.urbanfix.repository.UserRepository;
import jobseeker.urbanfix.request.CreateUserRequest;
import jobseeker.urbanfix.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User Not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user-> !userRepository.existsByEmail(request.getEmail()))
                .map(req-> {
                    User user= new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(()->new AlreadyExistException("Oops!"+request.getEmail()+"already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long id) {
        return userRepository.findById(id).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,()->{
            throw new ResourceNotFoundException("User Not Found");
        });

    }

    @Override
    public UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
