package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @project Assignment1-parent
 * @authorparam on
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;
    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(user.getId(),user.getName(), user.getEmail(),user.getRole(), user.isStudent());
    }
    private User mapRequestToUser(UserRequest request){
        return new User(request.id(),request.name(),request.email(),request.role(),request.student());
    }


    @Override
    public List<UserResponse> getAllUsers() {
        log.debug("Returning a list of Users.");
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.debug("Getting room by + " + userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return mapToUserResponse(user);
        }
        else return null;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        log.debug("Added the user with name + " + request.name());
        User user = mapRequestToUser(request);
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Long updateUser(Long userId, UserRequest userDetails) {
        log.debug("Updating a user with id{} + %d".formatted(userId));
        User user = userRepository.findById(userId).orElse(null);
        if(user!=null){
          user.setName(userDetails.name());
          user.setRole(userDetails.role());
          user.setEmail(userDetails.email());
          user.setStudent(userDetails.student());
            return userRepository.save(user).getId();
        }

        return userId;
    }
    public boolean isStudent(Long userId){
        return Objects.requireNonNull(userRepository.findById(userId).orElse(null)).isStudent();
    }
}
